package com.itheima.prize.api.action;

import com.itheima.prize.commons.config.RedisKeys;
import com.itheima.prize.commons.db.entity.CardUser;
import com.itheima.prize.commons.db.mapper.CardUserMapper;
import com.itheima.prize.commons.db.service.CardUserService;
import com.itheima.prize.commons.utils.ApiResult;
import com.itheima.prize.commons.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping(value = "/api")
@Api(tags = {"登录模块"})
public class LoginController {
    @Autowired
    private CardUserMapper cardUserMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CardUserService cardUserService;

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value = "用户名",required = true),
            @ApiImplicitParam(name="password",value = "密码",required = true)
    })
    public ApiResult login(HttpServletRequest request, @RequestParam String account, @RequestParam String password) {
        //1.先走redis，看看是否有session
        CardUser cardUser = (CardUser) request.getSession().getAttribute("user");
        //2.1若有直接取出
        if (cardUser != null){
            Date date = new Date();
            ApiResult apiResult = new ApiResult(1, "登录成功", cardUser);
            apiResult.setNow(date);
            return apiResult;
        }
        //3.redis判断   -到0之后就锁住
        Integer amount = (Integer) redisUtil.get(RedisKeys.USERLOGINTIMES + account);
        if (amount != null) {
            if (amount == 0) {
                Date date = new Date();
                ApiResult apiResult = new ApiResult(0, "密码错误5次，请5分钟后再登录", null);
                apiResult.setNow(date);
                return apiResult;
            }
        }else {
            redisUtil.set(RedisKeys.USERLOGINTIMES + account, 5L);
        }
        ApiResult apiResult = cardUserService.login(account, password, request);
        //4.存session
        /*if (apiResult.getCode() == 1){
            CardUser user = (CardUser) apiResult.getData();
            request.getSession().setAttribute("user", user);
        }*/
        return apiResult;
    }

    @GetMapping("/logout")
    @ApiOperation(value = "退出")
    public ApiResult logout(HttpServletRequest request){
        request.getSession().setAttribute("user", null);
        Date date = new Date();
        ApiResult apiResult = new ApiResult(1, "退出成功", null);
        apiResult.setNow(date);
        return apiResult;
    }

}