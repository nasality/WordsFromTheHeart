package com.sishui.words.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeChatAuthServiceImpl {

    public static Map<String, String> code2Session(String appId, String appSecret, String code) throws Exception {
        // 构建请求 URL
        String url = "https://api.weixin.qq.com/sns/jscode2session"
                + "?appid=" + appId
                + "&secret=" + appSecret
                + "&js_code=" + code
                + "&grant_type=authorization_code";

        // 发送 HTTP GET 请求
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        // 处理响应
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        // 将 JSON 响应解析为 Map 对象
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(response.toString(), new TypeReference<Map<String,String>>(){});
    }
}
