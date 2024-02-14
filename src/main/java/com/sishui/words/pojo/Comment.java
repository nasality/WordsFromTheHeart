package com.sishui.words.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private int commentId;
    private int postId;
    private int userId;
    private String commentContent;
    private int likeCount;
    private int parentCommentId;
    private Timestamp commentTime;
}
