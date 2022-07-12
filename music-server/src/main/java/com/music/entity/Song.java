package com.music.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class Song implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "song_id", type = IdType.AUTO)
    private Integer songId;

    /**
     * 歌手id
     */
    private Integer singerId;

    /**
     * 歌曲名字
     */
    @TableField("`name`")
    private String name;

    /**
     * 介绍
     */
    private String introduction;

    /**
     * 歌曲图片
     */
    private String pic;

    /**
     * 歌曲地址
     */
    private String url;

    /**
     * 歌词
     */
    private String geci;

    /**
     * 歌手名字
     */
    private String songName;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
