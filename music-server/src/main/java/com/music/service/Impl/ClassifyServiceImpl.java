package com.music.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.entity.Carousel;
import com.music.entity.Classify;
import com.music.mapper.CarouselMapper;
import com.music.mapper.ClassifyMapper;
import com.music.service.ClassifyService;
import org.springframework.stereotype.Service;

/**
 * @author lyj
 * @data 2022/6/26 16:54
 */
@Service
public class ClassifyServiceImpl extends ServiceImpl<ClassifyMapper, Classify> implements ClassifyService {
}
