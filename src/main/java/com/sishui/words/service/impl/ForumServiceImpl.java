package com.sishui.words.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sishui.words.mapper.FollowMapper;
import com.sishui.words.mapper.ForumMapper;
import com.sishui.words.pojo.Forum;
import com.sishui.words.service.IForumService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumServiceImpl extends ServiceImpl<ForumMapper, Forum> implements IForumService {
    @Override
    public List<Forum> getListById(Integer id) {
        QueryWrapper<Forum> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_tab_id", id);
        return baseMapper.selectList(wrapper);
    }
}
