package com.sishui.words.vo;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultEnum {
    SUCCESS(0, "成功"),

    ERROR(999, "错误"),

    UNAUTHENTICATED(401, "请先通过身份认证"), AUTH_FAIL(1400, "认证失败"),

    USER_NOT_EXSIT(402,"用户不存在"),

    // token异常
    TOKEN_PAST(1401, "身份过期，请求重新登录！"), TOKEN_ERROR(1402, "令牌错误"),

    HEADEA_ERROR(1403, "参数错误"),

    AUTH_USERNAME_NONE(1405, "用户名不能为空"), AUTH_PASSWORD_NONE(1406, "密码不能为空"),

    MENU_NO(306, "无权限");


    private Integer code;
    private String desc;
}
