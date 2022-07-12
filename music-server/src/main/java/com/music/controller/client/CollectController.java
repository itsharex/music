package com.music.controller.client;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.music.Vo.CollectVo;
import com.music.Vo.PageParams;
import com.music.common.CommonResult;
import com.music.entity.Collect;
import com.music.entity.Song;
import com.music.service.CollectService;
import com.music.service.SongService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lyj
 * @since 2022-06-20
 */
@RestController
@RequestMapping("/client")
@Validated
public class CollectController {
    @Resource
    private CollectService collectService;
    @Resource
    private SongService songService;

    /**
     * 添加收藏
     * @param collect
     * @return
     */
    @PostMapping("/collect/add")
    public CommonResult<String> addCollect(@RequestBody Collect collect){
        int count = collectService.count(new QueryWrapper<Collect>().eq("account_id",collect.getAccountId())
                .eq("song_id",collect.getSongId()));
        if(count == 0) {
            boolean result = collectService.save(collect);
            return result ? CommonResult.success("收藏歌曲成功") : CommonResult.faild("收藏歌曲失败");
        }
        return CommonResult.faild("用户已收藏该歌曲");
    }

    /**
     * 取消收藏
     * @param collect
     * @return
     */
    @PostMapping("/collect/cancel")
    public CommonResult<String> cancelCollect(@RequestBody Collect collect){
        boolean result = collectService.remove(new QueryWrapper<Collect>().eq("account_id",collect.getAccountId())
                .eq("song_id",collect.getSongId()));
        return result ? CommonResult.success("取消收藏成功") : CommonResult.faild("取消收藏失败");
    }

    @PostMapping("/collect/cancel/multiple")
    public CommonResult<String> multipleCancelCollect(@RequestBody CollectVo collectVo){
        for (int i : collectVo.getSongIdArr()) {
            collectService.remove(new QueryWrapper<Collect>().eq("account_id",collectVo.getAccountId())
                    .eq("song_id",i));
        }
        return CommonResult.success("移除成功");
    }

    /**
     * 查询是否已收藏
     * @param collect
     * @return
     */
    @GetMapping("/collect/whether")
    public CommonResult<Boolean> queryWhetherCollect(@Valid Collect collect){
        int result = collectService.count(new QueryWrapper<Collect>().eq("account_id",collect.getAccountId())
                .eq("song_id",collect.getSongId()));
        if(result == 0){
            return CommonResult.success(false);
        }
        return CommonResult.success(true);
    }

    /**
     * 查询收藏列表
     * @param params
     * @return
     */
    @GetMapping("/collect/list")
    public CommonResult<IPage<Song>> queryCollectList(PageParams params){
        IPage<Song> page = new Page<>(params.getCurrentPage(),10);
        List<Integer> list = collectService.getCollectList(params.getAccountId());
        return CommonResult.success(songService.page(page,new QueryWrapper<Song>().in("song_id",list)));
    }
}

