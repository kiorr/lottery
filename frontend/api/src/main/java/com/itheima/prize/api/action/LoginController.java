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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.Session;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Api(tags = {"登录模块"})
public class LoginController {
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
        /**
         * 1.对用户进行次数判定，redis里有缓存的次数，次数减1，没有的话就设缓存
         * 2.将传入的密码md5化，然后跟数据库相匹配
         * 3.将session的值存入*/
        if (redisUtil.get(account) == null) {
            redisUtil.set(account, 5);
        }
        if ((Integer) redisUtil.get(account) == 0) {
                ApiResult<Object> apiResult = new ApiResult<>(0, "密码错误5次，请5分钟后再登录", null);
                apiResult.setNow(new Date());
                return apiResult;
        }

        CardUser user = userMapper.selectByName(account);
        //将传入的密码生成md5格式
        String encodePassword = PasswordUtil.encodePassword(password);
        //用户登录成功
        if (encodePassword.equals(user.getPasswd())) {
            //对密码进行脱敏处理
            user.setPasswd("null");
            ApiResult apiResult = new ApiResult(1, "登录成功", user);
            apiResult.setNow(new Date());
            request.getSession().setAttribute("user", user);
            return apiResult;
        }
        //用户登录失败
        if (!encodePassword.equals(user.getPasswd())) {
            Integer integer = (Integer) redisUtil.get(account);
            integer--;
            if(integer==0){
                redisUtil.set(account,integer,300);
            }else {
                redisUtil.set(account,integer);
            }
            ApiResult<Object> apiResult = new ApiResult<>(0, "账户名或密码错误", null);
            apiResult.setNow(new Date());
            return apiResult;
        }
        return null;
    }

    @GetMapping("/logout")
    @ApiOperation(value = "退出")
    public ApiResult logout(HttpServletRequest request){
        request.getSession().setAttribute("user",null);
        ApiResult<Object> apiResult = new ApiResult<>(1,"退出成功",null);
        apiResult.setNow(new Date());
        return apiResult;
    }

}