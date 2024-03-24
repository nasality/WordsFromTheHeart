package com.sishui.words.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer postId;
    private String userId;
    private String content;
    private Integer likeCount;
    private Integer parentId;
    private Date time;
    @TableField(exist = false)
    private User user;
    @TableField(exist = false)
    private List<Comment> replys;
}
