package com.sishui.words.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sishui.words.mapper.UserTagMapper;
import com.sishui.words.pojo.UserTag;
import com.sishui.words.service.IUserTagService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTagServiceImpl extends ServiceImpl<UserTagMapper, UserTag> implements IUserTagService {
    @Override
    public List<UserTag> getUserTags(String userId) {
        QueryWrapper<UserTag> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<List<UserTag>> getAllUserTags() {
        //return baseMapper.getAllUserTags();
        return null;
    }
}
