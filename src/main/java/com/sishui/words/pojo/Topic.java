package com.sishui.words.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.sishui.words.dto.Content;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic extends Content {
    @TableId("topic_id")
    private Integer topicId;
    private String userId;
    private Integer likeCount;
    private String location;
    private Timestamp createTime;
    private String summary;
    private String topicDetail;

    /*private int id;
    private String excerpt;
    private String time;
    private String postType;
    private int stick;
    private String limit;
    private int likeCount;
    private int commentCount;
    private List<Comment> comments;
    private User author;
    private List<User> atUsers;
    private List<Subject> subjects;
    private Forum forum;
    private Location location;
    private List<Image> images;
    private String type;*/
}
