package com.sishui.words.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private int notificationId;
    private Integer receiverUserId;
    private String notificationType;
    private Integer relatedId;
    private String notificationContent;
    private Timestamp notificationTime;
    private Boolean isRead;
}
