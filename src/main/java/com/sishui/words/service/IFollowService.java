package com.sishui.words.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sishui.words.pojo.Follow;
import com.sishui.words.pojo.User;
import com.sishui.words.req.UserREQ;

import java.util.List;

public interface  IFollowService extends IService<Follow> {
    boolean isFollowingUser(String userId, String userId1);

    List<User> getFollowedUsers(UserREQ userREQ);


    List<User> getFollowUsers(UserREQ userREQ);
    

    Follow getByFollowerIdAndFollowedId(String followerId, String userId);

    boolean unFollow(String followerId, String userId);

    Integer getUserRelationship(String userId1, String userId2);
}
