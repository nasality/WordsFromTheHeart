package com.sishui.words.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    @TableId(value = "activity_id", type = IdType.AUTO)
    private Integer activityId;
    private String userId;
    private String title;
    private String content;
    private Timestamp startTime;
    private Timestamp endTime;
    private String address;
    private String marker;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private Integer maxParticipants;
    private Integer currentParticipants;
    private Byte activityStatus;
    private Timestamp createTime;
    @TableField(exist = false)
    private List<Image> images;
    @TableField(exist = false)
    private Forum forum;
    //true已参加
    @TableField(exist = false)
    private Boolean attend;
    @TableField(exist = false)
    private Boolean end;
    @TableField(exist = false)
    private String postType = "zhuige_activity";
}
