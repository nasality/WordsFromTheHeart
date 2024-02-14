package com.sishui.words.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sishui.words.mapper.UserMapper;
import com.sishui.words.pojo.User;
import com.sishui.words.pojo.UserTagRelation;
import com.sishui.words.service.IUserService;
import com.sishui.words.service.IUserTagRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IUserTagRelationService userTagRelationService;

    @Autowired
    private IUserService userService;

    @Override
    public List<User> recommendUsers(Integer userId) {
        //TODO 算法需改善
        List<User> recommendedUsers = new ArrayList<>();

        // 获取当前用户的标签列表
        List<UserTagRelation> currentUserTags = userTagRelationService.getBaseMapper().selectList(new QueryWrapper<UserTagRelation>().eq("user_id", userId));
        Set<Integer> currentUserTagIds = currentUserTags.stream().map(UserTagRelation::getTagId).collect(Collectors.toSet());

        //查询未关注的用户
        List<User> allUserWithoutSelfFollow = userService.getUsersWithoutSelfFollow(userId);

        // 获取其他用户的标签列表，并计算与当前用户的标签重复度
        Map<Integer, Double> userTagSimilarityMap = new HashMap<>();
        List<UserTagRelation> allUserTags = userTagRelationService.getBaseMapper().selectList(new QueryWrapper<UserTagRelation>().ne("user_id", userId));

        for (UserTagRelation userTagRelation : allUserTags) {
            Integer otherUserId = userTagRelation.getUserId();
            Set<Integer> otherUserTagIds = allUserTags.stream().map(UserTagRelation::getTagId).collect(Collectors.toSet());

            // 计算标签重复度
            Set<Integer> intersection = new HashSet<>(currentUserTagIds);
            intersection.retainAll(otherUserTagIds);
            double similarity = (double) intersection.size() / (currentUserTagIds.size() + otherUserTagIds.size() - intersection.size());

            userTagSimilarityMap.put(otherUserId, similarity);
        }

        // 根据标签重复度排序
        List<Map.Entry<Integer, Double>> sortedSimilarityList = userTagSimilarityMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());

        // 获取重复度高的用户
        List<Integer> recommendedUserIds = sortedSimilarityList.subList(0, Math.min(sortedSimilarityList.size(), 10)).stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // 根据用户ID查询用户信息并返回
        // 这里假设你有一个UserService来获取用户信息
        for (Integer recommendedUserId : recommendedUserIds) {
            User recommendedUser = userService.getBaseMapper().selectById(recommendedUserId);
            recommendedUser.setPassword(null);
            recommendedUser.setMobile(null);
            recommendedUsers.add(recommendedUser);
        }

        return recommendedUsers;
    }

    @Override
    public List<User> getUsersWithoutSelfFollow(Integer userId) {
        return baseMapper.selectUsersWithoutSelfFollow(userId);
    }
}
