package com.music.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
public class Songmenu implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "songmenu_id", type = IdType.AUTO)
    private Integer songmenuId;

    /**
     * 歌单创建者
     */
    private Integer accountId;

    /**
     * 标题
     */
    private String title;

    /**
     * 歌单图片
     */
    private String pic;

    /**
     * 歌单分类
     */
    private Integer classifyId;

    /**
     * 歌单类型
     */
    private Integer state;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 歌曲
     */
    @TableField(exist = false)
    private List<Song> songList;

}
