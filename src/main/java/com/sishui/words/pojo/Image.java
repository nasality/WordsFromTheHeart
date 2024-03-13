package com.sishui.words.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @TableId("image_id")
    private Integer imageId;
    private Integer topicId;
    private String imagePath;
    private Integer width;
    private Integer height;
}
