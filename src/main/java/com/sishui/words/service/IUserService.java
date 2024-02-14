package com.sishui.words.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sishui.words.pojo.User;

import java.util.List;

public interface IUserService extends IService<User> {
    List<User> recommendUsers(Integer userId);
    List<User> getUsersWithoutSelfFollow(Integer userId);
}
