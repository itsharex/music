package com.music.mapper;

import com.music.entity.Collect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface CollectMapper extends BaseMapper<Collect> {

    /**
     * 通过accountId查询收藏的歌曲id
     * @param accountId
     * @return List
     */
    @Select("select song_id from collect where account_id=#{accountId}")
    List<Integer> getCollectList(Integer accountId);
}
