package com.sishui.words.service.impl;

import com.sishui.words.pojo.Topic;
import com.sishui.words.pojo.User;
import com.sishui.words.pojo.UserTag;
import com.sishui.words.service.IContentRecommendService;
import com.sishui.words.service.ITopicForumRelationService;
import com.sishui.words.service.ITopicService;
import com.sishui.words.service.IUserTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ContentRecommendServiceImpl implements IContentRecommendService {

    private static final String TAG_PREFIX= "user_tag_";
    @Autowired
    private IUserTagService userTagService;
    @Autowired
    private ITopicService topicService;
    private static final double THRESHOLD = 0.05;
    @Autowired
    private ITopicForumRelationService topicForumRelationService;

    /**
     * 推荐话题
     * @return 推荐的话题列表
     */
    @Override
    public List<Topic> recommendTopics(String userId, long offset, long size) {
        List<Topic> recommendedTopics = new ArrayList<>();
        // 获取用户标签数据
        List<UserTag> userTags = userTagService.getUserTags(userId);
        // 计算话题的 TF-IDF 值
        Map<Integer, Double> userTFIDF = calculateTFIDF(userTags);

        // 根据 TF-IDF 值获取相关话题
        List<String> relevantTopicIds = getRelevantTopicIds(userTFIDF, offset, size);

        // 查询话题信息
        //List<Topic> recommendedTopics = topicService.getTopicsByIds(relevantTopicIds);




        //如果查询结果不足页大小，从数据库中再次查询。


        return recommendedTopics;
    }


    // 计算用户与话题的匹配程度
    private double calculateMatchScore(Map<String, Double> userTFIDF, Map<String, Double> topicTFIDF) {
        // 这里可以根据具体的计算方法来计算匹配程度
        // 这里只是一个简单的示例，实际应用中可能需要更复杂的计算逻辑
        double matchScore = 0.0;
        for (Map.Entry<String, Double> entry : userTFIDF.entrySet()) {
            String tag = entry.getKey();
            if (topicTFIDF.containsKey(tag)) {
                matchScore += entry.getValue() * topicTFIDF.get(tag);
            }
        }
        return matchScore;
    }

    private List<String> getRelevantTopicIds(Map<Integer, Double> userTFIDF, long offset, long size) {
        List<String> relevantTopicIds = new ArrayList<>();
        // 分页处理
        long start = offset;
        long end = offset + size - 1;
        // 获取所有话题的 ID
        List<Integer> allTopicIds = topicService.getAllTopicIds();
        // 遍历所有话题，找出与用户 TF-IDF 值相关度高的话题
       /* for (Integer topicId : allTopicIds) {
            // 获取话题的标签信息
            List<Integer> topicTags = topicForumRelationService.getForumsByTopicId(topicId);
            // 计算话题的 TF-IDF 值
            Map<Integer, Double> topicTFIDF = calculateTFIDF(topicTags);
            // 计算用户 TF-IDF 值与话题 TF-IDF 值的相关度
            double similarity = calculateCosineSimilarity(userTFIDF, topicTFIDF);
            // 如果相关度超过一定阈值，则将该话题加入到推荐列表中
            if (similarity > THRESHOLD) {
                relevantTopicIds.add(topicId);
            }
        }*/
        return relevantTopicIds;
    }

    private Map<Integer, Double> calculateTFIDF(List<UserTag> userTags) {
        Map<Integer, Double> userTFIDF = new HashMap<>();

        // 计算用户标签的 TF（词频）
        Map<Integer, Integer> termFrequency = new HashMap<>();
        int totalTags = userTags.size();
        for (UserTag tag : userTags) {
            termFrequency.put(tag.getForumId(), tag.getHotnessIndex());
        }
        List<List<UserTag>> userTagsList = userTagService.getAllUserTags();
        // 计算用户标签的 IDF（逆文档频率）
        Map<Integer, Integer> documentFrequency = calculateDocumentFrequency(userTagsList);

        // 计算用户标签的 TF-IDF 值
        for (Map.Entry<Integer, Integer> entry : termFrequency.entrySet()) {
            Integer tag = entry.getKey();
            double tf = entry.getValue() / (double) totalTags;
            double idf = Math.log(totalTags / (double) documentFrequency.getOrDefault(tag, 1)); // 避免除零
            double tfidf = tf * idf;
            userTFIDF.put(tag, tfidf);
        }

        return userTFIDF;
    }

    private Map<Integer, Integer> calculateDocumentFrequency(List<List<UserTag>> userTagsList) {
        Map<Integer, Integer> documentFrequency = new HashMap<>();

        // 遍历所有用户标签
        Set<Integer> uniqueTags = new HashSet<>();
        for (List<UserTag> userTags : userTagsList) {
            for (UserTag tag : userTags) {
                // 统计标签在所有用户中出现的次数
                uniqueTags.add(tag.getForumId());
            }
        }

        // 计算每个标签在所有用户中出现的次数
        for (Integer tag : uniqueTags) {
            int count = 0;
            for (List<UserTag> userTags : userTagsList) {
                for (UserTag userTag : userTags) {
                    if (userTag.getForumId().equals(tag)) {
                        count++;
                        break; // 避免重复计数
                    }
                }
            }
            documentFrequency.put(tag, count);
        }

        return documentFrequency;
    }


}
