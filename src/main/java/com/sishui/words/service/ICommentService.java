package com.sishui.words.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sishui.words.controller.CommentController;
import com.sishui.words.pojo.Comment;

import java.util.List;

public interface ICommentService extends IService<Comment> {
    Long getCommentCountById(Integer id);

    List<Comment> getCommentListByPostId(Integer topicId);

    int getIsCommentByUserIdAndTopicId(String userId, Integer topicId);

    int saveComment(Comment comment);

    List<Comment> findByArticleId(Integer topicId);
}
