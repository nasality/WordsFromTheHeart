package com.sishui.words.service;


import com.sishui.words.pojo.Topic;
import com.sishui.words.pojo.User;

import java.util.List;
public interface IContentRecommendService {

    /**
     * 推荐话题
     * @return 推荐的话题列表
     */
    List<Topic> recommendTopics(String userId, long offset, long size);

}
