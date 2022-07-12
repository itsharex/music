package com.music.service;

import com.music.entity.AdminUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lyj
 * @since 2022-06-20
 */
public interface AdminUserService extends IService<AdminUser> {
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    AdminUser getAdminUser(String username);
}
