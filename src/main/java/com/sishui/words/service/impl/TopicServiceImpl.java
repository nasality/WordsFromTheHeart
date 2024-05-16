package com.sishui.words.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sishui.words.mapper.TopicMapper;
import com.sishui.words.pojo.Image;
import com.sishui.words.pojo.Topic;
import com.sishui.words.req.HomeListRequest;
import com.sishui.words.service.*;
import com.sishui.words.util.DateFormat;
import com.sishui.words.vo.Constants;
import com.sishui.words.vo.TopicResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements ITopicService , IContentService {
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


    @Override
    public long getContentCount(String userId) {
        QueryWrapper<Topic> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return baseMapper.selectCount(wrapper);
    }

    public Integer countTotalLikesByUserId(String userId) {
        return baseMapper.countTotalLikesByUserId(userId);
    }

    @Override
    public Boolean updateTopicLike(Integer topicId) throws Exception {
        if (topicId == null) {
            throw new NullPointerException();
        }
        int update = 0;
        try {
            Topic topic = baseMapper.selectById(topicId);
            topic.setLikeCount(topic.getLikeCount() + 1);
            update = baseMapper.updateById(topic);
        } catch (Exception e) {
            throw new Exception();
        }
        return update > 0;
    }

    @Override
    public List<Topic> selectTopicPage(HomeListRequest request) {
        //TODO 增加limit时需修改
        QueryWrapper<Topic> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        IPage<Topic> page = baseMapper.selectPage(request.getPage(), wrapper);
        return page.getRecords();
    }

    @Override
    public boolean decreaseTopicLike(Integer postId) throws Exception {
        if (postId == null) {
            throw new NullPointerException();
        }
        int update = 0;
        try {
            Topic topic = baseMapper.selectById(postId);
            topic.setLikeCount(topic.getLikeCount() - 1);
            update = baseMapper.updateById(topic);
        } catch (Exception e) {
            throw new Exception();
        }
        return update > 0;
    }

    @Override
    public List<TopicResponseVO> topicProcess(List<Topic> rawTopicList) {
        //话题列表
        List<TopicResponseVO> topicList = new ArrayList<>();
        for (Topic record : rawTopicList) {
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
            List<Image> images = imageService.getImageListById(record.getTopicId());
            List<Map<String, Image>> imagesMapList = this.getImageMapList(images);
            topicResponseVO.setImages(imagesMapList);
            topicResponseVO.setLikeCount(record.getLikeCount());
            //设置评论数量
            topicResponseVO.setCommentCount(0L);
            //获取评论
            topicResponseVO.setComments(commentService.getCommentListByPostId(record.getTopicId()));

            topicList.add(topicResponseVO);
            //设置评论数量
            topicResponseVO.setCommentCount(commentService.getCommentCountById(record.getTopicId()));
            //获取评论
            //topicResponseVO.setComments(commentService.getCommentListByPostId(record.getTopicId()));

        }
        return topicList;
    }

    @Override
    public List<String> getAllTopicId() {

        return null;
    }

    @Override
    public List<Integer> getAllTopicIds() {
        return baseMapper.getAllTopicIds();
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
