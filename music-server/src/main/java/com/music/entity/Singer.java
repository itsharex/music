package com.music.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author lyj
 * @since 2022-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Singer implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "singer_id", type = IdType.AUTO)
    private Integer singerId;

    /**
     * 名字
     */
    private String name;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 图片
     */
    private String pic;

    /**
     * 出生年月
     */
    private Date birth;

    /**
     * 地址
     */
    private String address;

    /**
     * 介绍
     */
    private String introduction;

    /**
     * 歌曲
     */
    @TableField(exist = false)
    private List<Song> songList;

}
