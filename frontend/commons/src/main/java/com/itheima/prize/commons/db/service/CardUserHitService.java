package com.itheima.prize.commons.db.service;

import com.itheima.prize.commons.db.entity.ViewCardUserHit;
import com.itheima.prize.commons.utils.PageBean;

public interface CardUserHitService {
    Integer getByUserid(Integer id);

    PageBean<ViewCardUserHit> selectByExample(int curpage, int limit, Integer gameId, Integer userId);

    PageBean<ViewCardUserHit> page(int curpage, int limit, int gameid);
}
