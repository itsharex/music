package com.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.music.entity.Song;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lyj
 * @since 2022-06-20
 */
@Mapper
public interface SongMapper extends BaseMapper<Song> {
}
