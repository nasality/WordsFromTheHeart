package com.sishui.words.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sishui.words.mapper.TopicForumRelationMapper;
import com.sishui.words.pojo.TopicForumRelation;
import com.sishui.words.service.ITopicForumRelationService;
import org.springframework.stereotype.Service;

@Service
public class TopicForumRelationServiceImpl extends ServiceImpl<TopicForumRelationMapper, TopicForumRelation> implements ITopicForumRelationService {
    @Override
    public TopicForumRelation getTopicForumRelationByTopicId(Integer topicId) {
        if (topicId == null) {
            return null;
        }
        QueryWrapper<TopicForumRelation> wrapper = new QueryWrapper<>();
        wrapper.eq("topic_id", topicId);
        return baseMapper.selectOne(wrapper);
    }
}
