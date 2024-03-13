package com.sishui.words.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sishui.words.mapper.ImageMapper;
import com.sishui.words.mapper.TopicMapper;
import com.sishui.words.pojo.Image;
import com.sishui.words.pojo.Topic;
import com.sishui.words.service.IImageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements IImageService {
    @Override
    public List<Image> getImageListById(Integer topicId) {
        if (topicId == null) return null;
        QueryWrapper<Image> wrapper = new QueryWrapper<>();
        wrapper.eq("topic_id", topicId);
        return baseMapper.selectList(wrapper);
    }
}
