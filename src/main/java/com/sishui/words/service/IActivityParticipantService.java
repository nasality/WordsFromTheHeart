package com.sishui.words.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sishui.words.pojo.ActivityParticipant;

public interface IActivityParticipantService extends IService<ActivityParticipant> {
    Boolean getIsAttend(String userId, Integer activityId);
}
