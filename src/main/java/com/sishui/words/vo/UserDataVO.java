package com.sishui.words.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataVO {
    private Integer postCount;
    private Integer fansCount;
    private Integer followCount;
    private Integer likeMeCount;
    private String nickname;
    private String avatar;
    private boolean certify;
    private boolean vip;
}
