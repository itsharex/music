package com.music.service;

import com.music.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lyj
 * @since 2022-06-20
 */
public interface AccountService extends IService<Account> {
    /**
     * 根据邮箱查询账号的方法
     * @param email
     * @return void
     */
    Account getAccount(String email);

    /**
     * 通过id查询账号详细信息
     * @param accountId
     * @return
     */
    Account accountDetail(Integer accountId);

    /**
     * 分组统计用户不同性别数量
     * @return
     */
    List<Map<String, Object>> getAccountBYSex();
}
