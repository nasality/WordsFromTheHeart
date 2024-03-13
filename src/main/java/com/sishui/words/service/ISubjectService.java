package com.sishui.words.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sishui.words.pojo.Subject;

import java.util.List;


public interface ISubjectService extends IService<Subject> {
    Subject getSubjectByName(String subject);

    List<Subject> getSubjectListByTopicId(Integer topicId);
}
