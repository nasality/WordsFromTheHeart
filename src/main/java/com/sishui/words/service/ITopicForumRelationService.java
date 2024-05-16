package com.sishui.words.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sishui.words.pojo.TopicForumRelation;

import java.util.List;

public interface ITopicForumRelationService extends IService<TopicForumRelation> {
    TopicForumRelation getTopicForumRelationByTopicId(Integer topicId);

    void forumSave(Integer forumId, Integer activityId);

    List<Integer> getForumsByTopicId(Integer topicId);
}
