package com.sishui.words.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sishui.words.mapper.TopicMapper;
import com.sishui.words.pojo.Topic;
import com.sishui.words.req.HomeListRequest;
import com.sishui.words.service.IContentService;
import com.sishui.words.service.ITopicService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public Boolean updateTopicLike(Integer topicId) throws Exception {
        if (topicId == null) {
            throw new NullPointerException();
        }
        int update = 0;
        try {
            Topic topic = baseMapper.selectById(topicId);
            topic.setLikeCount(topic.getLikeCount() + 1);
            update = baseMapper.updateById(topic);
        } catch (Exception e) {
            throw new Exception();
        }
        return update > 0;
    }

    @Override
    public List<Topic> selectTopicPage(HomeListRequest request) {
        //TODO 增加limit时需修改
        QueryWrapper<Topic> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        IPage<Topic> page = baseMapper.selectPage(request.getPage(), wrapper);
        return page.getRecords();
    }

    @Override
    public boolean decreaseTopicLike(Integer postId) throws Exception {
        if (postId == null) {
            throw new NullPointerException();
        }
        int update = 0;
        try {
            Topic topic = baseMapper.selectById(postId);
            topic.setLikeCount(topic.getLikeCount() - 1);
            update = baseMapper.updateById(topic);
        } catch (Exception e) {
            throw new Exception();
        }
        return update > 0;
    }
}
