package com.sishui.words.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sishui.words.mapper.UserMapper;
import com.sishui.words.pojo.User;
import com.sishui.words.pojo.UserTagRelation;
import com.sishui.words.req.UserREQ;
import com.sishui.words.service.IContentService;
import com.sishui.words.service.IUserService;
import com.sishui.words.service.IUserTagRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
       /* //TODO 算法需改善
        List<User> recommendedUsers = new ArrayList<>();

        // 获取当前用户的标签列表
        List<UserTagRelation> currentUserTags = userTagRelationService.getBaseMapper().selectList(new QueryWrapper<UserTagRelation>().eq("user_id", userId));
        Set<Integer> currentUserTagIds = currentUserTags.stream().map(UserTagRelation::getTagId).collect(Collectors.toSet());

        //查询未关注的用户
        List<User> allUserWithoutSelfFollow = userService.getUsersWithoutSelfFollow(userId);

        // 获取其他用户的标签列表，并计算与当前用户的标签重复度
        Map<String, Double> userTagSimilarityMap = new HashMap<>();
        List<UserTagRelation> allUserTags = userTagRelationService.getBaseMapper().selectList(new QueryWrapper<UserTagRelation>().ne("user_id", userId));

        for (UserTagRelation userTagRelation : allUserTags) {
            String otherUserId = userTagRelation.getUserId();
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

        return recommendedUsers;*/
        return null;
    }

    @Override
    public List<User> getUsersWithoutSelfFollow(Integer userId) {
        return baseMapper.selectUsersWithoutSelfFollow(userId);
    }


    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public int getPostCount(String userId) {
        // 获取所有实现了 IContentService 接口的 bean
        Map<String, IContentService> map = applicationContext.getBeansOfType(IContentService.class);
        // 遍历 Map 集合
        //动态数量
        int sumOfCount = 0;
        for (Map.Entry<String, IContentService> entry : map.entrySet()) {
            IContentService contentService = entry.getValue(); // 获取 bean 实例
            // 调用 IContentService 接口的 get 方法
            sumOfCount += contentService.getContentCount(userId);
        }
        return sumOfCount;
    }

    @Override
    public int getUserFansCount(String userId) {
        return 0;
    }

    @Override
    public User getUserById(String userId) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public boolean unFollow(String userId) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        User user = baseMapper.selectOne(wrapper);
        user.setFansCount(user.getFansCount() - 1);
        return baseMapper.updateById(user) > 0;
    }

    @Override
    public boolean follow(String userId) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        User user = baseMapper.selectOne(wrapper);
        user.setFansCount(user.getFansCount() + 1);
        return baseMapper.updateById(user) > 0;
    }

}
