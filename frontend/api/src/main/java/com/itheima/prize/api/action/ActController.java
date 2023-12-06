package com.itheima.prize.api.action;

import com.alibaba.fastjson.JSON;
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
        //获得用户信息
        CardUser user = (CardUser) request.getSession().getAttribute("user");
        //获取活动信息
        CardGame cardGame = (CardGame) redisUtil.get(RedisKeys.INFO + gameid);
        Date now = new Date();
        //时间判断活动开始结束
        if(cardGame.getStarttime().compareTo(now)>0){
            ApiResult<Object> result1 = new ApiResult<>(-1,"活动未开始",null,new Date());
            return result1;
        }
        if(cardGame.getEndtime().compareTo(now)<0){
            ApiResult<Object> result2 = new ApiResult<>(-1,"活动已结束",null,new Date());
            return result2;
        }
        if(user==null){
            ApiResult<Object> result3 = new ApiResult<>(-1,"未登陆",null,new Date());
            return result3;
        }
        //判断用户抽奖次数
        if(redisUtil.hget(RedisKeys.MAXENTER + gameid ,user.getLevel() + "") != null
                && redisUtil.get(RedisKeys.USERENTER + gameid + "_" + user.getId())!=null
                && ((int)redisUtil.hget(RedisKeys.MAXENTER + gameid ,user.getLevel() + "") <
                (int)redisUtil.get(RedisKeys.USERENTER + gameid + "_" + user.getId()))){
            ApiResult<Object> result4 = new ApiResult<>(-1,"您的抽奖次数已用完",null,new Date());
            return result4;
        }
        //判断用户中奖次数
        if(redisUtil.hget(RedisKeys.MAXGOAL + gameid ,user.getLevel() + "") != null
                && redisUtil.get(RedisKeys.USERHIT + gameid + "_" + user.getId())!=null
                && (int)redisUtil.hget(RedisKeys.MAXGOAL + gameid ,user.getLevel() + "") <
                (int)redisUtil.get(RedisKeys.USERHIT + gameid + "_" + user.getId())){
            ApiResult<Object> result5 = new ApiResult<>(-1,"您已达到最大抽奖次数",null,new Date());
            return result5;
        }
        //lua对token判断，返回奖品抽的情况，原子性操作
        Long token = luaScript.tokenCheck(RedisKeys.TOKENS +gameid,String.valueOf(new Date().getTime()));
        if(token == 0){
            return new ApiResult(-1,"奖品已抽光",null);
        }else if(token == 1){
            return new ApiResult(0,"未中奖",null);
        }
        //参与活动
        if(redisUtil.incr(RedisKeys.USERGAME + gameid + "_" +user.getId(),1) == 1){
            CardUserGame cardUserGame = new CardUserGame(user.getId(),gameid,new Date());
            rabbitTemplate.convertAndSend(RabbitKeys.EXCHANGE_DIRECT,RabbitKeys.QUEUE_PLAY,JSON.toJSONString(cardUserGame));
        }
        //increment中奖次数
        redisUtil.incr(RedisKeys.USERHIT + gameid + "_" + user.getId(),  1);
        CardProduct cardProduct = (CardProduct) redisUtil.get(RedisKeys.TOKEN + gameid + "_" + token);
        CardUserHit cardUserHit = new CardUserHit(gameid,user.getId(),cardProduct.getId(), new Date());
        rabbitTemplate.convertAndSend(RabbitKeys.EXCHANGE_DIRECT,RabbitKeys.QUEUE_HIT, JSON.toJSONString(cardUserHit));
        ApiResult result = new ApiResult<>(1,"恭喜中奖", cardProduct);
        result.setNow(new Date());
        return result;
    }

    @GetMapping("/info/{gameid}")
    @ApiOperation(value = "缓存信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="gameid",value = "活动id",example = "1",required = true)
    })
    public ApiResult info(@PathVariable int gameid){
        //将所有本活动相关的信息放在⼀个Map中集中返回
        HashMap<String, Object> gameMap = new HashMap<>();
        //返回list缓存token
        List<Object> tokens = redisUtil.lrange(RedisKeys.TOKENS + gameid,0,-1);
        //根据token查找缓存 token到实际奖品之间的映射关系
        HashMap<String, CardProductDto> cardMap = new HashMap<>();
        for(Object token : tokens){
            //实际Redis⾥的令牌是时间戳，返回前可以将其转成⽇期类型
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date((Long) token/1000);
            String time = sdf.format(date);
            CardProductDto cardProductDto = (CardProductDto) redisUtil.get(RedisKeys.TOKEN + gameid + "_" + token);
            cardMap.put(time,cardProductDto);
        }
        gameMap.put(RedisKeys.INFO + gameid, redisUtil.get(RedisKeys.INFO + gameid));
        gameMap.put(RedisKeys.TOKENS + gameid, cardMap);
        gameMap.put(RedisKeys.MAXGOAL + gameid, redisUtil.hmget(RedisKeys.MAXGOAL + gameid));
        gameMap.put(RedisKeys.MAXENTER + gameid, redisUtil.hmget(RedisKeys.MAXENTER + gameid));
        ApiResult apiResult = new ApiResult(200, "缓存信息", gameMap);
        apiResult.setNow(new Date());
        return apiResult;
    }
}
