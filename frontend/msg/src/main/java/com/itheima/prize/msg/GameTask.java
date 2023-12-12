package com.itheima.prize.msg;

import com.alibaba.fastjson.JSON;
import com.itheima.prize.commons.config.RedisKeys;
import com.itheima.prize.commons.db.entity.*;
import com.itheima.prize.commons.db.mapper.CardGameMapper;
import com.itheima.prize.commons.db.mapper.CardGameProductMapper;
import com.itheima.prize.commons.db.mapper.CardGameRulesMapper;
import com.itheima.prize.commons.db.mapper.GameLoadMapper;
import com.itheima.prize.commons.utils.RedisUtil;
import com.sun.org.apache.bcel.internal.generic.LREM;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 活动信息预热，每隔1分钟执行一次
 * 查找未来1分钟内（含），要开始的活动，并初始化进Redis
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

    @Scheduled(cron = " 1 * * * * ?")
    public void execute() {
        // 进行数据的查询，查询 card——game中的每个表
        Date start = new Date();
        Date end = new Date(System.currentTimeMillis() + 1000 * 60);
        //数据查询
        List<CardGame> gameList = gameMapper.getByStartTime(start, end);
        //放入redis中

        gameList.forEach(game ->{
            //获取抽奖策略
            List<CardGameRules> rulesList = gameRulesMapper.getByGameId(game.getId());
            //缓存抽奖策略
            log.info("加载中奖策略----------------->");
            for (CardGameRules rules : rulesList) {
                log.info("加载中奖策略{}",rules);
                redisUtil.hset(RedisKeys.MAXGOAL + game.getId(), rules.getUserlevel()+"",rules.getGoalTimes());
                redisUtil.hset(RedisKeys.MAXENTER + game.getId(), rules.getUserlevel()+"", rules.getEnterTimes());
            }
            log.info("中奖策略加载完毕----------------->");

            //将活动信息存入redis
            redisUtil.set(RedisKeys.INFO + game.getId(), game);
            //查询物品信息
            List<CardProductDto> productList = gameLoadMapper.getByGameId(game.getId());
            //根据商品和总数进行分配 token
            allocateToken(productList,game);
            log.info("更改活动状态");
            game.setStatus(1);
            gameMapper.updateByPrimaryKey(game);
            log.info("缓存活动信息结束--------------------------->");
        } );
    }

    private void allocateToken(List<CardProductDto> productList, CardGame game) {
        log.info("进行令牌分配--------------------------->");
        //用来储存令牌
        TreeSet<Long> tokenList =new TreeSet<>((o1, o2) -> o1>o2?1:-1);
        for (CardProductDto productDto : productList) {
            for (int integer = 0; integer < productDto.getAmount(); integer++) {
                long token = getToken(game,productDto);
                tokenList.add(token);
            }
        }
        //排序
        //将令牌推入集合中
        redisUtil.rightPushAll(RedisKeys.TOKENS + game.getId(),  tokenList);
        log.info("令牌分配完毕-------------------------------------->");
    }

    private long getToken(CardGame game,CardProductDto productDto) {
        long startMs = game.getStarttime().getTime();
        long endMs = game.getEndtime().getTime();
        long duration = endMs - startMs;
        long rnd = startMs + new Random().nextInt((int) duration);
        long token=rnd * 1000 + new Random().nextInt(999);
        //当前令牌对应的奖品  有效事件为活动的持续时间 单位 s
        long expert = (duration/1000+1);
        redisUtil.set(RedisKeys.TOKEN + game.getId() +"_"+token,productDto,expert);
        return token;
    }
}
