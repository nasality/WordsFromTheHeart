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
        Map<String, Object> ret = new HashMap<>();
        ret.put("background", Constants.MINE_BACKGROUND.getValue());

        List<Map<String, Object>> menusList = new ArrayList<>();
        Map<String, Object> menusMap1 = new HashMap<>();
        ret.put("menus", menusList);
        menusMap1.put("title", "我的服务");
        List<Map<String, Object>> itemsList = new ArrayList<>();
        menusMap1.put("items", itemsList);

        Map<String, Object> menu1 = new HashMap<>();
        menu1.put("image", "https://q.zhuige.com/wp-content/uploads/2022/09/Q3.png");
        menu1.put("switch", 1);
        menu1.put("link", "/pages/user/home/home?tab=publish");
        menu1.put("title", "作品");
        menu1.put("type", "link");
        itemsList.add(menu1);

        Map<String, Object> menu2 = new HashMap<>();
        menu2.put("image", "https://q.zhuige.com/wp-content/uploads/2022/09/Q4.png");
        menu2.put("switch", 1);
        menu2.put("link", "/pages/user/info/info");
        menu2.put("title", "个人资料");
        menu2.put("type", "link");
        itemsList.add(menu2);

        Map<String, Object> menu3 = new HashMap<>();
        menu3.put("image", "https://q.zhuige.com/wp-content/uploads/2022/09/Q2.png");
        menu3.put("switch", 1);
        menu3.put("link", "/pages/user/friend/friend?tab=follow");
        menu3.put("title", "粉丝");
        menu3.put("type", "link");
        itemsList.add(menu3);

        Map<String, Object> menu4 = new HashMap<>();
        menu4.put("image", "https://q.zhuige.com/wp-content/uploads/2022/09/Q2.png");
        menu4.put("switch", 1);
        menu4.put("link", "/pages/user/friend/friend?tab=follow");
        menu4.put("title", "关注");
        menu4.put("type", "link");
        itemsList.add(menu4);
        menusList.add(menusMap1);

        Map<String, Object> menusMap2 = new HashMap<>();
        ret.put("menus", menusList);
        menusMap2.put("title", "官方服务");
        List<Map<String, Object>> itemsList2 = new ArrayList<>();
        menusMap2.put("items", itemsList2);

        Map<String, Object> menu5 = new HashMap<>();
        menu5.put("image", "https://q.zhuige.com/wp-content/uploads/2022/09/W1.png");
        menu5.put("switch", 1);
        menu5.put("link", "/pages/base/about/about");
        menu5.put("title", "关于");
        menu5.put("type", "link");
        itemsList2.add(menu5);

        Map<String, Object> menu6 = new HashMap<>();
        menu6.put("image", "https://q.zhuige.com/wp-content/uploads/2022/09/W5.png");
        menu6.put("switch", 1);
        menu6.put("link", "/pages/base/about/about?page_id=237");
        menu6.put("title", "使用协议");
        menu6.put("type", "link");
        itemsList2.add(menu6);

        Map<String, Object> menu7 = new HashMap<>();
        menu7.put("image", "https://q.zhuige.com/wp-content/uploads/2022/07/11Q.png");
        menu7.put("switch", 1);
        menu7.put("link", "/pages/wpmall/index/index");
        menu7.put("title", "评分");
        menu7.put("type", "score");
        itemsList2.add(menu7);

        Map<String, Object> menu8 = new HashMap<>();
        menu8.put("image", "https://q.zhuige.com/wp-content/uploads/2023/10/2222.png");
        menu8.put("switch", 1);
        menu8.put("link", "/pages/user/friend/friend?tab=follow");
        menu8.put("title", "退出登录");
        menu8.put("type", "clear");
        itemsList2.add(menu8);
        menusList.add(menusMap2);


        return Result.success(ret);
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
