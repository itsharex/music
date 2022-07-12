package com.music.service.Impl;

import com.music.entity.AdminUser;
import com.music.mapper.AdminUserMapper;
import com.music.service.AdminUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lyj
 * @since 2022-06-20
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements AdminUserService {
    @Resource
    private AdminUserMapper adminUserMapper;

    public AdminUser getAdminUser(String username){
        return adminUserMapper.getAdminUser(username);
    }
}
