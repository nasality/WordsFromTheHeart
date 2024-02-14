package com.sishui.words.controller;

import com.sishui.words.vo.LoginSetting;
import com.sishui.words.vo.Setting;
import com.sishui.words.service.ITopicService;
import com.sishui.words.vo.HomeData;
import com.sishui.words.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "配置")
@RestController
@RequestMapping("/setting")
public class SettingController {


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
        HomeData homeData = new HomeData();

        //热门话题  Redis 实现

        //推荐用户
        //推荐文章
        //Tab查询
        //TODO 未完成
        return null;
    }

    @PostMapping("login")
    private Result login() {
        LoginSetting loginSetting = new LoginSetting();
        loginSetting.setTitle("知心一言");
        loginSetting.setBackground("https://q.zhuige.com/wp-content/uploads/2022/07/62778b1dc69ac.jpg");
        loginSetting.setLogo("http://s8gqejvtl.hb-bkt.clouddn.com/words-logo.png");
        loginSetting.setYhxy("/pages/base/page/page?page_id=19");
        loginSetting.setYszc("/pages/base/page/page?page_id=148");
        return Result.success(loginSetting);
    }
}
