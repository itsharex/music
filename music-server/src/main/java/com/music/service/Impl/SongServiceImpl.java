package com.music.service.Impl;

import com.music.entity.Song;
import com.music.mapper.SongMapper;
import com.music.service.SongService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lyj
 * @since 2022-06-20
 */
@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {

}
