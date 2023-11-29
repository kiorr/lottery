package com.itheima.prize.commons.db.service.impl;
import com.itheima.prize.commons.config.RedisKeys;
import com.itheima.prize.commons.db.entity.CardUser;
import com.itheima.prize.commons.db.mapper.CardUserGamesMapper;
import com.itheima.prize.commons.db.mapper.CardUserMapper;
import com.itheima.prize.commons.db.service.CardUserGamesService;
import com.itheima.prize.commons.db.service.CardUserService;
import com.itheima.prize.commons.utils.ApiResult;
import com.itheima.prize.commons.utils.PasswordUtil;
import com.itheima.prize.commons.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CardUserGamesServiceImpl implements CardUserGamesService {
    @Autowired
    private CardUserGamesMapper cardUserGamesMapper;
    @Override
    public Integer getByUserId(Integer id) {
        return cardUserGamesMapper.getGamesNumByUserId(id);
    }

}
