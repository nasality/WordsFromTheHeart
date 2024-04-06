package com.sishui.words.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sishui.words.pojo.TopicForumRelation;

public interface ITopicForumRelationService extends IService<TopicForumRelation> {
    TopicForumRelation getTopicForumRelationByTopicId(Integer topicId);

    void forumSave(Integer forumId, Integer activityId);
}
