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

        //获取当前的请求用户
        CardUser user = (CardUser)request.getSession().getAttribute("user");

        //检查是否抽过奖
        if(redisUtil.hget(RedisKeys.USERGAME+user.getId(),String.valueOf(gameid))==null){
            //设置用户参加过
            redisUtil.hset(RedisKeys.USERGAME+user.getId(),String.valueOf(gameid),1);
            //发送参加信息
            CardUserGame userHit = new CardUserGame();
            userHit.setGameid(gameid);
            userHit.setCreatetime(new Date());
            userHit.setUserid(user.getId());
            rabbitTemplate.convertAndSend(RabbitKeys.EXCHANGE_DIRECT,RabbitKeys.QUEUE_PLAY,JSON.toJSONString(userHit));
        }

        //检查是否复合抽奖条件
        if (CheckUserCondition(user,gameid)){
            return  new ApiResult<>(0,"参加次数达到上限，请您关注其他活动",null);
        }

        redisUtil.incr(RedisKeys.USERENTER+user.getId()+"_"+gameid,1);
        //抽奖逻辑
        Long token = luaScript.tokenCheck(RedisKeys.TOKENS + gameid, String.valueOf(System.currentTimeMillis()));

        // result  = 1 ‘未中奖’  0 = ‘奖品已抽光’
        if (token==0)
            return  new ApiResult<>(-1,"奖品已抽光",null);
        if (token==1)
            return  new ApiResult<>(0,"未中奖",null);
        //用户中奖
        CardProductDto productDto = (CardProductDto)redisUtil.get(RedisKeys.TOKEN + gameid + "_" + token);
        redisUtil.incr(RedisKeys.USERHIT+gameid,1);

        //储存用户数据
        CardUserHit userHit = new CardUserHit();
        userHit.setHittime(new Date());
        userHit.setProductid(productDto.getId());
        userHit.setGameid(gameid);
        userHit.setUserid(user.getId());
        rabbitTemplate.convertAndSend(RabbitKeys.EXCHANGE_DIRECT,RabbitKeys.QUEUE_HIT,JSON.toJSONString(userHit));
        return   new ApiResult<>(1,"恭喜中奖",productDto);
    }




    private boolean CheckUserCondition(CardUser user ,int gameid) {
        //获取当前用用户的中奖规则
        Object userEnterCount = redisUtil.hget(RedisKeys.MAXENTER + gameid, user.getLevel().toString());
        Object userHitCount = redisUtil.hget(RedisKeys.MAXGOAL + gameid, user.getLevel().toString());
        //进行参加次数判断

        if(userEnterCount!=null&&redisUtil.get(RedisKeys.USERENTER+gameid).equals(userEnterCount)){
            return true;
        }
        //进行中将次数判断
        if (userHitCount!=null&&redisUtil.get(RedisKeys.USERHIT+gameid).equals(userEnterCount)){
            return true;
        }
        return  false;
    }

    @GetMapping("/info/{gameid}")
    @ApiOperation(value = "缓存信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="gameid",value = "活动id",example = "1",required = true)
    })
    public ApiResult info(@PathVariable int gameid){
        // 储存list对应的信息
        Map<Object,Object> map1 = new TreeMap<>();
        //查询活动信息
        Map<String, Object> map = new HashMap<>();
        CardGame cardGame = (CardGame) redisUtil.get(RedisKeys.INFO + gameid);
        map.put(RedisKeys.INFO+gameid,cardGame);
        for (Object o : redisUtil.lrange(RedisKeys.TOKENS+gameid, 0, -1)) {
            System.out.println(o);
            map1.put(new Date(Long.parseLong(o.toString())/1000),redisUtil.get(RedisKeys.TOKEN + gameid+"_"+o));
        }
        map.put(RedisKeys.MAXGOAL+gameid,redisUtil.hmget(RedisKeys.MAXGOAL+gameid));
        map.put(RedisKeys.MAXENTER+gameid,redisUtil.hmget(RedisKeys.MAXENTER+gameid));
        map.put(RedisKeys.TOKENS+gameid,map1);
        return new ApiResult(200,"缓存信息",map);
    }
}
