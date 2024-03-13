package com.sishui.words.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.sishui.words.pojo.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicResponseVO {
    private Integer id;
    private String excerpt;
    private Forum forum;
    private List<Image> images;
    private Integer likeCount;
    private String limit;
    private String address;
    private String postType;
    //置顶数量
    private Integer stick;
    private List<Subject> subjects;
    private String time;
    private String type;
    private List<User> atUsers;
    private User author;
    private Long commentCount;
    private List<Comment> comments;
    @TableField(exist = false, value = "0")
    private Integer isComment;
    @TableField(exist = false, value = "0")
    private Integer isFavorite;
    @TableField(exist = false, value = "0")
    private Integer isLike;
}
