package com.sishui.words.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sishui.words.pojo.User;
import com.sishui.words.req.UserREQ;

import java.util.List;

public interface IUserService extends IService<User> {
    List<User> recommendUsers(Integer userId);
    List<User> getUsersWithoutSelfFollow(Integer userId);

    int getPostCount(String userId);

    int getUserFansCount(String userId);


}
