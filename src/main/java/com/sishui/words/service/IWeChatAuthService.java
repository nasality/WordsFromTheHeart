package com.sishui.words.service;

public interface IWeChatAuthService {
    String code2Session(String appId, String appSecret, String code);
}
