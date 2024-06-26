package com.sishui.words.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.sishui.words.pojo.Follow;
import com.sishui.words.pojo.Tab;
import com.sishui.words.pojo.User;
import com.sishui.words.req.UserREQ;
import com.sishui.words.service.*;
import com.sishui.words.service.impl.TopicServiceImpl;
import com.sishui.words.service.impl.WeChatAuthServiceImpl;
import com.sishui.words.util.HttpRequest;
import com.sishui.words.vo.*;
import org.apache.ibatis.io.ResolverUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ITabService tabService;

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
            user.setAvatar(Constants.DEFAULT_AVATAR.getValue());
            userService.getBaseMapper().insert(user);
            //腾讯IM创建账号
            HttpRequest.accountImport(user.getUserId(), user.getNickname(), user.getAvatar());
        } else {
            //user.setNickname(nickname);
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


        QueryWrapper<Follow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("follower_id", userId);

        //获赞
        userDataVO.setLikeMeCount(topicService.countTotalLikesByUserId(userId));
        //粉丝
        userDataVO.setFansCount(userDetial.getFansCount());
        //关注
        userDataVO.setFollowCount(Math.toIntExact(followService.getBaseMapper().selectCount(queryWrapper)));
        //动态
        userDataVO.setPostCount(userService.getPostCount(userId));
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

    @PostMapping("/my_fans")
    public Result getUserFans(@RequestBody UserREQ userREQ) {
        if (userREQ == null || StringUtils.isEmpty(userREQ.getUserId())) {
           return Result.error("参数错误");
        }
        String userId = userREQ.getUserId();
        Map<String, Object> result = new HashMap<>();
        long offset = userREQ.getOffset();
        QueryWrapper<Follow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("follower_id", userId);

        User userDetial = userService.getById(userId);
        if (offset == 0) {
            result.put("follow_count", String.valueOf(Math.toIntExact(followService.getBaseMapper().selectCount(queryWrapper))));
            result.put("fan_count", String.valueOf(userDetial.getFansCount()));
        }

        int perPageCount = 12;
        userREQ.setSize(perPageCount);

        List<User> users = followService.getFollowUsers(userREQ);
        if (users.size() == 0) {
            result.put("users", new ArrayList<>());
            result.put("more", "nomore");
            return Result.success(result);
        }

        List<Map<String, Object>> data = new ArrayList<>();
        for (User user : users) {
            Map<String, Object> item = new HashMap<>();
            item.put("user_id", user.getUserId());
            item.put("nickname", user.getNickname());
            item.put("avatar", user.getAvatar());
            item.put("post_count", userService.getPostCount(userId));
            item.put("fans_count", user.getFansCount());


            if (userId != null) {
                boolean isFollow = followService.isFollowingUser(user.getUserId(), userId);
                item.put("is_follow", isFollow ? 1 : 0);
                item.put("is_fans", 1);
            } else {
                item.put("is_follow", 0);
                item.put("is_fans", 0);
            }

            data.add(item);
        }

        result.put("users", data);
        result.put("more", (data.size() == perPageCount ? "more" : "nomore"));

        return Result.success(result);
    }



    @PostMapping("/my_followers")
    public Result getMyFollowers(@RequestBody UserREQ userREQ) {
        if (userREQ == null || StringUtils.isEmpty(userREQ.getUserId())) {
            return Result.error("参数错误");
        }

        Map<String, Object> result = new HashMap<>();

        int perPageCount = 12;
        userREQ.setSize(perPageCount);
        List<User> users = followService.getFollowedUsers(userREQ);
        if (users.isEmpty()) {
            result.put("users", new ArrayList<>());
            result.put("more", "nomore");
            return Result.success(result);
        }

        List<Map<String, Object>> data = new ArrayList<>();
        for (User user : users) {
            Map<String, Object> item = new HashMap<>();
            item.put("user_id", user.getUserId());
            item.put("nickname", user.getNickname());
            item.put("avatar", user.getAvatar());
            item.put("post_count", userService.getPostCount(user.getUserId()));
            item.put("fans_count", user.getFansCount());

            item.put("is_follow", 1);
            boolean isFans = followService.isFollowingUser(user.getUserId(), userREQ.getUserId());
            item.put("is_fans", isFans ? 1 : 0);
            data.add(item);
        }

        result.put("users", data);
        result.put("more", (data.size() == perPageCount ? "more" : "nomore"));

        return Result.success(result);
    }

    @PostMapping("/set_info")
    public Result setUserInfo(@RequestBody User user) {
        if (user == null || user.getUserId() == null) {
            return Result.error("未登录");
        }
        //TODO 参数敏感信息校验
        if (StringUtils.isEmpty(user.getNickname())) {
            return Result.error("昵称不能为空");
        }

        User oldUser = userService.getById(user.getUserId());
        oldUser.setNickname(user.getNickname());
        oldUser.setSign(user.getSign());
        oldUser.setCover(user.getCover());
        oldUser.setAvatar(user.getAvatar());
        userService.updateById(oldUser);
        return Result.success();
    }

    @PostMapping("/init_info")
    public Result initUserInfo(@RequestBody User user) {
        User oldUser = userService.getUserById(user.getUserId());
        oldUser.setNickname(user.getNickname());
        oldUser.setAvatar(user.getAvatar());
        if (userService.updateById(oldUser)) {
            return Result.success("修改成功");
        } else {
            return Result.error("修改失败");
        }
    }

    @PostMapping("/home")
    public Result getUserHome(@RequestBody UserREQ userREQ) {
        if (userREQ == null) {
            return Result.error("参数为空");
        }
        String authorId = userREQ.getAuthorUserId();
        String userId = userREQ.getUserId();

        User userDetial = userService.getById(userREQ.getAuthorUserId());
        userDetial.setIsMe(Objects.equals(userId, authorId) ? 1 : 0);
        userDetial.setIsFollow(followService.getUserRelationship(userId, authorId));


        UserDataVO userDataVO = new UserDataVO();

        QueryWrapper<Follow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("follower_id", authorId);

        //获赞
        userDataVO.setLikeMeCount(topicService.countTotalLikesByUserId(authorId));
        //粉丝
        userDataVO.setFansCount(userDetial.getFansCount());
        //关注
        userDataVO.setFollowCount(Math.toIntExact(followService.getBaseMapper().selectCount(queryWrapper)));
        //动态
        userDataVO.setPostCount(userService.getPostCount(authorId));
        //昵称
        userDataVO.setNickname(userDetial.getNickname());
        //头像
        userDataVO.setAvatar(userDetial.getAvatar());
        Map<String, Object> ret = new HashMap<>();
        //tabs
        List<Tab> tabs = tabService.getUserHomeTabs(authorId);

        //私信按钮
        ret.put("btn_message", 1);
        ret.put("btn_promotion", 1);
        ret.put("stat", userDataVO);
        ret.put("user", userDetial);
        ret.put("tabs", tabs);
        return Result.success(ret);
    }

}
