package com.sishui.words.controller;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.sishui.words.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping("/get_storeToken")
    public Result getStoreToken() {
        Map<String, Object> data = new HashMap<>();
        data.put("uniqueCode", UUID.randomUUID());
        String accessKey = "21k7CBh1IXZYJluN2aARFB_eVJHLPfc022Lw2u14";
        String secretKey = "xaFbdMyfXHJi4rOI9ebuMHYNKAcWaP3Q11oJuzvW";
        String bucket = "wordsfrom";
        Auth auth = Auth.create(accessKey, secretKey);
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        long expireSeconds = 3600;
        String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
        data.put("upToken", upToken);
        data.put("keyPrefix", "images/userImages/");
        return Result.success(data);
    }
}
