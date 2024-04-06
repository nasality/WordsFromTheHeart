package com.sishui.words.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sishui.words.mapper.ActivityParticipantMapper;
import com.sishui.words.pojo.ActivityParticipant;
import com.sishui.words.service.IActivityParticipantService;
import org.springframework.stereotype.Service;

@Service
public class ActivityParticipantServiceImpl extends ServiceImpl<ActivityParticipantMapper, ActivityParticipant> implements IActivityParticipantService {
    @Override
    public Boolean getIsAttend(String userId, Integer activityId) {
        QueryWrapper<ActivityParticipant> wrapper = new QueryWrapper<>();
        wrapper.eq("participant_id", userId);
        wrapper.eq("activity_id", activityId);
        return baseMapper.selectOne(wrapper) != null;
    }
}
