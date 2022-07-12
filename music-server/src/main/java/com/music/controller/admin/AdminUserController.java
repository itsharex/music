package com.music.controller.admin;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.music.Vo.AdminUserVo;
import com.music.common.CommonResult;
import com.music.entity.AdminUser;
import com.music.service.AdminUserService;
import com.music.utils.RedisUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class AdminUserController {
    @Resource
    private AdminUserService adminUserService;
    @Resource
    private RedisUtils redisUtils;

    @PostMapping("/login")
    public CommonResult<AdminUser> login(@RequestBody AdminUserVo adminUserVo,HttpServletRequest request){
        //将前台传递的密码加密
        adminUserVo.setPassword(SecureUtil.md5(adminUserVo.getPassword()));
        //取出验证码
        String code = Convert.toStr(redisUtils.get(adminUserVo.getCode()));
        if(StrUtil.isBlank(code)){
            return CommonResult.faild("验证码错误");
        }
        //根据用户名查询
        AdminUser adminUser = adminUserService.getOne(new QueryWrapper<AdminUser>().eq("username",adminUserVo.getUsername()));
        if(ObjectUtil.isNull(adminUser)){
            return CommonResult.faild("账号不存在");
        }

        if(!adminUser.getPassword().equals(adminUserVo.getPassword())){
            return CommonResult.faild("用户名或密码错误");
        }
        return CommonResult.success("登录成功",adminUser);
    }

    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response){
        //定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(100, 40, 4, 4);
        try {
            redisUtils.set(captcha.getCode(),captcha.getCode(),60);
            response.setContentType("image/png");
            response.setHeader("pragma","no-cache");
            response.setDateHeader("Expire",0);
            captcha.write(response.getOutputStream());
            response.flushBuffer();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping("/update/password")
    public CommonResult<String> updatePassword(@RequestBody AdminUserVo adminUserVo){
        //将前端传递的密码加密
        adminUserVo.setPassword(SecureUtil.md5(adminUserVo.getPassword()));
        int result = adminUserService.count(new QueryWrapper<AdminUser>().eq("username",adminUserVo.getUsername())
                .eq("password",SecureUtil.md5(adminUserVo.getOldPassword())));
        if(result == 0) {
            return CommonResult.faild("旧密码错误");
        }
        AdminUser adminUser = new AdminUser();
        BeanUtil.copyProperties(adminUserVo,adminUser);
        //修改密码
        boolean modify = adminUserService.update(new UpdateWrapper<AdminUser>().set("password",adminUser.getPassword()).eq("username",adminUser.getUsername()));
        return modify ? CommonResult.success("修改密码成功") : CommonResult.faild("修改密码失败");
    }

}

