package com.sishui.words.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sishui.words.mapper.SubjectMapper;
import com.sishui.words.pojo.Subject;
import com.sishui.words.service.ISubjectService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
}
