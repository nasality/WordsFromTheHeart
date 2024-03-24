package com.sishui.words.controller;

import com.sishui.words.service.ILikeService;
import com.sishui.words.vo.BasicRequest;
import com.sishui.words.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class LikeController {

    @Autowired
    private ILikeService likeService;

    @PostMapping("like")
    public Result userLike(@RequestBody BasicRequest request) {
        if (request.getUserId() == null || request.getPostId() == null) {
            return Result.error("参数错误");
        }
        boolean flag;
        try {
            flag = likeService.updateLikeCount(request.getUserId(), request.getPostId());
        } catch (Exception e) {
            System.out.println(e);
            return Result.error("点赞失败");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("is_like",  flag ? 1 : 0);

        return Result.success(data);
    }
}
