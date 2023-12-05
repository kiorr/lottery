package com.itheima.prize.msg;

import com.itheima.prize.commons.config.RedisKeys;
import com.itheima.prize.commons.db.entity.*;
import com.itheima.prize.commons.db.mapper.CardGameMapper;
import com.itheima.prize.commons.db.mapper.CardGameProductMapper;
import com.itheima.prize.commons.db.mapper.CardGameRulesMapper;
import com.itheima.prize.commons.db.mapper.GameLoadMapper;
import com.itheima.prize.commons.utils.RedisUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 活动信息预热，每隔1分钟执行一次
 * 查找未来1分钟内（含），要开始的活动
 */
@Component
public class GameTask {
    private final static Logger log = LoggerFactory.getLogger(GameTask.class);
    @Autowired
    private CardGameMapper gameMapper;
    @Autowired
    private CardGameProductMapper gameProductMapper;
    @Autowired
    private CardGameRulesMapper gameRulesMapper;
    @Autowired
    private GameLoadMapper gameLoadMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Scheduled(cron = "0 * * * * ?")
    public void execute() {

    }
}
