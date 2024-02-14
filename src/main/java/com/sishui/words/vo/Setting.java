package com.sishui.words.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Setting extends BasicSetting{
    private String desc;
    private Integer notifyCount;

}
