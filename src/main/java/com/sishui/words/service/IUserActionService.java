package com.sishui.words.service;

import com.sishui.words.constants.ActionType;

import java.util.Map;

public interface IUserActionService {
    void increaseActionScore(String userId, String contentId, ActionType action);
    void decreaseActionScore(String userId, String contentId, ActionType action);
    Map<String, String> getUserActionData(String userId);
}
