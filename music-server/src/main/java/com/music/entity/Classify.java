package com.music.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lyj
 * @data 2022/6/25 15:33
 */
@Data
public class Classify implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 歌单分类id
     */
    @TableId(value = "classify_id", type = IdType.AUTO)
    private Integer classifyId;

    /**
     * 歌单分类名
     */
    private String classifyName;
}
