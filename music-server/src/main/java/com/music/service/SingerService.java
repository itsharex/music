package com.music.service;

import com.music.entity.Singer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lyj
 * @since 2022-06-20
 */
public interface SingerService extends IService<Singer> {

    /**
     * 查询歌手详情
     * @param singerId
     * @return
     */
    Singer getSingerDetail(Integer singerId);

    /**
     * 分组统计歌手不同性别数量
     * @return
     */
    List<Map<String, Object>> getSingBySex();
}
