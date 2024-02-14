package com.sishui.words.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    private int tagId;
    private String tagName;
    private Timestamp createTime;
}
