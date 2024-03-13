package com.sishui.words.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sishui.words.pojo.Topic;
import com.sishui.words.service.*;
import com.sishui.words.util.DateFormat;
import com.sishui.words.vo.Constants;
import com.sishui.words.vo.HomeData;
import com.sishui.words.vo.Result;
import com.sishui.words.vo.TopicResponseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/posts")
public class PostController {
    private static final Long PAGE_SIZE = 10L;
    @Autowired
    private ITopicService topicService;
    @Autowired
    private ISubjectService subjectService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IForumService forumService;
    @Autowired
    private IImageService imageService;
    @Autowired
    private ICommentService commentService;

    /**
     * 获取最新的文章列表
     * @return Result
     */
    @PostMapping("list_last2")
    public Result getHomeListLast(@RequestBody HomeListRequest request) {
        Map<String, Object> data = new HashMap<>();

        //置顶数量
        data.put("sticky_count", 0);

        //话题列表
        List<TopicResponseVO> topicList = new ArrayList<>();
        IPage<Topic> page = new Page<>();
        page.setCurrent(request.offset);
        page.setSize(PAGE_SIZE);
        if (Objects.equals(request.getPostType(), "1")) {
            IPage<Topic> rawTopicList = topicService.page(page);
            for (Topic record : rawTopicList.getRecords()) {
                TopicResponseVO topicResponseVO = new TopicResponseVO();
                topicResponseVO.setAddress(record.getLocation());
                topicResponseVO.setId(record.getTopicId());
                topicResponseVO.setTime(DateFormat.timeStamp2DateString(record.getCreateTime()));
                topicResponseVO.setType("image");
                topicResponseVO.setSubjects(subjectService.getSubjectListByTopicId(record.getTopicId()));
                topicResponseVO.setAuthor(userService.getById(record.getUserId()));
                topicResponseVO.setLimit("free");
                topicResponseVO.setPostType(Constants.ZHUIGE_TOPIC.getValue());

                topicResponseVO.setForum(forumService.getForumByTopicId(record.getTopicId()));
                topicResponseVO.setExcerpt(record.getTopicDetail());
                topicResponseVO.setImages(imageService.getImageListById(record.getTopicId()));
                topicResponseVO.setLikeCount(record.getLikeCount());
                //设置评论数量
                topicResponseVO.setCommentCount(0L);
                //获取评论
                topicResponseVO.setComments(new ArrayList<>());

                topicList.add(topicResponseVO);
                //设置评论数量
                topicResponseVO.setCommentCount(commentService.getCommentCountById(record.getTopicId()));
                //获取评论
                //topicResponseVO.setComments(commentService.getCommentListByPostId(record.getTopicId()));

            }

        }

        data.put("topics", topicList);
        if (topicList.size() < PAGE_SIZE) {
            data.put("more", "nomore");
        } else {
            data.put("more", "more");
        }
        return Result.success(data);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class HomeListRequest {
        private Integer offset;
        private String os;
        private String postType;
    }

}
