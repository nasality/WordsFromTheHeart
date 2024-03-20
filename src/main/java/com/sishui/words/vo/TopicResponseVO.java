package com.sishui.words.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.sishui.words.pojo.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicResponseVO {
    private Integer id;
    private String excerpt;
    private Forum forum;
    private List<Map<String, Image>> images;
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
    @TableField(exist = false)
    private Integer isComment;
    @TableField(exist = false)
    private Integer isFavorite;
    @TableField(exist = false)
    private Integer isLike;
}
