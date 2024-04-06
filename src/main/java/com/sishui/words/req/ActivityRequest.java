package com.sishui.words.req;

import com.sishui.words.pojo.Activity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityRequest extends BaseRequest<Activity>{
    private String os;
    private String postType;
    private String userId;
}
