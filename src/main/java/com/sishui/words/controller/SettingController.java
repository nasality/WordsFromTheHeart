package com.sishui.words.controller;

import com.sishui.words.pojo.Tab;
import com.sishui.words.service.ITabService;
import com.sishui.words.vo.*;
import com.sishui.words.service.ITopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "配置")
@RestController
@RequestMapping("/setting")
public class SettingController {
    @Autowired
    private ITabService tabService;

    @ApiOperation("获取配置 全局")
    @PostMapping("/global")
    public Result getGlobal() {
        Setting setting = new Setting();
        setting.setDesc("社交网络平台");
        //TODO 未获取logo，未获取通知数量
        setting.setLogo("");
        setting.setNotifyCount(0);
        setting.setTitle("知心一言");

        return Result.success(setting);
    }

    @Autowired
    private ITopicService topicService;

    @ApiOperation("获取配置 首页")
    @PostMapping("/home")
    public Result getHome() {
        Map<String, Object> data = new HashMap<>();
        data.put("list_switch", 1);
        data.put("tab_type", 1);
        data.put("tabs", tabService.getHomeTabList());
        data.put("cur_tab", "1");
        data.put("tab_switch", 1);


        HomeData homeData = new HomeData();

        //热门话题  Redis 实现

        //推荐用户
        //推荐文章
        //Tab查询
        //TODO 未完成
        return Result.success(data);
    }

    @PostMapping("/mine")
    public Result getMine() {
        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> background = new HashMap<>();
        background.put("background", Constants.MINE_BACKGROUND.getValue());
        data.add(background);
        return Result.success(background);
    }

    @PostMapping("login")
    public Result login() {
        LoginSetting loginSetting = new LoginSetting();
        loginSetting.setTitle("知心一言");
        loginSetting.setBackground("https://q.zhuige.com/wp-content/uploads/2022/07/62778b1dc69ac.jpg");
        loginSetting.setLogo("http://s8gqejvtl.hb-bkt.clouddn.com/words-logo.png");
        loginSetting.setYhxy("/pages/base/page/page?page_id=19");
        loginSetting.setYszc("/pages/base/page/page?page_id=148");
        return Result.success(loginSetting);
    }

    @PostMapping("/create")
    public Result createSetting() {
        Map<String, Object> data = new HashMap<>();
        Map<String,List<Map<String, Object>>>  map = new HashMap<>();
        List<Map<String, Object>> dataList = new ArrayList<>();
        data.put("image", "https://q.zhuige.com/wp-content/uploads/2022/07/img.png");
        data.put("link", "/pages/bbs/post/post");
        data.put("require_login", 1);
        data.put("title", "图文");
        dataList.add(data);
        Map<String, Object> data1 = new HashMap<>();
        data1.put("image", "https://q.zhuige.com/wp-content/uploads/2022/07/sp.png");
        data1.put("link", "/pages/bbs/activity-publish/activity-publish");
        data1.put("require_login", 1);
        data1.put("title", "活动");
        dataList.add(data1);

        map.put("items", dataList);
        return Result.success(map);
    }
}
