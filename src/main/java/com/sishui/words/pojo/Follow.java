package com.sishui.words.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Follow {
    @TableId("follow_id")
    private Integer followId;
    private String  followerId;
    private String followedId;
    private Timestamp followTime;
}
