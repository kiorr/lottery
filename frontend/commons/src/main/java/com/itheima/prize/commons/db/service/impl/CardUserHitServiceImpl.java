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
    public Integer getByUserid(Integer id) {
        return cardUserGamesMapper.getPrizesNumByUserId(id);
        /*return cardUserHitMapper.getCountByUserId(id);*/
    }

    @Override
    public PageBean<ViewCardUserHit> selectByExample(int curpage, int limit, Integer gameId, Integer userId) {
        /*PageHelper.startPage(curpage, limit);
        ViewCardUserHitExample example = new ViewCardUserHitExample();
        ViewCardUserHitExample.Criteria criteria = example.createCriteria(); // 创建查询条件
        criteria.andGameidEqualTo(gameId).andUseridEqualTo(userId); // 添加查询条件
        example.setDistinct(false);
        List<ViewCardUserHit> viewCardUserHits = viewCardUserHitMapper.selectByExample(example);
        return new PageBean<ViewCardUserHit>(curpage, limit, (long) viewCardUserHits.size(), viewCardUserHits);*/

        PageHelper.startPage(curpage, limit);

        Page<ViewCardUserHit> page = viewCardUserHitMapper.pageQuery(gameId, userId);

        long total = page.getTotal();
        List<ViewCardUserHit> result = page.getResult();
        return new PageBean<ViewCardUserHit>(curpage, limit, total, result);
    }
}
