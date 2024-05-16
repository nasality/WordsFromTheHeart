package com.sishui.words.service.impl;

import com.sishui.words.constants.ActionType;
import com.sishui.words.service.ITopicService;
import com.sishui.words.service.IUserActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Map;

public class UserActionServiceImpl implements IUserActionService {
    private static final String TAG_PREFIX = "user_action:";
    private final RedisTemplate<String, String> redisTemplate;
    private final HashOperations<String, String, String> hashOperations;
    private final ZSetOperations<String, String> zSetOperations;
    private static final String ACTION_RANKINIG_PREFIX = "action_ranking:";
    @Autowired
    private ITopicService topicService;


    @Autowired
    public UserActionServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.zSetOperations = redisTemplate.opsForZSet();
    }

    //用户在哪些话题上产生哪些行为
    @Override
    public void increaseActionScore(String userId, String forumId, ActionType action) {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(ACTION_RANKINIG_PREFIX + userId))) {
            //获取用户的话题列表
            List<Integer> topicIdList = topicService.getAllTopicIds();
            for (Integer topicId : topicIdList) {
                zSetOperations.add(ACTION_RANKINIG_PREFIX + userId, String.valueOf(topicId), 0);
            }
        } else {
            // 如果ZSet存在，则直接添加数据
            zSetOperations.incrementScore(ACTION_RANKINIG_PREFIX + userId, forumId, action.getWeight());
        }

        zSetOperations.add(ACTION_RANKINIG_PREFIX + userId, forumId, action.getWeight());
    }


    @Override
    public void decreaseActionScore(String userId, String forumId, ActionType action) {
        zSetOperations.add(ACTION_RANKINIG_PREFIX + userId, forumId, -action.getWeight());

    }

    private void updateRanking(String userId, String actionId) {
        double score = calculateScore(actionId, userId);
        zSetOperations.add(ACTION_RANKINIG_PREFIX + userId, actionId, score);
    }

    private double calculateScore(String actionId, String userId) {
        Map<String, String> userActionData = getUserActionData(actionId);
        double totalScore = 0.0;

        for (ActionType actionType : ActionType.values()) {
            String actionName = actionType.getActionName();
            if (userActionData.containsKey(actionName)) {
                double actionScore = Double.parseDouble(userActionData.get(actionName));
                double score = actionType.getWeight() * actionScore;
                userActionData.put(actionName, String.valueOf(score));
                totalScore += score;
            }
        }

        return totalScore;
    }

    public Map<String, String> getUserActionData(String userId) {
        return hashOperations.entries(TAG_PREFIX + userId);
    }
}
