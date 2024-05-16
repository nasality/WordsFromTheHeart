package com.sishui.words.controller;


import com.sishui.words.pojo.Topic;
import com.sishui.words.req.HomeListRequest;
import com.sishui.words.service.*;
import com.sishui.words.vo.Result;
import com.sishui.words.vo.TopicResponseVO;

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
    private IContentRecommendService recommendService;

    /**
     * 获取最新的文章列表
     * @return Result
     */
    @PostMapping("list_last2")
    public Result getHomeListLast(@RequestBody HomeListRequest request) {
        Map<String, Object> data = new HashMap<>();
        data.put("sticky_count", 0);

        List<Topic> topicList;
        if (request.getUserId() == null) {
            topicList = topicService.selectTopicPage(request);
        } else {
            topicList = recommendService.recommendTopics(request.getUserId(), request.getOffset(), request.getSize());
        }
        List<TopicResponseVO> topicResponseVOList = topicService.topicProcess(topicList);
        data.put("topics", topicResponseVOList);
        if (topicList.size() < PAGE_SIZE) {
            data.put("more", "nomore");
        } else {
            data.put("more", "more");
        }
        return Result.success(data);
    }
}
