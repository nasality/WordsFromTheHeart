package com.sishui.words.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sishui.words.pojo.Topic;

public interface ITopicService extends IService<Topic> {
    Integer countTotalLikesByUserId(String userId);
}
