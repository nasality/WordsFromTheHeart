package com.sishui.words.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sishui.words.mapper.LikeMapper;
import com.sishui.words.pojo.Like;
import com.sishui.words.service.ILikeService;
import org.springframework.stereotype.Service;


@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements ILikeService {
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
}
