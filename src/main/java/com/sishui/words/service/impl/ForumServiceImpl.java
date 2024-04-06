package com.sishui.words.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sishui.words.mapper.FollowMapper;
import com.sishui.words.mapper.ForumMapper;
import com.sishui.words.pojo.Forum;
import com.sishui.words.pojo.TopicForumRelation;
import com.sishui.words.service.IForumService;
import com.sishui.words.service.ITopicForumRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumServiceImpl extends ServiceImpl<ForumMapper, Forum> implements IForumService {
    @Autowired
    private ITopicForumRelationService topicForumRelationService;

    @Override
    public List<Forum> getListById(Integer id) {
        QueryWrapper<Forum> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_tab_id", id);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public Forum getForumByTopicId(Integer topicId) {
        if (topicId == null) return null;
        TopicForumRelation topicForumRelation = topicForumRelationService.getTopicForumRelationByTopicId(topicId);
        QueryWrapper<Forum> wrapper = new QueryWrapper<>();
        wrapper.eq("id", topicForumRelation.getForumId());
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public void forumSave(String forumId, Integer activityId) {

    }
}
