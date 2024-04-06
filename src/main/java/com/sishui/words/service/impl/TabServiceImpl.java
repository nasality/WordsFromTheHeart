package com.sishui.words.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sishui.words.mapper.TabMapper;
import com.sishui.words.pojo.Tab;
import com.sishui.words.service.ITabService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TabServiceImpl extends ServiceImpl<TabMapper, Tab>  implements ITabService {
    @Override
    public List<Tab> getTabListSortByHotness() {
        QueryWrapper<Tab> wrapper = new QueryWrapper<>();
//        wrapper.orderByDesc("")
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<Tab> getHomeTabList() {
        List<Tab> ret = new ArrayList<>();
        Tab tab = new Tab(1, "全部", 1);
        ret.add(tab);
        Tab tab1 = new Tab(2, "活动", 2);
        ret.add(tab1);
        return ret;
    }

    @Override
    public List<Tab> getUserHomeTabs(String userId) {
        List<Tab> ret = new ArrayList<>();
        Tab tab = new Tab(1, "话题", 1);
        ret.add(tab);
        Tab tab1 = new Tab(2, "活动", 2);
        ret.add(tab1);
        return ret;
    }
}
