package com.itheima.prize.commons.db.service;

import com.itheima.prize.commons.db.entity.CardProductDto;
import com.itheima.prize.commons.utils.ApiResult;

import java.util.List;

public interface CardProductService {
    ApiResult<List<CardProductDto>> getByGameid(String gameid);
}
