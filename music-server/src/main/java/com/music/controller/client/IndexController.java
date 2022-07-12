package com.music.controller.client;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.music.Vo.PageParams;
import com.music.common.CommonResult;
import com.music.entity.Carousel;
import com.music.entity.Classify;
import com.music.entity.Singer;
import com.music.entity.Songmenu;
import com.music.service.CarouselService;
import com.music.service.ClassifyService;
import com.music.service.SingerService;
import com.music.service.SongmenuService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author lyj
 * @data 2022/6/25 17:00
 */
@RestController
@RequestMapping("/client")
public class IndexController {
    @Resource
    private CarouselService carouselService;
    @Resource
    private SongmenuService songmenuService;
    @Resource
    private SingerService singerService;
    @Resource
    private ClassifyService classifyService;

    @Cacheable(value = "carousel",keyGenerator = "keyGenerator")
    @GetMapping("/carousel")
    public CommonResult<List<Carousel>> getCarousel(){
        return CommonResult.success(carouselService.list());
    }


    @Cacheable(value = "playlist",keyGenerator = "keyGenerator")
    @GetMapping(value = "/playlist")
    public CommonResult<List<Songmenu>> getPlayList(){
        return CommonResult.success(songmenuService.list(new QueryWrapper<Songmenu>().eq("state",0).last("limit 10")));
    }

    @Cacheable(value = "singer",keyGenerator = "keyGenerator")
    @GetMapping("/singer")
    public CommonResult<List<Singer>> getSinger(){
        return CommonResult.success(singerService.list(new QueryWrapper<Singer>().last("limit 10")));
    }

    @GetMapping("/singer/list")
    public CommonResult<IPage<Singer>> getSingerByPage(@Valid PageParams params){
        IPage<Singer> iPage = new Page<>(params.getCurrentPage(),10);
        //查询全部
        if(params.getSex() == null){
            return CommonResult.success(singerService.page(iPage));
        }
        //分类查询
        return CommonResult.success(singerService.page(iPage,new QueryWrapper<Singer>().eq("sex",params.getSex())));
    }

    @GetMapping("/playlist/list")
    public CommonResult<IPage<Songmenu>> getPlayListByPage(@Valid PageParams params){
        IPage<Songmenu> iPage = new Page<>(params.getCurrentPage(),10);
        //查询全部
        if(params.getClassifyId() == null){
            return CommonResult.success(songmenuService.page(iPage));
        }
        //分类查询
        return CommonResult.success(songmenuService.page(iPage,new QueryWrapper<Songmenu>().eq("state",0).eq("classify_id",params.getClassifyId())));
    }

    /**
     * 歌单分类
     * @return
     */
    @Cacheable(value = "classify",keyGenerator = "keyGenerator")
    @GetMapping("/playlist/classify")
    public CommonResult<List<Classify>> getClassify(){
        return CommonResult.success(classifyService.list());
    }
}
