package com.music.service.Impl;

import com.music.entity.SongmenuList;
import com.music.mapper.SongmenuListMapper;
import com.music.service.SongmenuListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class SongmenuListServiceImpl extends ServiceImpl<SongmenuListMapper, SongmenuList> implements SongmenuListService {
    @Resource
    private SongmenuListMapper songmenuListMapper;

    @Override
    public List<Integer> getSongMenu(Integer songmenuId) {
        return songmenuListMapper.getSongMenu(songmenuId);
    }
}
