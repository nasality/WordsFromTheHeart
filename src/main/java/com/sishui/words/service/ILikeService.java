package com.sishui.words.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sishui.words.pojo.Like;

public interface ILikeService extends IService<Like> {
    Integer getIsLikeByUserIdAndContentId(String userId, Integer topicId);
}
