package com.sishui.words.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Forum {
    @TableId("id")
    private Integer id;
    private String name;
    private Timestamp createTime;
    private Integer hotnessIndex;
    private Integer parentTabId;
}
