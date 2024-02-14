package com.sishui.words.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @TableId("user_id")
    private String userId;
    private String nickname;
    private String mobile;
    private String password;
    private String gender;
    private String avatar;
    private Integer fansCount;
    private String sign;
    private Timestamp registrationDate;
    private Timestamp lastLoginTime;
    private Date birthday;
    private String role;
    @TableField(exist = false)
    private Integer first;
    private String cover;
}
