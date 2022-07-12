package com.music.controller.admin;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.music.Vo.PageParams;
import com.music.common.CommonResult;
import com.music.entity.Song;
import com.music.service.SongService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
@RequestMapping("/api")
@Validated
public class SongController {
    @Resource
    private SongService songService;

    @GetMapping("/song/list")
    public CommonResult<IPage<Song>> getSongList(@Valid PageParams params){
        IPage<Song> page = new Page<>(params.getCurrentPage(),5);
        if(StrUtil.isEmpty(params.getKeyword())){
            return CommonResult.success(songService.page(page,new QueryWrapper<Song>().eq("singer_id",params.getSingerId())));
        }
        return CommonResult.success(songService.page(page,new QueryWrapper<Song>().eq("singer_id",params.getSingerId()).eq("name",params.getKeyword())));
    }

    @PostMapping("/song/add")
    public CommonResult<String> addSong(@RequestBody Song song){
        boolean result = songService.save(song);
        return result ? CommonResult.success("添加歌曲成功") : CommonResult.faild("添加歌曲失败");
    }

    @PostMapping("/song/update")
    public CommonResult<String> updateSong(@RequestBody Song song){
        boolean result = songService.updateById(song);
        return result ? CommonResult.success("修改歌曲成功") : CommonResult.faild("修改歌曲失败");
    }

    @PostMapping("/song/delete/{songId}")
    public CommonResult<String> delelteSong(@NotNull(message = "id不能为空") @PathVariable("songId") Integer songId){
        boolean result = songService.removeById(songId);
        return result ? CommonResult.success("删除歌曲成功") : CommonResult.faild("删除歌曲失败");
    }

    @PostMapping("/song/deletes")
    public CommonResult<String> deletesSong(@RequestBody List<Integer> list){
        boolean result = songService.removeByIds(list);
        return result ? CommonResult.success("批量删除歌曲成功") : CommonResult.faild("批量删除歌曲失败");
    }
}

