package com.sishui.words.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sishui.words.pojo.UserTag;

import java.util.List;

public interface IUserTagService extends IService<UserTag> {
    List<UserTag> getUserTags(String userId);

    List<List<UserTag>> getAllUserTags();
}
