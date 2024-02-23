package com.sishui.words.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tag")
public class Tag {
    private int tagId;
    private String tagName;
    private Timestamp createTime;
}
