package com.sishui.words.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sishui.words.mapper.UserTagRelationMapper;
import com.sishui.words.pojo.UserTagRelation;
import com.sishui.words.service.IUserTagRelationService;
import org.springframework.stereotype.Service;

@Service
public class UserTagRelationServiceImpl extends ServiceImpl<UserTagRelationMapper, UserTagRelation> implements IUserTagRelationService {
}
