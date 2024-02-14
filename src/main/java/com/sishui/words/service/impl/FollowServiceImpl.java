package com.sishui.words.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sishui.words.mapper.FollowMapper;
import com.sishui.words.pojo.Follow;
import com.sishui.words.service.IFollowService;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements IFollowService {
}
