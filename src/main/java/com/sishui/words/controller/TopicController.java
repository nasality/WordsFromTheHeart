package com.sishui.words.controller;

import com.sishui.words.pojo.*;
import com.sishui.words.service.*;
import com.sishui.words.util.DateFormat;
import com.sishui.words.util.HttpRequest;
import com.sishui.words.vo.Constants;
import com.sishui.words.vo.Result;

import com.sishui.words.vo.TopicResponseVO;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/bbs")
public class TopicController {

    @Autowired
    private IForumService forumService;
    @Autowired
    private ITopicService topicService;
    @Autowired
    private IImageService imageService;
    @Autowired
    private ITopicForumRelationService topicForumRelationService;
    @Autowired
    private ISubjectContentRelationService subjectContentRelationService;
    @Autowired
    private ISubjectService subjectService;

    @PostMapping("list_cat")
    public Result getListCat(@RequestBody Forum forum) {
        if (forum == null || forum.getId() == null) {
            return Result.error("参数错误");
        }

        List<Forum> forumList = forumService.getListById(forum.getId());
        Map<String, Object> data = new HashMap<>();
        data.put("forums", forumList);
        return Result.success(data);
    }

    @PostMapping("topic_create")
    public Result topicPost(@RequestBody TopicRequest topicRequest) {
        if (topicRequest.getForumId() == null) {
            return Result.error("请选择圈子");
        }
        //TODO 异常处理
        try {
            //创建新话题
            Topic topic = new Topic();
            topic.setUserId(topicRequest.getUserId());
            topic.setLikeCount(0);
            topic.setLocation(topicRequest.getMarker());
            topic.setCreateTime(new Timestamp(System.currentTimeMillis()));
            topic.setTopicDetail(topicRequest.getContent());
            topicService.save(topic);

            //创建图片
            for (String image : topicRequest.getImages()) {
                Image newImage = HttpRequest.getImageInfo(image);
                assert newImage != null;
                newImage.setTopicId(topic.getTopicId());
                imageService.save(newImage);
            }


            //圈子
            if (topicRequest.getForumId() != null) {
                TopicForumRelation topicForumRelation = new TopicForumRelation();
                topicForumRelation.setTopicId(topic.getTopicId());
                topicForumRelation.setForumId(topicRequest.getForumId());
                topicForumRelationService.save(topicForumRelation);
            }

            //字符串分割，解析出单个话题
            String[] subjects = topicRequest.getSubjects().split("-0-");
            for (String subject : subjects) {
                Subject oldSubject;
                if ((oldSubject = subjectService.getSubjectByName(subject)) == null) {
                    oldSubject = new Subject();
                    oldSubject.setName(subject);
                    oldSubject.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    subjectService.save(oldSubject);
                }
                //话题
                SubjectContentRelation subjectContentRelation = new SubjectContentRelation();
                subjectContentRelation.setSubjectId(oldSubject.getSubjectId());
                subjectContentRelation.setRelatedId(topic.getTopicId());
                subjectContentRelationService.save(subjectContentRelation);
            }
        } catch (Exception e) {
            System.out.println(e);
            return Result.error("服务器异常");
        }

        return Result.success();
    }


    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    private static class TopicRequest {
        private String userId;
        private String type;
        private String content;
        private double latitude;
        private double longitude;
        private String marker;
        private String address;
        private Integer score;
        private Integer forumId;
        private String atList;
        private List<String> images;
        private String video;
        private String videoCover;
        private String subjects;
    }

    @Autowired
    private IUserService userService;
    @Autowired
    private IFollowService followService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private ILikeService likeService;

    @PostMapping("topic_detail")
    public Result getTopicDetail(@RequestBody TopicDetailRequest request) {
        if (request == null || request.getTopicId() == null) {
            return Result.error("缺少参数");
        }



        Topic topic = topicService.getById(request.topicId);

        if (topic == null) {
            return Result.error("话题不存在");
        }

        Map<String, Object> data = new HashMap<>();
        //是否开放举报窗口
        data.put("is_report", true);
        //data.put("limit", "free");



        //封装Topic返回结果
        TopicResponseVO topicResponse = new TopicResponseVO();
        //浏览用户
        User user = userService.getById(topic.getUserId());
        topicResponse.setAuthor(user);

        //检查是否已关注作者
        if (request.getUserId() != null) {
            boolean isFollow = followService.isFollowingUser(request.getUserId(), user.getUserId());
            user.setIsFollow(isFollow ? 1 : 0);
            //是否已评论，是否已点赞，是否已收藏
            topicResponse.setIsComment(commentService.getIsCommentByUserIdAndTopicId(user.getUserId(), topic.getTopicId()));
            topicResponse.setIsLike(likeService.getIsLikeByUserIdAndContentId(user.getUserId(), topic.getTopicId()));
            //是否收藏暂不添加
            topicResponse.setIsFavorite(0);
        } else {
            topicResponse.setIsComment(0);
            topicResponse.setIsLike(0);
            topicResponse.setIsFavorite(0);
        }

        //圈子
        topicResponse.setForum(forumService.getForumByTopicId(request.getTopicId()));
        //位置
        topicResponse.setAddress(topic.getLocation());


        //ID
        topicResponse.setId(topic.getTopicId());
        List<Image> images = imageService.getImageListById(topic.getTopicId());
        List<Map<String, Image>> imagesMapList = this.getImageMapList(images);
        //设置图片
        topicResponse.setImages(imagesMapList);

        //评论数量
        topicResponse.setCommentCount(commentService.getCommentCountById(topic.getTopicId()));
        //话题
        topicResponse.setSubjects(subjectService.getSubjectListByTopicId(topic.getTopicId()));
        //时间
        topicResponse.setTime(DateFormat.timeStamp2DateString(topic.getCreateTime()));
        //类型
        topicResponse.setType("image");
        //内容
        topicResponse.setExcerpt(topic.getTopicDetail());
        //点赞数量
        topicResponse.setLikeCount(topic.getLikeCount());
        topicResponse.setPostType(Constants.ZHUIGE_TOPIC.getValue());
        topicResponse.setLimit("free");
        //评论内容
        topicResponse.setComments(commentService.getCommentListByPostId(topic.getTopicId()));
        data.put("topic", topicResponse);
        return Result.success(data);
    }

    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    private static class TopicDetailRequest {
        private String os;
        private String token;
        private Integer topicId;
        private String userId;
    }

    private List<Map<String, Image>> getImageMapList(List<Image> images) {
        List<Map<String, Image>> ret = new ArrayList<>();
        for (Image image : images) {
            Map<String, Image> map = new HashMap<>();
            map.put("image", image);
            ret.add(map);
        }
        return ret;
    }


}
