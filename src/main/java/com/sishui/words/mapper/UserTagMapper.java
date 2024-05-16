package com.sishui.words.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sishui.words.pojo.UserTag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserTagMapper extends BaseMapper<UserTag> {
    List<List<UserTag>> getAllUserTags();
}
