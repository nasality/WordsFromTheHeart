package com.sishui.words.controller;

import com.sishui.words.mapper.FollowMapper;
import com.sishui.words.pojo.Follow;
import com.sishui.words.service.IFollowService;
import com.sishui.words.service.IUserService;
import com.sishui.words.util.HttpRequest;
import com.sishui.words.vo.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class FollowController {
    @Autowired
    private IFollowService followService;
    @Autowired
    private IUserService userService;

    @PostMapping("/follow_user")
    public Result followUser(@RequestBody FollowRequest request) {

        Follow follow = new Follow();
        Map<String, Object> data = new HashMap<>();
        boolean flag;
        if (followService.getByFollowerIdAndFollowedId(request.getFollowerId(), request.getUserId() )!= null) {
             followService.unFollow(request.getFollowerId(), request.getUserId());
             flag = false;
             //腾讯IM取消关注
             String[] userIds = new String[1];
             userIds[0] = request.getUserId();
             HttpRequest.deleteFriends(request.getFollowerId(), userIds);
        } else {

            follow.setFollowerId(request.getFollowerId());
            follow.setFollowedId(request.userId);
            follow.setFollowTime(new Timestamp(System.currentTimeMillis()));
            userService.follow(request.userId);
            flag = followService.save(follow);
            //腾讯IM添加关注
            HttpRequest.addFriend(request.getFollowerId(), request.getUserId());
        }
        data.put("isFollow", flag);
        return Result.success(data);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class FollowRequest {
        private String followerId;
        private String userId;
        private String os;
    }

}
