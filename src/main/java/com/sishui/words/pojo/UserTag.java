package com.sishui.words.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_tag")
public class UserTag {
    private String userId;
    private Integer forumId;
    private Integer hotnessIndex;
}
