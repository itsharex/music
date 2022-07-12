package com.music.controller.client;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.music.Vo.PageParams;
import com.music.common.CommonResult;
import com.music.entity.Songmenu;
import com.music.entity.SongmenuList;
import com.music.service.SongmenuListService;
import com.music.service.SongmenuService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author lyj
 * Date 2022/7/5
 */
@RestController
@RequestMapping("/client")
@Validated
public class PlayListController {
    @Resource
    private SongmenuService songmenuService;
    @Resource
    private SongmenuListService songmenuListService;

    @PostMapping("/playlist/add")
    public CommonResult<String> addPlayList(@RequestBody Songmenu songmenu){
        boolean result = songmenuService.save(songmenu);
        return result ? CommonResult.success("添加歌单成功") : CommonResult.faild("添加歌单失败");
    }

    @PostMapping("/playlist/delete/{songmenuId}")
    public CommonResult<String> delPlayList(@PathVariable("songmenuId") @NotNull(message = "songmenuId不能为空") Integer songmenuId){
        boolean result = songmenuService.removeById(songmenuId);
        songmenuListService.remove(new QueryWrapper<SongmenuList>().eq("songmenuId",songmenuId));
        return result ? CommonResult.success("删除歌单成功") : CommonResult.faild("删除歌单失败");
    }

    @PostMapping("/playlist/update")
    public CommonResult<String> updatePlayList(@RequestBody Songmenu songmenu){
        boolean result = songmenuService.updateById(songmenu);
        return result ? CommonResult.success("修改歌单成功") : CommonResult.faild("修改歌单失败");
    }

    @GetMapping("/playlist/query")
    public CommonResult<IPage<Songmenu>> queryPlayList(@Valid PageParams params){
        IPage<Songmenu> iPage = new Page<>(params.getCurrentPage(),5);
        return CommonResult.success(songmenuService.page(iPage,new QueryWrapper<Songmenu>().eq("account_id",params.getAccountId())));
    }

    /**
     * 添加歌曲到歌单
     * @param songmenuList
     * @return
     */
    @PostMapping("/playlist/add/song")
    public CommonResult<String> addSongToPlayList(@RequestBody SongmenuList songmenuList){
        //查询歌曲是否已存在当前歌单
        int count = songmenuListService.count(new QueryWrapper<SongmenuList>().eq("song_id",songmenuList.getSongId()).
                eq("songmenu_id",songmenuList.getSongmenuId()));
        //歌曲不存在
        if(count == 0){
            boolean result = songmenuListService.save(songmenuList);
            return result ? CommonResult.success("添加到歌单成功") : CommonResult.faild("添加到歌单失败");
        }
        //歌曲已存在
        return CommonResult.faild("当前歌曲已在歌单中");
    }

    /**
     * 获取所有自己的歌单
     * @param accountId
     * @return List
     */
    @GetMapping("/playlist/get/list")
    public CommonResult<List<Songmenu>> getPlayList(@RequestParam Integer accountId){
        List<Songmenu> list = songmenuService.list(new QueryWrapper<Songmenu>().eq("account_id",accountId));
        return CommonResult.success(list);
    }

}
