package com.music.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.entity.Classify;
import com.music.entity.Songmenu;
import com.music.mapper.SongmenuMapper;
import com.music.service.SongmenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lyj
 * @since 2022-06-20
 */
@Service
public class SongmenuServiceImpl extends ServiceImpl<SongmenuMapper, Songmenu> implements SongmenuService {
    @Resource
    private SongmenuMapper songmenuMapper;
    @Override
    public List<Classify> getClassifyCount() {
        return songmenuMapper.getClassifyCount();
    }
}
