package com.sishui.words.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sishui.words.pojo.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TopicMapper extends BaseMapper<Topic> {
    Integer countTotalLikesByUserId(@Param("userId") String userId);

    @Select("SELECT topic_id FROM topic")
    List<Integer> getAllTopicIds();
}
