package com.sishui.words.req;


import com.sishui.words.pojo.Follow;
import com.sishui.words.pojo.User;
import com.sishui.words.util.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户相关通用查询对象")
public class UserREQ extends BaseRequest<Follow> {

    @ApiModelProperty(value = "用户id")
    private String userId;
}
