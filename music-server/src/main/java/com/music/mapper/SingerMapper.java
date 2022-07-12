package com.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.music.entity.Singer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lyj
 * @since 2022-06-20
 */
@Mapper
public interface SingerMapper extends BaseMapper<Singer> {

    /**
     * 查询歌手详情
     * @param singerId
     * @return
     */
    @Results({
            @Result(property = "singerId",column = "singer_id",id = true),
            @Result(property = "name",column = "name"),
            @Result(property = "sex",column = "sex"),
            @Result(property = "pic",column = "pic"),
            @Result(property = "birth",column = "birth"),
            @Result(property = "address",column = "address"),
            @Result(property = "introduction",column = "introduction")
    })
    @Select("select * from singer where singer_id=#{singerId}")
    Singer getSingerDetail(Integer singerId);

    /**
     * 分组统计歌手不同性别数量
     * @return
     */
    @Select("select sex,count(sex) type from singer group by sex")
    List<Map<String, Object>> getSingBySex();
}
