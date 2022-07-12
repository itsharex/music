package com.music.mapper;

import com.music.entity.Classify;
import com.music.entity.Songmenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

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
public interface SongmenuMapper extends BaseMapper<Songmenu> {
    /**
     * 查询歌单类型数量
     * @return
     */
    @Select("select  c.classify_name classifyName,count(c.classify_id) classifyId from classify c left join songmenu s " +
            "on c.classify_id = s.classify_id group by c.classify_id")
    List<Classify> getClassifyCount();
}
