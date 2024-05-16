package com.sishui.words.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sishui.words.pojo.Topic;
import com.sishui.words.req.HomeListRequest;
import com.sishui.words.vo.TopicResponseVO;

import java.util.List;

public interface ITopicService extends IService<Topic> {
    Integer countTotalLikesByUserId(String userId);
    Boolean updateTopicLike(Integer topicId) throws Exception;

    List<Topic> selectTopicPage(HomeListRequest request);

    boolean decreaseTopicLike(Integer postId)throws Exception;

    List<TopicResponseVO> topicProcess(List<Topic> rawTopicList);

    List<String> getAllTopicId();

    List<Integer> getAllTopicIds();
}
