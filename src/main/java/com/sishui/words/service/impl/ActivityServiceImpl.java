package com.sishui.words.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sishui.words.mapper.ActivityMapper;
import com.sishui.words.pojo.Activity;
import com.sishui.words.pojo.Image;
import com.sishui.words.pojo.TopicForumRelation;
import com.sishui.words.req.ActivityRequest;
import com.sishui.words.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {
    private static final Long PAGE_SIZE = 10L;

    @Override
    public Map<String, Object> getActivityList(ActivityRequest request) {
        Map<String, Object> data = new HashMap<>();

        //置顶数量
        data.put("sticky_count", 0);
        List<Activity> topicList = process(getActivitys(request), request.getUserId());
        data.put("topics", topicList);
        if (topicList.size() < PAGE_SIZE) {
            data.put("more", "nomore");
        } else {
            data.put("more", "more");
        }
        return data;
    }

    private List<Activity> getActivitys(ActivityRequest request) {
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        IPage<Activity> page = baseMapper.selectPage(request.getPage(), wrapper);
        return page.getRecords();
    }

    @Autowired
    private IImageService imageService;
    @Autowired
    private ITopicForumRelationService relationService;
    @Autowired
    private IForumService forumService;
    @Autowired
    private IActivityParticipantService activityParticipantService;

    private List<Activity> process(List<Activity> activityList, String userId) {
        for (Activity activity : activityList) {
            List<Image> imageList = imageService.getImageListById(activity.getActivityId());
            activity.setImages(imageList);
            TopicForumRelation topicForumRelation = relationService.getTopicForumRelationByTopicId(activity.getActivityId());
            activity.setForum(forumService.getForumByTopicId(topicForumRelation.getTopicId()));
            Boolean attend = userId != null && activityParticipantService.getIsAttend(userId, activity.getActivityId());
            activity.setAttend(attend);
            Timestamp curTime = new Timestamp(System.currentTimeMillis());
            activity.setEnd(curTime.compareTo(activity.getEndTime()) > 0);
        }
        return activityList;
    }
}