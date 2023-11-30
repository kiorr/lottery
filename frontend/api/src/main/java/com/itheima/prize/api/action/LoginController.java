package com.itheima.prize.api.action;

import com.itheima.prize.api.service.LoginService;
import com.itheima.prize.commons.config.RedisKeys;
import com.itheima.prize.commons.db.entity.CardUser;
import com.itheima.prize.commons.db.entity.CardUserExample;
import com.itheima.prize.commons.db.mapper.CardUserMapper;
import com.itheima.prize.commons.utils.ApiResult;
import com.itheima.prize.commons.utils.PasswordUtil;
import com.itheima.prize.commons.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Api(tags = {"登录模块"})
public class LoginController {
    @Autowired
    private CardUserMapper userMapper;

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value = "用户名",required = true),
            @ApiImplicitParam(name="password",value = "密码",required = true)
    })
    public ApiResult login(HttpServletRequest request, @RequestParam String account,@RequestParam String password) {
        // 记录用户登入次数
        long time = redisUtil.getExpire(RedisKeys.USERLOGINTIMES + account);
        if(time >= 0) {
            return new ApiResult(0,"密码错误5次，请5分钟后再登录",null);
        }
        CardUser login = loginService.login(account, password);
        // 登入成功
        if(!ObjectUtils.isEmpty(login)) {
            // 获取会话
            HttpSession session = request.getSession(true);
            session.setAttribute("user",login);
            // 存入到 redis
            redisUtil.set(RedisKeys.SESSION+login.getId(),session);
            login.setPasswd(null);
            login.setIdcard(null);
            return new ApiResult(1,"登录成功",login);
        }
        // 登入失败
        long incr = redisUtil.incr(RedisKeys.USERLOGINTIMES + account, 1);
        if(incr <= 5) {
            return new ApiResult(0,"账户名或密码错误",null);
        }
        redisUtil.set(RedisKeys.USERLOGINTIMES + account,0,300);
        // 登入失败五次
        return new ApiResult(0,"密码错误5次，请5分钟后再登录",null);
    }

    @GetMapping("/logout")
    @ApiOperation(value = "退出")
    public ApiResult logout(HttpServletRequest request){
        // 删除会话
        HttpSession session = request.getSession(false);
        CardUser user = (CardUser) session.getAttribute("user");
        session.removeAttribute("user");
        // 删除 redis 中的会话
        redisUtil.del(RedisKeys.SESSION+user.getId());
        return new ApiResult<>(1,"退出成功",null);
    }

}
