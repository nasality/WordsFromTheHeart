package com.sishui.words.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    private int activityId;
    private int userId;
    private String activityTitle;
    private String activityDescription;
    private Timestamp activityStartTime;
    private Timestamp activityEndTime;
    private String activityLocation;
    private int maxParticipants;
    private int currentParticipants;
    private String activityStatus;
    private Timestamp createTime;
}
