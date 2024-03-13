package com.sishui.words.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Integer id;
    private Integer postId;
    private String userId;
    private String content;
    private Integer like;
    private Integer parentId;
    private Date time;
    @TableField(exist = false)
    private User user;
    @TableField(exist = false)
    private Comment replys;
}
