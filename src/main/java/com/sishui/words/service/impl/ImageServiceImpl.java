package com.sishui.words.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sishui.words.mapper.ImageMapper;
import com.sishui.words.pojo.Image;
import com.sishui.words.service.IImageService;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements IImageService {
}
