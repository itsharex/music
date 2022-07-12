package com.music.service;

import com.music.entity.SongmenuList;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lyj
 * @since 2022-06-20
 */
public interface SongmenuListService extends IService<SongmenuList> {
    /**
     *  根据歌单id获取歌单下的歌曲
     * @param songmenuId
     * @return
     */
    List<Integer> getSongMenu(Integer songmenuId);
}
