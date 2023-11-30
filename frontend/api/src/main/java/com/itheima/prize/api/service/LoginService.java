package com.itheima.prize.api.service;


import com.itheima.prize.commons.db.entity.CardUser;
import com.itheima.prize.commons.db.mapper.CardUserMapper;
import com.itheima.prize.commons.utils.ApiResult;
import com.itheima.prize.commons.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class LoginService {

    @Autowired
    private CardUserMapper mapper;


    public CardUser login(String account, String password) {
        // 获取 加密 的密码
        String encodePassword = PasswordUtil.encodePassword(password);
        // 查询数据库看是否用户名和密码相同
        CardUser cardUser = mapper.selectByAccount(account);
        if(ObjectUtils.isEmpty(cardUser)) {
            return null;
        }
        if(cardUser.getUname().equals(account) && cardUser.getPasswd().equals(encodePassword)) {
            return cardUser;
        }

        return null;
    }
}
