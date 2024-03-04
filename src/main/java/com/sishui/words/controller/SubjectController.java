package com.sishui.words.controller;


import com.sishui.words.pojo.Subject;
import com.sishui.words.pojo.Topic;
import com.sishui.words.service.ISubjectService;
import com.sishui.words.vo.Result;
import com.sishui.words.vo.TopicVO;
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

@RestController
@RequestMapping("/bbs")
public class SubjectController {

    @Autowired
    private ISubjectService subjectService;

    @PostMapping("/setting_subject")
    public Result settingSubject() {
        //TODO 热门话题推荐

        Map<String, Object> data = new HashMap<>();
        List<Subject> allSubject = subjectService.list();
        data.put("alls", allSubject);
        return Result.success(data);
    }

    @PostMapping("/topic_cteate")
    public Result topicCreate(@RequestBody TopicVO topicVO) {
        //TODO 发帖频繁检验
        if (topicVO == null) {
            return Result.error("参数为空");
        }
        if (topicVO.getUserId() == null) {
            return Result.error("未登录");
        }
        //创建新话题
        Topic topic = new Topic();
        topic.setTopicDetail(topicVO.getContent());
        topic.setCreateTime(new Timestamp(System.currentTimeMillis()));

        return null;
    }


}
