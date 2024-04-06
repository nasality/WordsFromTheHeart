package com.sishui.words.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sishui.words.pojo.Activity;
import com.sishui.words.req.ActivityRequest;
import com.sishui.words.req.HomeListRequest;

import java.util.Map;

public interface IActivityService extends IService<Activity> {
    Map<String, Object> getActivityList(ActivityRequest request);
}
