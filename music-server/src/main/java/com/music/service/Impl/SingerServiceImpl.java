package com.music.service.Impl;

import com.music.entity.Singer;
import com.music.mapper.SingerMapper;
import com.music.service.SingerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lyj
 * @since 2022-06-20
 */
@Service
public class SingerServiceImpl extends ServiceImpl<SingerMapper, Singer> implements SingerService {
    @Resource
    private SingerMapper singerMapper;

    @Override
    public Singer getSingerDetail(Integer singerId) {
        return singerMapper.getSingerDetail(singerId);
    }

    @Override
    public List<Map<String, Object>> getSingBySex() {
        return singerMapper.getSingBySex();
    }
}
