package com.sishui.words.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.sishui.words.pojo.Follow;
import com.sishui.words.pojo.User;
import com.sishui.words.service.IContentService;
import com.sishui.words.service.IFollowService;
import com.sishui.words.service.ITopicService;
import com.sishui.words.service.IUserService;
import com.sishui.words.service.impl.TopicServiceImpl;
import com.sishui.words.service.impl.WeChatAuthServiceImpl;
import com.sishui.words.vo.Constants;
import com.sishui.words.vo.LoginRequest;
import com.sishui.words.vo.Result;
import com.sishui.words.vo.UserDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ApplicationContext applicationContext;

    @PostMapping("/login")
    private Result login(@RequestBody LoginRequest loginRequest) throws Exception {
        String code = loginRequest.getCode();
        String nickname = loginRequest.getNickname();
        String channel = loginRequest.getChannel();

        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(channel)) {
            return Result.error("缺少参数");
        }

        String appId = "wx11b98d2e426a9e3f";
        String appSecret = "d7a1630b65bf6c56e445a94c673fdc97";
        Map<String, String> session = WeChatAuthServiceImpl.code2Session(appId, appSecret, code);
        if (session == null) {
            return Result.error("登录失败");
        }
        User user = userService.getById(session.get("openid"));
        if (user == null) {
            user = new User();
            user.setUserId(session.get("openid"));
            user.setNickname(nickname);
            user.setRegistrationDate(Timestamp.from(Instant.now()));
            user.setLastLoginTime(Timestamp.from(Instant.now()));
            user.setFirst(1);
            userService.getBaseMapper().insert(user);
        } else {
            user.setNickname(nickname);
            user.setLastLoginTime(Timestamp.from(Instant.now()));
            user.setFirst(0);
            userService.getBaseMapper().updateById(user);
        }

        return Result.success(user);
    }

    @Autowired
    private IFollowService followService;

    @Autowired
    private ITopicService topicService;

    @PostMapping("/my_statistics")
    public Result getMyStatistics(@RequestBody User user) {
        //FIXME 未测试的接口
        if (user == null) {
            return Result.error("参数为空");
        }
        String userId = user.getUserId();
        if (StringUtils.isEmpty(userId)) {
            return Result.error("用户不存在");
        }

        User userDetial = userService.getById(userId);
        user.setPassword(null);

        UserDataVO userDataVO = new UserDataVO();

        // 获取所有实现了 IContentService 接口的 bean
        Map<String, IContentService> map = applicationContext.getBeansOfType(IContentService.class);
        // 遍历 Map 集合
        //动态数量
        int sumOfCount = 0;
        for (Map.Entry<String, IContentService> entry : map.entrySet()) {
            IContentService contentService = entry.getValue(); // 获取 bean 实例
            // 调用 IContentService 接口的 get 方法
            sumOfCount += contentService.getContentCount(user.getUserId());
        }
        QueryWrapper<Follow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("follower_id", userId);

        //获赞
        userDataVO.setLikeMeCount(topicService.countTotalLikesByUserId(userId));
        //粉丝
        userDataVO.setFansCount(userDetial.getFansCount());
        //关注
        userDataVO.setFollowCount(Math.toIntExact(followService.getBaseMapper().selectCount(queryWrapper)));
        //动态
        userDataVO.setPostCount(sumOfCount);
        //昵称
        userDataVO.setNickname(userDetial.getNickname());
        //头像
        userDataVO.setAvatar(userDetial.getAvatar());
        return Result.success(userDataVO);
    }

    // 查询所有用户
    // 根据ID查询用户
    @PostMapping("/get_info")
    public Result getUserById(@RequestBody User user) {
        if (user == null || user.getUserId() == null) {
            return Result.error("参数错误");
        }
        User ans = userService.getBaseMapper().selectById(user.getUserId());
        if (ans.getCover() == null) {
            ans.setCover(Constants.DEFAULT_COVER.getValue());
        }



        return Result.success(ans);
    }

   /* @GetMapping("/")
    public Result getAllUsers() {
        List<User> users = userService.getAllUsers();
    }

    // 创建用户
    @PostMapping("/")
    public Result createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
    }

    // 更新用户信息
    @PutMapping("/{userId}")
    public Result updateUser(@PathVariable int userId, @RequestBody User user) {
        User updatedUser = userService.updateUser(userId, user);
    }

    // 删除用户
    @DeleteMapping("/{userId}")
    public Result deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
    }*/
}
