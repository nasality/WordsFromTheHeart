package com.sishui.words.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityParticipant {
    private Integer id;
    private Integer activityId;
    private String participantId;
    private Timestamp registrationTime;
}
