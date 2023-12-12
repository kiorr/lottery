package com.itheima.prize.api.action;

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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Api(tags = {"登录模块"})
public class LoginController {
    private  static  final  int LOGIN_COUNT=5;
    public static final long FORBID_TIME=300;
    @Autowired
    private CardUserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value = "用户名",required = true),
            @ApiImplicitParam(name="password",value = "密码",required = true)
    })
    public ApiResult login(HttpServletRequest request, @RequestParam String account,@RequestParam String password) {
            if(StringUtils.isBlank(account)||StringUtils.isBlank(password))
                return new ApiResult(0,"账号或密码为空",null);
            String redisKey=RedisKeys.USERLOGINTIMES+account;
        Object o = redisUtil.get(redisKey);
        if(o!=null&&o.equals(LOGIN_COUNT)){
                return new ApiResult(0,"密码错误5次，请5分钟后再登录",null);
            }
            //进行登录密码加密
            String encodedPassword = PasswordUtil.encodePassword(password);
            //数据查询
            CardUser cardUser = userMapper.selectUserByName(account, encodedPassword);
            //登陆未通过
            if(cardUser==null){
                if (redisUtil.incr(RedisKeys.USERLOGINTIMES+account,1)>=5){
                    redisUtil.expire(redisKey,FORBID_TIME);
                }
                return new ApiResult(0,"用户名或密码不正确",null);
            }
            //登陆通过，为请求设置session
            //删除redis中的键
            redisUtil.del(redisKey);
            request.getSession().setAttribute("user",cardUser);
        return new ApiResult(1,"登陆成功",cardUser);
    }

    @GetMapping("/logout")
    @ApiOperation(value = "退出")
    public ApiResult logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return new ApiResult(1,"退出成功",null);
    }

}