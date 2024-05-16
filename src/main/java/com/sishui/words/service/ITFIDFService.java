package com.sishui.words.service;

import com.sishui.words.dto.Content;
import com.sishui.words.pojo.User;

import java.util.List;
import java.util.Map;

public interface ITFIDFService {

    /**
     * 计算用户的TF-IDF值
     * @param user 用户对象
     * @param contentList 内容列表（活动、话题）
     * @return 计算得到的TF-IDF值
     */
    Map<String, Double> calculateTFIDF(User user, List<Content> contentList);

    /**
     * 将用户标签数据缓存到Redis中
     * @param user 用户对象
     */
    void cacheUserTags(User user, List<String> tags);

    /**
     * 从Redis中获取用户标签数据
     * @param userId 用户ID
     * @return 用户标签数据
     */
    List<String> getUserTags(String userId);
}