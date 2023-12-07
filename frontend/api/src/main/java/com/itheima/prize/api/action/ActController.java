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
        CardUser user = (CardUser)request.getSession().getAttribute("user");
        long now = new Date().getTime();
        CardGame cardGame = (CardGame) redisUtil.get(RedisKeys.INFO + gameid);
        //判断用户是否登录
        if(user==null){
            ApiResult<Object> result = new ApiResult<>(-1,"未登录",null);
            result.setNow(new Date());
            return result;
        }
        //获取活动开始时间和结束时间
        long startime = cardGame.getStarttime().getTime();
        long endtime = cardGame.getEndtime().getTime();
        //判断活动是否开始
        if(startime>now){
            ApiResult<Object> result = new ApiResult<>(-1,"活动未开始",null);
            result.setNow(new Date());
            return result;
        }
        //判断活动是否结束
        if(endtime<now){
            ApiResult<Object> result = new ApiResult<>(-1,"活动已结束",null);
            result.setNow(new Date());
            return result;
        }
        //判断中奖次数是否为空,为空就存入缓存
        if(redisUtil.get(user.getUname()+gameid+"_goal")==null){
            redisUtil.set(user.getUname()+gameid+"_goal",0);
        }
        //判断抽奖次数是否为空，为空就存入缓存
        if(redisUtil.get(user.getUname()+gameid+"_enter")==null) {
            redisUtil.set(user.getUname() + gameid + "_enter", 0);
        }
        //用rabbitmq队列发送抽奖消息
        if((Integer) redisUtil.get(user.getUname()+gameid+"_enter")==0){
            CardUserGame cardUserGame = new CardUserGame();
            cardUserGame.setGameid(gameid);
            cardUserGame.setUserid(user.getId());
            cardUserGame.setCreatetime(cardGame.getStarttime());
            rabbitTemplate.convertAndSend(RabbitKeys.EXCHANGE_DIRECT,RabbitKeys.QUEUE_PLAY,JSON.toJSONString(cardUserGame));
        }
        //根据用户等级取出最大抽奖次数
        Integer entertimes = (Integer) redisUtil.hget(RedisKeys.MAXENTER + gameid, user.getLevel() + "");
        //根据用户等级取出最大中奖次数
        Integer goaltimes = (Integer) redisUtil.hget(RedisKeys.MAXGOAL + gameid, user.getLevel() + "");
        //判断抽奖次数是否用完
        if(entertimes<(Integer) redisUtil.get(user.getUname()+gameid+"_enter")){
            ApiResult<Object> result = new ApiResult<>(-1, "您的抽奖次数已用完", null);
            result.setNow(new Date());
            return result;
        }
        //判断中奖次数是否用完
        if(goaltimes<(Integer) redisUtil.get(user.getUname() + gameid + "_enter")){
            ApiResult<Object> result = new ApiResult<>(-1,"您已达到最大中奖次数",null);
            result.setNow(new Date());
            return result;
        }
        Long token = luaScript.tokenCheck(RedisKeys.TOKENS+gameid,String.valueOf(new Date().getTime()));
        if(token == 0){
            redisUtil.incr(user.getUname() + gameid + "_enter",1);
            return new ApiResult(-1,"奖品已抽光",null);
        }else if(token == 1){
            redisUtil.incr(user.getUname() + gameid + "_enter",1);
            return new ApiResult(0,"未中奖",null);
        }
        redisUtil.incr(user.getUname()+gameid+"_goal",1);
        redisUtil.incr(user.getUname() + gameid + "_enter",1);
        CardProductDto cardProductDto = (CardProductDto) redisUtil.get(RedisKeys.TOKEN + gameid + "_" + token);
        //用rabbitmq发送中奖消息
        CardUserHit cardUserHit = new CardUserHit();
        cardUserHit.setProductid(cardProductDto.getId());
        cardUserHit.setUserid(user.getId());
        cardUserHit.setGameid(gameid);
        cardUserHit.setHittime(new Date());
        rabbitTemplate.convertAndSend(RabbitKeys.EXCHANGE_DIRECT,RabbitKeys.QUEUE_HIT,JSON.toJSONString(cardUserHit));
        ApiResult<Object> result = new ApiResult<>(1,"恭喜中奖",cardProductDto);
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
