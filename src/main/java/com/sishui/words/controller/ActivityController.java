package com.sishui.words.controller;

import com.sishui.words.pojo.*;
import com.sishui.words.req.ActivityRequest;
import com.sishui.words.service.IActivityService;
import com.sishui.words.service.IImageService;
import com.sishui.words.service.ITopicForumRelationService;
import com.sishui.words.vo.Result;
import com.sishui.words.vo.TopicResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("activity")
public class ActivityController {
    @Autowired
    private IActivityService activityService;
    @Autowired
    private IImageService imageService;
    @Autowired
    private ITopicForumRelationService relationService;

    @PostMapping("create")
    public Result create(@RequestBody Activity activity) {
        activity.setCreateTime(new Timestamp(System.currentTimeMillis())); // 设置创建时间为当前时间
        boolean savedActivity = activityService.save(activity); // 保存 Activity 对象到数据库中
        List<String> paths = activity.getImages().stream().map(Image::getImagePath).collect(Collectors.toList());
        imageService.imagesSave(paths, activity.getActivityId());
        relationService.forumSave(activity.getForum().getId(), activity.getActivityId());
        if (savedActivity) {
            return Result.success();
        } else {
            return Result.error("保存失败");
        }
    }

    @PostMapping("list")
    public Result getList(@RequestBody ActivityRequest activity) {
        Map<String, Object> data = activityService.getActivityList(activity);
        return Result.success(data);
    }
}
