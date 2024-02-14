package com.sishui.words.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoginSetting extends BasicSetting{
    private String background;
    private String yhxy;
    private String yszc;
}
