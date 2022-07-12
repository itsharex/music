package com.music.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

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
public class Account implements Serializable {

    private static final long serialVersionUID=1L;

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
    @NotBlank(message = "密码不能为空")
    @JSONField(serialize = false)
    private String password;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    @NotBlank(message = "邮箱不能为空")
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


}
