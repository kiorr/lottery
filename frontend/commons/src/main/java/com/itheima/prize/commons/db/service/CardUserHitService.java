package com.itheima.prize.commons.db.service;

import com.itheima.prize.commons.db.entity.ViewCardUserHit;
import com.itheima.prize.commons.utils.PageBean;

public interface CardUserHitService {
    Integer getByUserid(String id);

    PageBean<ViewCardUserHit> selectByExample(int curpage, int limit, String gameId, String userId);

    PageBean<ViewCardUserHit> page(int curpage, int limit, String gameid);
}
