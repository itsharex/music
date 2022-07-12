package com.music.mapper;

import com.music.entity.AdminUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lyj
 * @since 2022-06-20
 */
@Mapper
public interface AdminUserMapper extends BaseMapper<AdminUser> {
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @Select("select admin_id adminId,username from admin_user where username=#{username}")
    AdminUser getAdminUser(String username);
}
