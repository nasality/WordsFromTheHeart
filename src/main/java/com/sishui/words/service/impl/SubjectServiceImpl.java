package com.sishui.words.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sishui.words.mapper.SubjectMapper;
import com.sishui.words.pojo.Subject;
import com.sishui.words.pojo.SubjectContentRelation;
import com.sishui.words.service.ISubjectContentRelationService;
import com.sishui.words.service.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements ISubjectService {
    @Override
    public Subject getSubjectByName(String subject) {
        if (subject == null) {
            return null;
        }
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("name", subject);
        return baseMapper.selectOne(wrapper);
    }

    @Autowired
    private ISubjectContentRelationService subjectContentRelationService;

    @Override
    public List<Subject> getSubjectListByTopicId(Integer topicId) {
        if (topicId == null) return null;
        // Return data
        List<Subject> subjectList = new ArrayList<>();

        QueryWrapper<SubjectContentRelation> relationWrapper = new QueryWrapper<>();
        relationWrapper.eq("related_id", topicId);
        List<SubjectContentRelation> subjectContentRelations = subjectContentRelationService.getBaseMapper().selectList(relationWrapper);
        for (SubjectContentRelation subjectContentRelation : subjectContentRelations) {
            Subject subject = baseMapper.selectById(subjectContentRelation.getSubjectId());
            subjectList.add(subject);
        }
        return subjectList;
    }
}
