package com.music.Vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author lyj
 * @data 2022/6/21 11:17
 */
@Data
public class AccountVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "account_id", type = IdType.AUTO)
    private Integer accountId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 1表示男，0表示女
     */
    private Integer sex;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 生日
     */
    private Date birth;

    /**
     * 介绍
     */
    private String introduction;

    /**
     * 头像
     */
    private String avator;

    /**
     * 0表示账号可用，1表示账号不可用
     */
    private Integer enable;

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

    /**
     * TODO
     * 验证码
     * @Param
     * @return
     */
    private String code;

    /**
     * 旧密码
     */
    private String oldPassWord;



}
