package com.sishui.words.controller;

import com.sishui.words.pojo.Tab;
import com.sishui.words.service.IForumService;
import com.sishui.words.service.ITabService;
import com.sishui.words.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bbs")
public class TabController {

    @Autowired
    private ITabService tabService;

    @PostMapping("forum_cats")
    public Result getForumCats() {

        Map<String, Object> data = new HashMap<>();
        List<Tab> tabList = tabService.getTabListSortByHotness();
        data.put("tabs", tabList);
        data.put("cur_tab", tabList.get(0).getId());
        return Result.success(data);
    }
}
