package com.music.Vo;

import lombok.Data;

/**
 * @Author lyj
 * @since 2022/7/7
 */
@Data
public class AdminUserVo {

    /**
     * 管理员id
     */
    private Integer adminId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 说明
     */
    private String password;

    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 验证码
     */
    private String code;
}
