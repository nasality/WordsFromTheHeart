package com.sishui.words.controller;

import com.sishui.words.pojo.Comment;
import com.sishui.words.pojo.User;
import com.sishui.words.service.ICommentService;
import com.sishui.words.service.IUserService;
import com.sishui.words.vo.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

@RestController
@RequestMapping("comment")
public class CommentController {
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IUserService userService;

    @PostMapping("add")
    public Result addComment(@RequestBody CommentRequest request) {
        //TODO 评论频率检测
        //TODO 评论内容审核

        String userId = request.userId;
        Comment comment = new Comment();
        User user = userService.getUserById(userId);
        comment.setUser(user);
        comment.setContent(request.content);
        comment.setTime(new Date(System.currentTimeMillis()));
        comment.setLikeCount(0);
        comment.setPostId(request.postId);
        comment.setParentId(request.parentId);
        comment.setUserId(userId);
        int flag = commentService.saveComment(comment);
        //TODO 被评论者会收到通知
        if (flag != 0) {
            return Result.success();
        } else {
            return Result.error("评论失败");
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class CommentRequest {
        private String content;
        private String os;
        private Integer parentId;
        private Integer postId;
        private Integer replyId;
        private String userId;
    }

    @PostMapping("get")
    public Result getComment(@RequestBody CommentRequest request) {
        return Result.success(commentService.getCommentListByPostId(request.getPostId()));
    }
}
