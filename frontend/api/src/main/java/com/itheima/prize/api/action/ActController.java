package com.itheima.prize.api.action;

import com.itheima.prize.api.config.LuaScript;
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
        HttpSession session = request.getSession(false);
        CardUser user = (CardUser) session.getAttribute("user");
        if (Objects.isNull(user)){
            return new ApiResult(-1,"未登陆",null);
        }


        return null;
    }

    @GetMapping("/info/{gameid}")
    @ApiOperation(value = "缓存信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="gameid",value = "活动id",example = "1",required = true)
    })
    public ApiResult info(@PathVariable int gameid){
        // 最终结果
        Map<String,Object> res = new HashMap<>();
        // 活动基本信息
        CardGame game = (CardGame) redisUtil.get(RedisKeys.INFO + gameid);
        res.put(RedisKeys.INFO + gameid,game);
        // 活动 token 的list
        List<Object> tokenList = redisUtil.lrange(RedisKeys.TOKENS + gameid, 0, -1);
        // 通过 list 获取到所有的奖品信息
        Map<String,CardProductDto> productMap = new HashMap<>(); // 存放 奖品
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
