package com.music.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.music.Vo.PageParams;
import com.music.common.CommonResult;
import com.music.entity.Singer;
import com.music.service.SingerService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author lyj
 * @since 2022/7/11
 */
@RestController
@RequestMapping("/api")
@Validated
public class SingerController {
    @Resource
    private SingerService singerService;

    @GetMapping("/singer/list")
    public CommonResult<IPage<Singer>> getSingerList(@Valid PageParams params){
        IPage<Singer> page = new Page<>(params.getCurrentPage(),5);
        if(StrUtil.isEmpty(params.getKeyword())){
            return CommonResult.success(singerService.page(page));
        }
        return CommonResult.success(singerService.page(page,new QueryWrapper<Singer>().eq("name",params.getKeyword())));
    }

    @PostMapping("/singer/add")
    public CommonResult<String> addSinger(@RequestBody Singer singer){
        boolean result = singerService.save(singer);
        return result ? CommonResult.success("添加歌手成功") : CommonResult.faild("添加歌手失败");
    }

    @PostMapping("/singer/update")
    public CommonResult<String> updateSinger(@RequestBody Singer singer){
        boolean result = singerService.updateById(singer);
        return result ? CommonResult.success("修改歌手成功") : CommonResult.faild("修改歌手失败");
    }

    @PostMapping("/singer/delete/{singerId}")
    public CommonResult<String> delelteSinger(@NotNull(message = "id不能为空") @PathVariable("singerId") Integer singerId){
        boolean result = singerService.removeById(singerId);
        return result ? CommonResult.success("删除歌手成功") : CommonResult.faild("删除歌手失败");
    }

    @PostMapping("/singer/deletes")
    public CommonResult<String> deletesSinger(@RequestBody List<Integer> list){
        boolean result = singerService.removeByIds(list);
        return result ? CommonResult.success("批量删除歌手成功") : CommonResult.faild("批量删除歌手失败");
    }
}
