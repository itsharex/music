package com.music.controller.client;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.music.Vo.PageParams;
import com.music.common.CommonResult;
import com.music.entity.Singer;
import com.music.entity.Song;
import com.music.entity.Songmenu;
import com.music.service.SongService;
import com.music.service.SongmenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author lyj
 * @data 2022/6/28 16:07
 */
@RestController
@RequestMapping("/client")
public class SearchController {
    @Resource
    private SongService songService;
    @Resource
    private SongmenuService songmenuService;

    @GetMapping("/search/song")
    public CommonResult<IPage<Song>> searchSong(@Valid PageParams params){
        Page<Song> page = new Page<>(params.getCurrentPage(),10);
        IPage<Song> iPage = songService.page(page,new QueryWrapper<Song>().likeRight("name",params.getKeyword()));
        if(iPage.getTotal() == 0) {
            return CommonResult.faild("搜索不到相关数据");
        }
        return CommonResult.success(iPage);
    }

    @GetMapping("/search/playlist")
    public CommonResult<IPage<Songmenu>> searchSongMenu(@Valid PageParams params){
        Page<Songmenu> page = new Page<>(params.getCurrentPage(),10);
        IPage<Songmenu> iPage = songmenuService.page(page,new QueryWrapper<Songmenu>().likeRight("title",params.getKeyword()));
        if(iPage.getTotal() == 0) {
            return CommonResult.faild("搜索不到相关数据");
        }
        return CommonResult.success(iPage);
    }
}
