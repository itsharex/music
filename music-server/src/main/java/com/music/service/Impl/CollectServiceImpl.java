package com.music.service.Impl;

import com.music.entity.Collect;
import com.music.mapper.CollectMapper;
import com.music.service.CollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lyj
 * @since 2022-06-20
 */
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {
    @Resource
    private CollectMapper collectMapper;

    @Override
    public List<Integer> getCollectList(Integer accountId) {
        return collectMapper.getCollectList(accountId);
    }
}
