package com.sishui.words.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sishui.words.mapper.FollowMapper;
import com.sishui.words.pojo.Follow;
import com.sishui.words.pojo.User;
import com.sishui.words.req.UserREQ;
import com.sishui.words.service.IContentService;
import com.sishui.words.service.IFollowService;
import com.sishui.words.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements IFollowService {

    @Autowired
    private IUserService userService;



    @Override
    public List<User> getFollowUsers(UserREQ userREQ) {
        if (StringUtils.isEmpty(userREQ.getUserId())) {
            return null;
        }
        QueryWrapper<Follow> wrapper = new QueryWrapper<>();
        wrapper.eq("followed_id", userREQ.getUserId());
        wrapper.orderByDesc("follow_time");
        List<Follow> follows = baseMapper.selectPage(userREQ.getPage(), wrapper).getRecords();
        List<User> ret = new ArrayList<>();
        for (Follow follow : follows) {
            User user = userService.getById(follow.getFollowerId());
            ret.add(user);
        }
        return ret;
    }

    @Override
    public Follow getByFollowerIdAndFollowedId(String followerId, String userId) {
        QueryWrapper<Follow> wrapper = new QueryWrapper<>();
        wrapper.eq("follower_id", followerId);
        wrapper.eq("followed_id", userId);
        return baseMapper.selectOne(wrapper);
    }


    @Transactional
    @Override
    public boolean unFollow(String followerId, String userId) {
        QueryWrapper<Follow> wrapper = new QueryWrapper<>();
        wrapper.eq("follower_id", followerId);
        wrapper.eq("followed_id", userId);
        userService.unFollow(userId);
        return baseMapper.delete(wrapper) > 0;
    }


    /**
     * A 是否 关注 B
     * @param follower A
     * @param followed B
     * @return Boolean
     */
    //A 是否 关注 B
    @Override
    public boolean isFollowingUser(String follower, String followed) {
        if (!StringUtils.hasLength(followed) || !StringUtils.hasLength(follower)) {
            return false;
        }

        QueryWrapper<Follow> wrapper = new QueryWrapper<>();
        wrapper.eq("follower_id", follower);
        wrapper.eq("followed_id", followed);
        return baseMapper.selectOne(wrapper) != null;
    }

    @Override
    public List<User> getFollowedUsers(UserREQ userREQ) {
        if (StringUtils.isEmpty(userREQ.getUserId())) {
            return null;
        }
        QueryWrapper<Follow> wrapper = new QueryWrapper<>();
        wrapper.eq("follower_id", userREQ.getUserId());
        wrapper.orderByDesc("follow_time");
        List<Follow> follows = baseMapper.selectPage(userREQ.getPage(), wrapper).getRecords();

        List<User> ret = new ArrayList<>();
        for (Follow follow : follows) {
            User user = userService.getById(follow.getFollowedId());
            ret.add(user);
        }
        return ret;
    }
}
