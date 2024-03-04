package com.sishui.words.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicVO {
    private String type;
    private String userId;
    private String content;
    private Double latitude;
    private Double longitude;
    private String address;
    private Integer forumId;
    private List<String> atList;
    private List<String> images;
    private List<String> subjects;
}
