package com.sishui.words.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tab {
    private Integer id;
    private String name;
    private Integer hotnessIndex;
}
