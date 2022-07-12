package com.music.service;

import com.music.entity.Classify;
import com.music.entity.Songmenu;
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
public interface SongmenuService extends IService<Songmenu> {
    /**
     * 查询歌单类型数量
     * @return
     */
    List<Classify> getClassifyCount();
}
