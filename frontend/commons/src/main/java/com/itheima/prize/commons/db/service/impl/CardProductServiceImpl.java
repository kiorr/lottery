package com.itheima.prize.commons.db.service.impl;

import com.itheima.prize.commons.db.entity.CardProduct;
import com.itheima.prize.commons.db.entity.CardProductDto;
import com.itheima.prize.commons.db.mapper.CardProductMapper;
import com.itheima.prize.commons.db.service.CardProductService;
import com.itheima.prize.commons.utils.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CardProductServiceImpl implements CardProductService {
    @Autowired
    private CardProductMapper cardProductMapper;

    @Override
    public ApiResult<List<CardProductDto>> getByGameid(int gameid) {
        List<CardProductDto> cardProductDtos = cardProductMapper.getByGameid(gameid);
        ApiResult<List<CardProductDto>> apiResult = new ApiResult<>(1, "成功", cardProductDtos);
        apiResult.setNow(new Date());
        return apiResult;
    }
}
