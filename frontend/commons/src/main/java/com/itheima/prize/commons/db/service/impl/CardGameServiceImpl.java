package com.itheima.prize.commons.db.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.prize.commons.db.entity.CardGame;
import com.itheima.prize.commons.db.entity.CardGameExample;
import com.itheima.prize.commons.db.entity.ViewCardUserHit;
import com.itheima.prize.commons.db.mapper.CardGameMapper;
import com.itheima.prize.commons.db.service.CardGameService;
import com.itheima.prize.commons.utils.ApiResult;
import com.itheima.prize.commons.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CardGameServiceImpl implements CardGameService {
    @Autowired
    private CardGameMapper cardGameMapper;

    @Override
    public PageBean<CardGame> page(int curpage, int limit, int status) {
        //开启分页
        Page<Object> page = PageHelper.startPage(curpage, limit);
        CardGameExample example = new CardGameExample();
        //若是不等于-1，则构建下边的查询条件
        if (status != -1){
            CardGameExample.Criteria criteria = example.createCriteria().andStatusEqualTo(status); // 创建查询条件
        }
        example.setDistinct(false);
        List<CardGame> cardGames = cardGameMapper.selectByExample(example);
        return new PageBean<CardGame>(curpage, limit, page.getTotal(), cardGames);
    }

    @Override
    public ApiResult<CardGame> getByGameId(int gameid) {
        CardGame cardGame = cardGameMapper.selectByPrimaryKey(gameid);
        ApiResult<CardGame> apiResult = new ApiResult<>(1, "成功", cardGame);
        apiResult.setNow(new Date());
        return apiResult;
    }
}
