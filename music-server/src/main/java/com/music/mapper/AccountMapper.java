package com.music.mapper;

import com.music.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lyj
 * @since 2022-06-20
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {
    /**
     *  查询用户主要信息
     * @param email
     * @return void
     */
    @Select("select account_id,avator,email from account where email=#{email}")
    Account getAccount(String email);

    /**
     * 查询用户详情
     * @param accountId
     * @return void
     */
    @Select("select account_id,username,email,sex,birth,phone,introduction from account where account_id=#{accountId}")
    Account getAccountDetail(Integer accountId);

    /**
     * 分组统计用户不同性别数量
     * @return
     */
    @Select("select sex,count(sex) type from account group by sex")
    List<Map<String, Object>> getAccountBySex();
}
