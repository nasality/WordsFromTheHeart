package com.sishui.words.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sishui.words.mapper.TopicMapper;
import com.sishui.words.pojo.Topic;
import com.sishui.words.service.IContentService;
import com.sishui.words.service.ITopicService;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements ITopicService , IContentService {
    @Override
    public long getContentCount(String userId) {
        QueryWrapper<Topic> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return baseMapper.selectCount(wrapper);
    }

    public Integer countTotalLikesByUserId(String userId) {
        return baseMapper.countTotalLikesByUserId(userId);
    }
}
