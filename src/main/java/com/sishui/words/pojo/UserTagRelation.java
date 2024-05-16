package com.sishui.words.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTagRelation {
    private String userId;
    private Integer tagId;
    private Integer hotnessIndex;
}
