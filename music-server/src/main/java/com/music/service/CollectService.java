package com.music.service;

import com.music.entity.Collect;
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
public interface CollectService extends IService<Collect> {

    /**
     * 通过accountId查询收藏的歌曲id
     * @param accountId
     * @return
     */
    List<Integer> getCollectList(Integer accountId);
}
