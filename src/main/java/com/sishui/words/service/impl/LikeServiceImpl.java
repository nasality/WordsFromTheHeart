package com.sishui.words.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sishui.words.mapper.LikeMapper;
import com.sishui.words.pojo.Like;
import com.sishui.words.service.ILikeService;
import com.sishui.words.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements ILikeService {
    @Autowired
    private ITopicService topicService;
    @Override
    public Integer getIsLikeByUserIdAndContentId(String userId, Integer topicId) {
        if (userId == null || topicId == null) {
            return 0;
        }
        QueryWrapper<Like> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("content_id", topicId);
        return baseMapper.selectCount(wrapper) > 0 ? 1 : 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateLikeCount(String userId, Integer postId){
        if (StringUtils.isBlank(userId) || postId == null) {
            throw new NullPointerException();
        }
        QueryWrapper<Like> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("content_id", postId);
        Like like = baseMapper.selectOne(wrapper);
        if (like != null) {
            try {
                baseMapper.delete(wrapper);
                topicService.decreaseTopicLike(postId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return false;
        }
        like = new Like();
        like.setUserId(userId);
        like.setContentId(postId);
        try {
            baseMapper.insert(like);
            topicService.updateTopicLike(postId);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        return true;
    }
}
