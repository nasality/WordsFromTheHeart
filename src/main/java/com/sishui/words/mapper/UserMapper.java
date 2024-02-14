package com.sishui.words.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sishui.words.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<User> selectUsersWithoutSelfFollow(@Param("userId") Integer userId);
}