package com.sishui.words.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sishui.words.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    List<Comment> findByArticleId(Integer topicId);
}
