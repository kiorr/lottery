package com.itheima.prize.commons.db.service;

import com.itheima.prize.commons.db.entity.CardGame;
import com.itheima.prize.commons.utils.ApiResult;
import com.itheima.prize.commons.utils.PageBean;

public interface CardGameService {
    PageBean<CardGame> page(int curpage, int limit, int status);

    ApiResult<CardGame> getByGameId(String gameid);
}
