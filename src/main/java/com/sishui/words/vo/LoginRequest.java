package com.sishui.words.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String code;
    private String nickname;
    private String avatar;
    private String channel;
    private String fromUserId;
}
