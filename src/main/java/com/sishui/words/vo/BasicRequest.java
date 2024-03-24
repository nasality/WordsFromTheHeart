package com.sishui.words.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicRequest {
    private String os;
    private Integer postId;
    private String token;
    private String userId;
}
