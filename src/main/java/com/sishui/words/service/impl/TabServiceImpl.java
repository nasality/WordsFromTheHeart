package com.sishui.words.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sishui.words.mapper.TabMapper;
import com.sishui.words.pojo.Tab;
import com.sishui.words.service.ITabService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TabServiceImpl extends ServiceImpl<TabMapper, Tab>  implements ITabService {
    @Override
    public List<Tab> getTabListSortByHotness() {
        QueryWrapper<Tab> wrapper = new QueryWrapper<>();
//        wrapper.orderByDesc("")
        return baseMapper.selectList(wrapper);
    }
}
