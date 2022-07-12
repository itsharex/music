package com.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.music.entity.SongmenuList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lyj
 * @since 2022-06-20
 */
@Mapper
public interface SongmenuListMapper extends BaseMapper<SongmenuList> {

    /**
     * 根据歌单id获取歌单下的歌曲
     * @param songmenuId
     * @return
     */
    @Select("select song_id from songmenu_list where songmenu_id=#{songmenuId}")
    List<Integer> getSongMenu(Integer songmenuId);
}
