package com.sishui.words.controller;

import com.sishui.words.pojo.*;
import com.sishui.words.service.*;
import com.sishui.words.vo.Result;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bbs")
public class TopicController {

    @Autowired
    private IForumService forumService;
    @Autowired
    private ITopicService topicService;
    @Autowired
    private IImageService imageService;
    @Autowired
    private ITopicForumRelationService topicForumRelationService;
    @Autowired
    private ISubjectContentRelationService subjectContentRelationService;
    @Autowired
    private ISubjectService subjectService;

    @PostMapping("list_cat")
    public Result getListCat(@RequestBody Forum forum) {
        if (forum == null || forum.getId() == null) {
            return Result.error("参数错误");
        }

        List<Forum> forumList = forumService.getListById(forum.getId());
        Map<String, Object> data = new HashMap<>();
        data.put("forums", forumList);
        return Result.success(data);
    }

    @PostMapping("topic_create")
    public Result topicPost(@RequestBody TopicRequest topicRequest) {
        //TODO 异常处理
        try {
            //创建新话题
            Topic topic = new Topic();
            topic.setUserId(topicRequest.getUserId());
            topic.setLikeCount(0);
            topic.setLocation(topicRequest.getMarker());
            topic.setCreateTime(new Timestamp(System.currentTimeMillis()));
            topic.setTopicDetail(topicRequest.getContent());
            topicService.save(topic);

            //创建图片
            for (String image : topicRequest.getImages()) {
                Image newImage = new Image();
                newImage.setImagePath(image);
                newImage.setTopicId(topic.getTopicId());
                imageService.save(newImage);
            }


            //圈子
            if (topicRequest.getForumId() != null) {
                TopicForumRelation topicForumRelation = new TopicForumRelation();
                topicForumRelation.setTopicId(topic.getTopicId());
                topicForumRelation.setForumId(topicRequest.getForumId());
                topicForumRelationService.save(topicForumRelation);
            }

            //字符串分割，解析出单个话题
            String[] subjects = topicRequest.getSubjects().split("-0-");
            for (String subject : subjects) {
                Subject oldSubject;
                if ((oldSubject = subjectService.getSubjectByName(subject)) == null) {
                    oldSubject = new Subject();
                    oldSubject.setName(subject);
                    subjectService.save(oldSubject);
                }
                //话题
                SubjectContentRelation subjectContentRelation = new SubjectContentRelation();
                subjectContentRelation.setSubjectId(oldSubject.getSubjectId());
                subjectContentRelation.setRelatedId(topic.getTopicId());
                subjectContentRelationService.save(subjectContentRelation);
            }
        } catch (Exception e) {
            return Result.error("新建话题失败");
        }

        return Result.success();
    }


    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    private static class TopicRequest {
        private String userId;
        private String type;
        private String content;
        private double latitude;
        private double longitude;
        private String marker;
        private String address;
        private Integer score;
        private Integer forumId;
        private String atList;
        private List<String> images;
        private String video;
        private String videoCover;
        private String subjects;
    }

}
