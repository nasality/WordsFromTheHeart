package com.sishui.words.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("subject")
public class Subject {
    @TableId("subject_id")
    private int subjectId;
    private String name;
    private Timestamp createTime;
}
