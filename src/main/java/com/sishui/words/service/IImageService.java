package com.sishui.words.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sishui.words.pojo.Image;

import java.util.List;

public interface IImageService extends IService<Image> {
    List<Image> getImageListById(Integer topicId);
}
