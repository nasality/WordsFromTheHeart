package com.sishui.words.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sishui.words.pojo.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Random;

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

    public static boolean addFriend(String fromAccount, String toAccount) {
        Long sdkAppId = 1600029273L;
        String identifier = "administrator";
        String userSig = getUserSig(sdkAppId, identifier);
        long random = getRandom();
        String requestUrl = "https://console.tim.qq.com/v4/sns/friend_add?sdkappid=" + sdkAppId + "&identifier=" + identifier + "&usersig=" + userSig + "&random=" + random + "&contenttype=json";

        String addSource = "AddSource_Type_ZhiXin";

        try {
            String requestBody = "{\"From_Account\":\"" + fromAccount + "\",\"AddFriendItem\":[{\"To_Account\":\"" + toAccount + "\",\"AddSource\":\"" + addSource + "\"}],\"AddType\":\"Add_Type_Both\",\"ForceAddFlags\":1}";

            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            os.write(requestBody.getBytes());
            os.flush();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 可以根据需要处理响应内容，这里只是简单返回成功
                return true;
            } else {
                System.out.println("Failed to add friend. Response code: " + responseCode);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String getUserSig(Long sdkAppId, String identifier) {
        String key = "cc7a20636f9f10e8b02acbc6fe1788b98338954add4e099a0f1a5de474854deb";
        TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(sdkAppId, key);
        long expire = 5184000L;
        try {
            return tlsSigAPIv2.genUserSig(identifier, expire);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static long getRandom() {
        Random random = new Random();

        // 生成随机的32位无符号整数
        return (long)random.nextInt(Integer.MAX_VALUE) + ((long)random.nextInt(Integer.MAX_VALUE) << 32);
    }

    public static String accountImport(String userId, String nick, String faceUrl) {
        long sdkAppId = 1600029273L;
        String identifier = "administrator";
        String content = "{\"UserID\":\"" + userId + "\",\"Nick\":\"" + nick + "\",\"FaceUrl\":\"" + faceUrl + "\"}";
        String userSig = getUserSig(sdkAppId, identifier);
        long random = getRandom();

        try {
            String baseUrl = "https://console.tim.qq.com/v4/im_open_login_svc/account_import";
            String urlString = baseUrl + "?sdkappid=" + sdkAppId + "&identifier=" + identifier + "&usersig=" + userSig + "&random=" + random + "&contenttype=json";

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            String encoded = Base64.getEncoder().encodeToString(content.getBytes());
            connection.getOutputStream().write(encoded.getBytes());

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String deleteFriends(String fromAccount, String[] toAccounts) {
        long sdkAppId = 1600029273L;
        String identifier = "administrator";
        String userSig = getUserSig(sdkAppId, identifier);
        long random = getRandom();
        String deleteType = "Delete_Type_Both";
        try {
            String baseUrl = "https://console.tim.qq.com/v4/sns/friend_delete";
            String urlString = baseUrl + "?sdkappid=" + sdkAppId + "&identifier=" + identifier + "&usersig=" + userSig + "&random=" + random + "&contenttype=json";

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // 构造请求体参数
            String requestBody = "{\"From_Account\":\"" + fromAccount + "\",\"To_Account\":[";
            for (int i = 0; i < toAccounts.length; i++) {
                if (i > 0) {
                    requestBody += ",";
                }
                requestBody += "\"" + toAccounts[i] + "\"";
            }
            requestBody += "],\"DeleteType\":\"" + deleteType + "\"}";

            String encoded = Base64.getEncoder().encodeToString(requestBody.getBytes());
            connection.getOutputStream().write(encoded.getBytes());

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
