package com.itheima.prize.commons.db.service.impl;
import com.itheima.prize.commons.db.mapper.CardUserGamesMapper;
import com.itheima.prize.commons.db.service.CardUserGamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardUserGamesServiceImpl implements CardUserGamesService {
    @Autowired
    private CardUserGamesMapper cardUserGamesMapper;
    @Override
    public Integer getByUserId(String id) {
        return cardUserGamesMapper.getGamesNumByUserId(id);
    }

}
