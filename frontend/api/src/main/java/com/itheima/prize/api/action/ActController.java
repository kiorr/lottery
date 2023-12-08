package com.itheima.prize.api.action;

import com.itheima.prize.api.config.LuaScript;
import com.itheima.prize.commons.config.RabbitKeys;
import com.itheima.prize.commons.config.RedisKeys;
import com.itheima.prize.commons.db.entity.*;
import com.itheima.prize.commons.db.mapper.CardGameMapper;
import com.itheima.prize.commons.utils.ApiResult;
import com.itheima.prize.commons.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/act")
@Api(tags = {"抽奖模块"})
public class ActController {

    @Autowired
    private CardGameMapper cardGameMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private LuaScript luaScript;

    @GetMapping("/go/{gameid}")
    @ApiOperation(value = "抽奖")
    @ApiImplicitParams({
            @ApiImplicitParam(name="gameid",value = "活动id",example = "1",required = true)
    })
    public ApiResult<Object> act(@PathVariable int gameid, HttpServletRequest request){
        // 获取当前的用户
        HttpSession session = request.getSession(false);
        CardUser user = (CardUser) session.getAttribute("user");
        if (Objects.isNull(user)){
            return new ApiResult(-1,"未登陆",null);
        }

        // 获取当前活动信息，判断活动是否开始
        CardGame curGame = (CardGame) redisUtil.get(RedisKeys.INFO + gameid);
        long starttime = curGame.getStarttime().getTime();
        Date curTime = new Date();
        if(Objects.isNull(curGame) || curGame.getStarttime().after(new Date())) {
            return new ApiResult<>(-1,"当前活动未开始",null);
        }

        // 判断 活动 是否结束
        if (curTime.after(curGame.getEndtime())){
            return new ApiResult(-1,"活动已结束",null);
        }

        // 判断用户是否还有抽奖机会
        Integer count = (Integer) redisUtil.get(RedisKeys.USERENTER + gameid+ "_"+user.getId().toString());
        if(count == null) {
            redisUtil.set(RedisKeys.USERHIT+gameid+"_"+user.getId(),0,
                    (curGame.getEndtime().getTime() - curTime.getTime())/1000);
        }
        // 获取最大抽奖数
        Integer maxCount = (Integer) redisUtil.hget( RedisKeys.MAXGOAL + curGame.getId(), user.getLevel()+"");
        // 可以抽无限次
        if(Objects.isNull(maxCount) || maxCount == 0) {
            maxCount = 0;
        }
        if(maxCount > 0 &&maxCount <= count) {
            return new ApiResult(-1,"您抽奖次数用光了",null);
        }

        // 开始抽奖
        // 增加一次抽奖数
        redisUtil.incr(RedisKeys.USERENTER + gameid+ "_"+user.getId(),1);
        Long token = luaScript.tokenCheck(RedisKeys.TOKENS + gameid, curTime.getTime() + "");
        // 判断 token 奖池空有两种情况
        // 1. 所有奖品都没到时间 2. 奖品抽完了
        if( token == 0 ||Objects.isNull(token)) {
            return new ApiResult(-1,"当前奖池已空",null);
        }else if(token == 1) {
            return new ApiResult(-1,"未中奖，不要灰心，再来一次！",null);
        }
        // 中奖了，处理结果
        CardProduct product = (CardProduct) redisUtil.get(RedisKeys.TOKEN + gameid +"_"+token);
        // 中奖次数+1
        redisUtil.incr(RedisKeys.USERHIT+gameid+"_"+user.getId(),1);
        // 存入消息队列
        Map map = new HashMap();
        map.put("gameid",gameid);
        map.put("userid",user.getId());
        map.put("productid",product.getId());
        map.put("hittime",curTime.getTime());
        rabbitTemplate.convertAndSend(RabbitKeys.EXCHANGE_DIRECT,RabbitKeys.QUEUE_HIT,map);
        return new ApiResult(1,"恭喜中奖",product);
    }

    @GetMapping("/info/{gameid}")
    @ApiOperation(value = "缓存信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="gameid",value = "活动id",example = "1",required = true)
    })
    public ApiResult info(@PathVariable int gameid){
        // 最终结果
        Map<String,Object> res = new TreeMap<>();
        // 活动基本信息
        CardGame game = (CardGame) redisUtil.get(RedisKeys.INFO + gameid);
        res.put(RedisKeys.INFO + gameid,game);
        // 活动 token 的list
        List<Object> tokenList = redisUtil.lrange(RedisKeys.TOKENS + gameid, 0, -1);
        // 通过 list 获取到所有的奖品信息
        Map<String,CardProductDto> productMap = new TreeMap<>(); // 存放 奖品
        for(Object token : tokenList) {
            CardProductDto cardProductDto = (CardProductDto) redisUtil.get(RedisKeys.TOKEN + gameid +"_"+ token);
            Long times = Long.valueOf(token.toString());

            Date date = new Date(times/1000);
            productMap.put(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date),cardProductDto);
        }
        // 奖品信息放入到 res 中
        res.put(RedisKeys.TOKENS + gameid,productMap);
        // 存取最大中奖数
        Map<Object, Object> gameMaxGoal = redisUtil.hmget(RedisKeys.MAXGOAL + gameid);
        res.put(RedisKeys.MAXGOAL + gameid,gameMaxGoal);
        // 存取最大抽奖数
        Map<Object, Object> gameMaxEnter = redisUtil.hmget(RedisKeys.MAXENTER + gameid);
        res.put(RedisKeys.MAXENTER + gameid,gameMaxEnter);
        return new ApiResult(200,"缓存信息",res);
    }
}
