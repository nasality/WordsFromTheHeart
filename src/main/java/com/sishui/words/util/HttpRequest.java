package com.sishui.words.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sishui.words.pojo.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {
    public static Image getImageInfo(String path) {
        try {
            // 要发送请求的目标URL，添加查询参数来获取图片信息
            URL url = new URL(path + "?imageInfo");

            // 创建HttpURLConnection对象
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法为GET
            connection.setRequestMethod("GET");

            // 设置请求头部信息
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer token"); // 可选的授权信息

            // 获取响应
            int responseCode = connection.getResponseCode();
            BufferedReader reader;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            // 读取响应内容
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();


            // 解析响应JSON并将其转换为Image对象，此处需要您实现Image类的构造方法和getter/setter方法
            Image image = parseResponse(response.toString());
            if (image != null)  image.setImagePath(path);

            // 断开连接
            connection.disconnect();

            // 返回Image对象
            return image;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 解析响应JSON并将其转换为Image对象的方法
    private static Image parseResponse(String jsonResponse) {
        Image ret = new Image();
        try {
            ObjectMapper mapper = new ObjectMapper();
            ImageInfo imageInfo = mapper.readValue(jsonResponse, ImageInfo.class);
            ret.setHeight(imageInfo.getHeight());
            ret.setWidth(imageInfo.getWidth());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ImageInfo {
        private Integer size;
        private String format;
        private Integer width;
        private Integer height;
        private String colorModel;
    }
}
