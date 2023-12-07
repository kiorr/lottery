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
import java.util.stream.Collectors;

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


        List<CardGame> beginGames = getCardGame();
        if(beginGames.size() == 0) {
            log.info("当前没有需要预热的活动");
            return;
        }
        // 进行活动预热
        for (CardGame game : beginGames) {
            // 获取活动开始时间
            long starttime = game.getStarttime().getTime();
            // 获取活动结束时间
            long endtime = game.getEndtime().getTime();
            // 获取离现在结束时间剩多久
            long expire =  (endtime - new Date().getTime())/1000;
            // 活动之间的间隔时间，生成令牌用
            long duration = endtime - starttime;
            // 存入活动基本信息
            redisUtil.set(RedisKeys.INFO+game.getId(),game,-1);
            // 获取这个活动的奖品
            List<CardProductDto> cardProductDtos = gameLoadMapper.getByGameId(game.getId());
            Map<Integer,CardProductDto> productMap = new HashMap<>(cardProductDtos.size());
            // 将所有的奖品数据放入到 map 中进行管理
            cardProductDtos.forEach(cardProductDto ->productMap.put(cardProductDto.getId(),cardProductDto));
            // 获取 奖品的数量
            List<CardGameProduct> productList = gameProductMapper.getProductById(game.getId());
            // 将 奖品放入到 缓存中
            //令牌桶
            List<Long> tokenList = new ArrayList();
            for(CardGameProduct cgp: productList) {
                long token = getToken(starttime,duration);
                // 添加到令牌桶中
                tokenList.add(token);
                // 将奖品放入到缓存当中 ，这个映射关系如下
                //        key            keys         value
                //game_token_+gameid    token    cardProductDto
                redisUtil.set(RedisKeys.TOKEN + game.getId()
                        +"_"+token,productMap.get(cgp.getProductid()),expire);
            }
            //排序后放入redis队列
            Collections.sort(tokenList);

            //从右侧压入队列，从左到右，时间戳逐个增大
            redisUtil.rightPushAll(RedisKeys.TOKENS + game.getId(),tokenList);
            redisUtil.expire(RedisKeys.TOKENS + game.getId(),expire);

            // 获取奖品规则信息
            List<CardGameRules> rules = gameRulesMapper.selectByGameid(game.getId());

            // 将奖品规则信息存入到缓存中
            for (CardGameRules rule : rules) {
                // 存取最大中奖数
                redisUtil.hset(RedisKeys.MAXGOAL + game.getId(),
                        rule.getUserlevel()+"",rule.getGoalTimes());
                // 存取最大抽奖数
                redisUtil.hset(RedisKeys.MAXENTER +
                        game.getId(),rule.getUserlevel()+"",rule.getEnterTimes());
            }
            // 设置过期时间
            redisUtil.expire(RedisKeys.MAXGOAL +game.getId(),expire);
            redisUtil.expire(RedisKeys.MAXENTER +game.getId(),expire);

            //活动状态变更为已预热，禁止管理后台再随便变动
            game.setStatus(1);
            gameMapper.updateByPrimaryKey(game);
        }

    }

    private long getToken(long starttime, long duration) {
        // token的组成 时间戳(活动的持续时间) * 1000 + 3位随机数
        long rnd = starttime + new Random().nextInt((int)duration);
        // 生成最终的token
        long token = rnd * 1000 + new Random().nextInt(999);
        return token;
    }

    // 判断是否有距开始还有小于1分钟的活动
    private List<CardGame> getCardGame() {

        Date now = new Date();
        //查询将来1分钟内要开始的活动
        CardGameExample example = new CardGameExample();
        CardGameExample.Criteria criteria = example.createCriteria();
        //开始时间大于当前时间
        criteria.andStarttimeGreaterThan(now);
        //小于等于（当前时间+1分钟）
        criteria.andStarttimeLessThanOrEqualTo(DateUtils.addMinutes(now,1));
        List<CardGame> list = gameMapper.selectByExample(example);

        return list;
    }
}
