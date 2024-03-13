package com.sishui.words.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sishui.words.pojo.Tab;

import java.util.List;

public interface ITabService extends IService<Tab> {
    List<Tab> getTabListSortByHotness();

    List<Tab> getHomeTabList();
}
