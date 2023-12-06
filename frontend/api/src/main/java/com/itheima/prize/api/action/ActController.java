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
import org.springframework.util.StringUtils;
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
        //取出session 用来判断是否登陆
        CardUser user = (CardUser) request.getSession().getAttribute("user");
        //根据gameid去返回对应的活动信息
        CardGame game = (CardGame) redisUtil.get(RedisKeys.INFO + gameid);
        Date date = new Date();
        //如果开始时间比当前时间晚
        if (game.getStarttime().compareTo(date) > 0){
            ApiResult apiResult = new ApiResult(-1, "活动未开始", null);
            apiResult.setNow(date);
        }
        //如果结束时间比当前时间早
        if (game.getEndtime().compareTo(date) < 0){
            ApiResult apiResult = new ApiResult(-1, "活动已结束", null);
            apiResult.setNow(date);
        }
        //判断用户是否登陆
        if (user == null){
            ApiResult apiResult = new ApiResult(-1, "未登录", null);
            apiResult.setNow(date);
        }
        //抽奖次数判断
        if (!(!(StringUtils.isEmpty(redisUtil.hget(RedisKeys.MAXENTER + gameid, user.getLevel().toString())))
                        && ((int)redisUtil.hget(RedisKeys.MAXENTER + gameid, user.getLevel().toString()) >
                (int)redisUtil.incr(RedisKeys.USERENTER+gameid+"_"+user.getId(),1)))){
            ApiResult apiResult = new ApiResult(0, "您的抽奖次数已用完", null);
            apiResult.setNow(date);
            return apiResult;
        }
        //中奖次数判断
        if (!((redisUtil.get(RedisKeys.USERHIT+gameid+"_"+user.getId())  == null)  ||
                !((StringUtils.isEmpty(redisUtil.hget(RedisKeys.MAXGOAL + gameid, user.getLevel().toString())))
                && ((int)redisUtil.hget(RedisKeys.MAXGOAL + gameid, user.getLevel().toString()) >
                (int)redisUtil.get(RedisKeys.USERHIT + gameid + "_" + user.getId()))))){
            ApiResult apiResult = new ApiResult(0, "您已达到最大中奖数", null);
            apiResult.setNow(date);
            return apiResult;
        }
        //执行lua脚本的逻辑
        Long token = luaScript.tokenCheck(RedisKeys.TOKENS + gameid,String.valueOf(new Date().getTime()));
        //判断是否为1，若为1就是没发送消息
        if (redisUtil.incr(RedisKeys.USERGAME + gameid + "_" + user.getId(), 1) == 1){
            CardUserGame cardUserGame = new CardUserGame(user.getId(), gameid, new Date());
            rabbitTemplate.convertAndSend(RabbitKeys.EXCHANGE_DIRECT, RabbitKeys.QUEUE_PLAY, JSON.toJSONString(cardUserGame));
        }
        if(token == 0){
            //奖品已抽光
            ApiResult apiResult = new ApiResult(-1, "奖品已抽光", null);
            apiResult.setNow(date);
            return apiResult;
        }else if(token == 1){
            //未中奖
            ApiResult apiResult = new ApiResult(0, "未中奖", null);
            apiResult.setNow(date);
            return apiResult;
        }
        //中奖次数 + 1
        redisUtil.incr(RedisKeys.USERHIT + gameid + "_" + user.getId(), 1);
        //发送消息，哪个奖品被抢到了，封装CardUserHit对象，发送
        CardProduct cardProduct = (CardProduct) redisUtil.get(RedisKeys.TOKEN + gameid + "_" + token);
        CardUserHit cardUserHit = new CardUserHit(gameid, user.getId(), cardProduct.getId(), new Date());
        rabbitTemplate.convertAndSend(RabbitKeys.EXCHANGE_DIRECT, RabbitKeys.QUEUE_HIT, JSON.toJSONString(cardUserHit));
        ApiResult apiResult = new ApiResult(1, "恭喜中奖", cardProduct);
        apiResult.setNow(date);
        return apiResult;
    }

    @GetMapping("/info/{gameid}")
    @ApiOperation(value = "缓存信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="gameid",value = "活动id",example = "1",required = true)
    })
    public ApiResult info(@PathVariable int gameid){
        //创建一个map用来存储返回的数据
        HashMap<String, Object> gameMap = new HashMap<>();
        gameMap.put(RedisKeys.INFO + gameid, redisUtil.get(RedisKeys.INFO + gameid));
        HashMap<String, CardProductDto> stringCardProductDtoHashMap = new HashMap<>();
        List<Object> tokens = redisUtil.lrange(RedisKeys.TOKENS + gameid, 0, -1);
        for (Object token : tokens) {
            // 创建一个SimpleDateFormat对象，指定日期格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 将时间戳转换为Date对象
            Date date = new Date((long)token / 1000);
            // 使用SimpleDateFormat格式化Date对象，得到字符串形式的时间
            String time = sdf.format(date);
            //根据每个token去寻找redis里边缓存的东西 (这块是缓存的cardProductid)
            CardProductDto cardProductDto = (CardProductDto) redisUtil.get(RedisKeys.TOKEN + gameid + "_" + token);
            stringCardProductDtoHashMap.put(time, cardProductDto);
        }
        gameMap.put(RedisKeys.TOKENS + gameid, stringCardProductDtoHashMap);
        gameMap.put(RedisKeys.MAXGOAL + gameid, redisUtil.hmget(RedisKeys.MAXGOAL + gameid));
        gameMap.put(RedisKeys.MAXENTER + gameid, redisUtil.hmget(RedisKeys.MAXENTER + gameid));
        //返回数据
        ApiResult apiResult = new ApiResult(200, "缓存信息", gameMap);
        apiResult.setNow(new Date());
        return apiResult;
    }
}
