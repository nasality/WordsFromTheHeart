package com.sishui.words.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sishui.words.pojo.Forum;

import java.util.List;

public interface IForumService extends IService<Forum> {
    List<Forum> getListById(Integer id);
}
