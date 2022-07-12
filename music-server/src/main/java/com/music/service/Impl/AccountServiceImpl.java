package com.music.service.Impl;

import com.music.entity.Account;
import com.music.mapper.AccountMapper;
import com.music.service.AccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lyj
 * @since 2022-06-20
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {
    @Resource
    private AccountMapper accountMapper;

    @Override
    public Account getAccount(String email) {
        return accountMapper.getAccount(email);
    }

    @Override
    public Account accountDetail(Integer accountId) {
        return accountMapper.getAccountDetail(accountId);
    }

    @Override
    public List<Map<String, Object>> getAccountBYSex() {
        return accountMapper.getAccountBySex();
    }
}
