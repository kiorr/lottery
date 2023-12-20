package com.itheima.prize.api.action;

import com.itheima.prize.commons.db.entity.CardUser;
import com.itheima.prize.commons.db.entity.SysUser;
import com.itheima.prize.commons.db.mapper.CardUserMapper;
import com.itheima.prize.commons.db.mapper.SysUserMapper;
import com.itheima.prize.commons.utils.ApiResult;
import com.itheima.prize.commons.utils.PasswordUtil;
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
    private CardUserMapper userMapper;

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value = "用户名",required = true),
            @ApiImplicitParam(name="password",value = "密码",required = true)
    })
    public ApiResult login(HttpServletRequest request, @RequestParam String account,@RequestParam String password) {
        ApiResult result = new ApiResult<>();
        SysUser sysUser = sysUserMapper.selectByName(account);
        CardUser user = new CardUser(sysUser);
        //加密
        //设置缓存登录次数5
        if(redisUtil.get(account)==null){
            redisUtil.set(account, 5);
        }
        Integer total2 = (Integer)redisUtil.get(account);
        //md5加密
        String encodePassword = PasswordUtil.encrypt(account, password,sysUser.getSalt());
        //判断密码是否正确
        //将缓存登录次数-1，直到为0重新登陆
        if(!(user.getPasswd().equals(encodePassword))){
            Integer total = (Integer)redisUtil.get(account);
            total--;
            redisUtil.set(account, total);
            if(total==0){
                result.setCode(0);
                result.setMsg("密码错误五次，请五分钟后再登录");
                result.setData("null");
                result.setNow(new Date());
                return result;
            }else{
                result.setCode(0);
                result.setMsg("账户名或密码错误");
                result.setData("null");
                result.setNow(new Date());
            }
        }
        //密码符合，登陆成功
        if(user.getUname().equals(account)&&user.getPasswd().equals(encodePassword)){
            result.setCode(1);
            result.setMsg("登录成功");
            result.setData(user);
            result.setNow(new Date());
            request.getSession().setAttribute("user",user);
        }
        return result;
    }

    @GetMapping("/logout")
    @ApiOperation(value = "退出")
    public ApiResult logout(HttpServletRequest request){
        //退出登录
        request.getSession().setAttribute("user", null);
        ApiResult result=new ApiResult(1, "成功",null,new Date());
        return result;
    }

}
