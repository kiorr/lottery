package com.itheima.prize.commons.db.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.prize.commons.db.entity.ViewCardUserHit;
import com.itheima.prize.commons.db.entity.ViewCardUserHitExample;
import com.itheima.prize.commons.db.mapper.CardUserGamesMapper;
import com.itheima.prize.commons.db.mapper.CardUserHitMapper;
import com.itheima.prize.commons.db.mapper.ViewCardUserHitMapper;
import com.itheima.prize.commons.db.service.CardUserHitService;
import com.itheima.prize.commons.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardUserHitServiceImpl implements CardUserHitService {
    @Autowired
    private CardUserHitMapper cardUserHitMapper;
    @Autowired
    private ViewCardUserHitMapper viewCardUserHitMapper;
    @Autowired
    private CardUserGamesMapper cardUserGamesMapper;

    @Override
    public Integer getByUserid(String id) {
        return cardUserGamesMapper.getPrizesNumByUserId(id);
        /*return cardUserHitMapper.getCountByUserId(id);*/
    }

    @Override
    public PageBean<ViewCardUserHit> selectByExample(int curpage, int limit, String gameId, String userId) {
        /*PageHelper.startPage(curpage, limit);
        ViewCardUserHitExample example = new ViewCardUserHitExample();
        ViewCardUserHitExample.Criteria criteria = example.createCriteria(); // 创建查询条件
        criteria.andGameidEqualTo(gameId).andUseridEqualTo(userId); // 添加查询条件
        example.setDistinct(false);
        List<ViewCardUserHit> viewCardUserHits = viewCardUserHitMapper.selectByExample(example);
        return new PageBean<ViewCardUserHit>(curpage, limit, (long) viewCardUserHits.size(), viewCardUserHits);*/

        //开启查询条件
        PageHelper.startPage(curpage, limit);
        //分页数据
        Page<ViewCardUserHit> page = viewCardUserHitMapper.pageQuery(gameId, userId);
        //取出值，去封装到PageBean
        long total = page.getTotal();
        List<ViewCardUserHit> result = page.getResult();
        return new PageBean<ViewCardUserHit>(curpage, limit, total, result);
    }

    @Override
    public PageBean<ViewCardUserHit> page(int curpage, int limit, String gameid) {
        Page<Object> page = PageHelper.startPage(curpage, limit);
        ViewCardUserHitExample example = new ViewCardUserHitExample();
        ViewCardUserHitExample.Criteria criteria = example.createCriteria(); // 创建查询条件
        criteria.andGameidEqualTo(gameid); // 添加查询条件
        example.setDistinct(false);
        List<ViewCardUserHit> viewCardUserHits = viewCardUserHitMapper.selectByExample(example);
        return new PageBean<ViewCardUserHit>(curpage, limit, page.getTotal(), viewCardUserHits);
    }
}
