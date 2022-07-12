package com.music.controller.client;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.music.Vo.AccountVo;
import com.music.common.CommonResult;
import com.music.entity.Account;
import com.music.service.AccountService;
import com.music.utils.RedisUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

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
public class AccountController {
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private AccountService accountService;

    /**
     * TODO
     * 登录
     * @Param [accountVo]
     * @return com.music.common.CommonResult<java.lang.Object>
     */
    @PostMapping("/login")
    public CommonResult<Map<String,Object>> login(@RequestBody @Valid AccountVo accountVo){
        Map<String,Object> map = new HashMap<>(16);
        //将前台传递的密码加密
        accountVo.setPassword(SecureUtil.md5(accountVo.getPassword()));
        //验证验证码是否正确
        String code = Convert.toStr(redisUtils.get(accountVo.getCode()));
        if(StrUtil.isBlank(code)){
            return CommonResult.faild("验证码错误");
        }
        //根据邮箱查询
        Account account = accountService.getOne(new QueryWrapper<Account>().eq("email",accountVo.getEmail()));
        if(ObjectUtil.isNull(account)){
            return CommonResult.faild("账号不存在");
        }
        //对比密码是否正确
        if(!account.getPassword().equals(accountVo.getPassword())){
            return CommonResult.faild("用户名或密码错误");
        }
        //查询用户信息
        map.put("info",accountService.getAccount(accountVo.getEmail()));
        return CommonResult.success("登录成功",map);
    }

    @PostMapping("/register")
    public CommonResult<String> register(@RequestBody Account account){
        String password = SecureUtil.md5(account.getPassword());
        account.setPassword(password);
        account.setAvator("/file/avatar.jpg");
        boolean result = accountService.save(account);
        return result ? CommonResult.success("注册成功") : CommonResult.faild("注册失败");
    }

    /**
     * TODO
     * 生成验证码
     * @Param [request, response]
     * @return void
     */
    @GetMapping("/code")
    public void code(HttpServletRequest request, HttpServletResponse response){
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100,40);
        lineCaptcha.setGenerator(randomGenerator);
        // 重新生成code
        lineCaptcha.createCode();
        try {
            redisUtils.set(lineCaptcha.getCode(),lineCaptcha.getCode(),60);
            request.getSession().setAttribute("code",lineCaptcha.getCode());
            response.setContentType("image/png");
            response.setHeader("pragma","no-cache");
            response.setDateHeader("Expire",0);
            lineCaptcha.write(response.getOutputStream());
            response.flushBuffer();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @PostMapping("/updateInfo")
    public CommonResult<String> updateUserInfo(@RequestBody Account account){
        boolean result = accountService.updateById(account);
        return result ? CommonResult.success("修改成功") : CommonResult.faild("修改失败");
    }

    @PostMapping("/password")
    public CommonResult<String> updatePassWord(@RequestBody  AccountVo accountVo){
        //密码加密
        accountVo.setOldPassWord(SecureUtil.md5(accountVo.getOldPassWord()));
        //查询旧密码是否正确
        int count = accountService.count(new QueryWrapper<Account>().eq("email",accountVo.getEmail()).eq("password",accountVo.getOldPassWord()));
        if(count == 0){
            return CommonResult.faild("旧密码错误");
        }
        //修改密码
        Account account = new Account();
        account.setAccountId(accountVo.getAccountId());
        account.setPassword(SecureUtil.md5(accountVo.getPassword()));
        boolean result = accountService.updateById(account);
        return result ? CommonResult.success("修改成功") : CommonResult.faild("修改失败");
    }

    @PostMapping("/logoff/{accountId}")
    public CommonResult<String> logoff(@PathVariable("accountId") @NotNull(message = "accountId不能为空") Integer accountId){
        boolean result = accountService.removeById(accountId);
        return result ? CommonResult.success("注销成功") : CommonResult.faild("注销失败");
    }

    @GetMapping("/accountDetail")
    public CommonResult<Account> accountDetail(@RequestParam("accountId") @NotNull(message = "accountId不能为空") Integer accountId){
        return CommonResult.success(accountService.accountDetail(accountId));
    }

    @PostMapping("/account/upload/avatar")
    public CommonResult<String> updateAvatar(@RequestBody Account account){
        if(account.getAccountId() == null || StrUtil.isBlank(account.getAvator())){
            return  CommonResult.faild("缺少参数");
        }
        boolean result = accountService.updateById(account);
        return result ? CommonResult.success("修改成功") : CommonResult.faild("修改失败");
    }

}

