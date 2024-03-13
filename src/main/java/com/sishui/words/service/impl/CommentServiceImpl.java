package com.sishui.words.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sishui.words.mapper.CommentMapper;
import com.sishui.words.pojo.Comment;
import com.sishui.words.service.ICommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Override
    public Long getCommentCountById(Integer id) {
        if (id == null) return null;
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("post_id", id);
        return baseMapper.selectCount(wrapper);
    }

    @Override
    public List<Comment> getCommentListByPostId(Integer topicId) {
        if (topicId == null) return null;
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        return null;
    }

    @Override
    public int getIsCommentByUserIdAndTopicId(String userId, Integer topicId) {
        if (StringUtils.isEmpty(userId) || topicId == null) {
            //应该抛出异常
            return 0;
        }
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("post_id", topicId);
        wrapper.eq("user_id", userId);
        return baseMapper.selectCount(wrapper) > 0 ? 1 : 0;
    }
}
