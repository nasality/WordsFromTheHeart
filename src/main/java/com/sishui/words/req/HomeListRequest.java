package com.sishui.words.req;

import com.sishui.words.pojo.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeListRequest extends BaseRequest<Topic>{
    private String os;
    private String postType;
}
