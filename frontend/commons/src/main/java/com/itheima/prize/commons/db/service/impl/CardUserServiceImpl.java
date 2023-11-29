package com.itheima.prize.commons.db.service.impl;
import com.itheima.prize.commons.config.RedisKeys;
import com.itheima.prize.commons.db.entity.CardUser;
import com.itheima.prize.commons.db.mapper.CardUserMapper;
import com.itheima.prize.commons.db.service.CardUserService;
import com.itheima.prize.commons.utils.ApiResult;
import com.itheima.prize.commons.utils.PasswordUtil;
import com.itheima.prize.commons.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class CardUserServiceImpl implements CardUserService {

    @Autowired

    private CardUserMapper cardUserMapper;
    @Autowired
    private RedisUtil redisUtil;

    private static final Long TIME = 5L;
    @Override
    public ApiResult login(String account, String password, HttpServletRequest request) {
        //3若没有去数据库查找
        //3.1根据用户名去数据库搜索
        CardUser cardUser = cardUserMapper.getByName(account);
        //3.2若搜不到用户名则异常
        if (cardUser == null){
            Date date = new Date();
            ApiResult apiResult = new ApiResult(0, "账号或者密码错误", null);
            apiResult.setNow(date);
            return apiResult;
        }
        //3.3找到的话就把密码进行MD5加密，然后与数据库作比较
        String pw = PasswordUtil.encodePassword(password);
        //3.4密码不正确的话  数值-1
        if (!(pw.equals(cardUser.getPasswd()))){
            Date date = new Date();
            ApiResult apiResult = new ApiResult(0, "账号或者密码错误", null);
            apiResult.setNow(date);
            //涉及到redis
            Integer amount = (Integer) redisUtil.get(RedisKeys.USERLOGINTIMES + account);
            if (!((amount - 1) == 0)){
                redisUtil.set(RedisKeys.USERLOGINTIMES + account, amount - 1);
            }else {
                redisUtil.set(RedisKeys.USERLOGINTIMES + account, amount - 1, 300);
            }
            return apiResult;
        }
        //3.5密码正确则登陆
        //4.返回登陆成功
        request.getSession().setAttribute("user", cardUser);
        redisUtil.set(RedisKeys.USERLOGINTIMES + account, TIME);
        Date date = new Date();
        cardUser.setPasswd(null);
        ApiResult apiResult = new ApiResult(1, "登录成功", cardUser);
        apiResult.setNow(date);
        return apiResult;
    }
}
