package com.sishui.words.req;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseRequest<T> implements Serializable {

    @ApiModelProperty(value="页码",required = true)
    private long offset;

    @ApiModelProperty(value = "每页有多少数据",required = true)
    private long size = 10;

    /**
     * 封装分页对象
     * */
    @ApiModelProperty(hidden = true)
    public IPage<T> getPage(){
        return new Page<T>().setCurrent(this.offset).setSize(this.size);
    }
}
