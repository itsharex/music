package com.music.controller.client;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.music.Vo.PageParams;
import com.music.common.CommonResult;
import com.music.entity.Singer;
import com.music.entity.Song;
import com.music.entity.Songmenu;
import com.music.service.SingerService;
import com.music.service.SongService;
import com.music.service.SongmenuListService;
import com.music.service.SongmenuService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author lyj
 * @data 2022/6/26 20:15
 */
@Validated
@RestController
@RequestMapping("/client")
public class DetailController {
    @Resource
    private SongmenuService songmenuService;
    @Resource
    private SingerService singerService;
    @Resource
    private SongmenuListService songmenuListService;
    @Resource
    private SongService songService;

    /**
     * 查询歌手详情
     * @param singerId
     * @return
     */
    @GetMapping("/singer/detail/{singerId}")
    public CommonResult<Singer> getSingerDetail(@NotNull(message = "歌手id不能为空") @PathVariable("singerId") Integer singerId){
        return CommonResult.success(singerService.getSingerDetail(singerId));
    }

    /**
     * 查询歌单详情
     * @param songmenuId
     * @return
     */
    @GetMapping("/playlist/detail/{songmenuId}")
    public CommonResult<Songmenu> getSongMenuDetail(@NotNull(message = "歌单id不能为空") @PathVariable("songmenuId") Integer songmenuId){
        Songmenu songmenu = songmenuService.getById(songmenuId);
        return CommonResult.success(songmenu);
    }

    @GetMapping("/song/list")
    public CommonResult<IPage<Song>> getSong(PageParams params){
        IPage<Song> iPage = new Page<>(params.getCurrentPage(),10);
        IPage<Song> page = null;
        if(params.getType() == 0){
            page = songService.page(iPage,new QueryWrapper<Song>().eq("singer_id",params.getSingerId()));
        }else{
            //根据歌单id查询歌曲
            List<Integer> list = songmenuListService.getSongMenu(params.getSongmenuId());
            if(list.size() == 0){
                return CommonResult.faild("没有数据");
            }
            page = songService.page(iPage,new QueryWrapper<Song>().in("song_id",list));
        }
        if(page.getTotal() == 0){
            return CommonResult.faild("没有数据");
        }
        return CommonResult.success(page);
    }
}
