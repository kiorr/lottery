package com.itheima.prize.api.action;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.itheima.prize.api.service.UserService;
import com.itheima.prize.commons.config.RedisKeys;
import com.itheima.prize.commons.db.entity.CardUser;
import com.itheima.prize.commons.db.entity.CardUserDto;
import com.itheima.prize.commons.db.entity.ViewCardUserHit;
import com.itheima.prize.commons.db.entity.ViewCardUserHitExample;
import com.itheima.prize.commons.db.mapper.CardUserGamesMapper;
import com.itheima.prize.commons.db.mapper.ViewCardUserHitMapper;
import com.itheima.prize.commons.utils.ApiResult;
import com.itheima.prize.commons.utils.BeanCopyUtils;
import com.itheima.prize.commons.utils.PageBean;
import com.itheima.prize.commons.utils.RedisUtil;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
@Api(tags = {"用户模块"})
public class UserController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ViewCardUserHitMapper hitMapper;
    @Autowired
    private CardUserGamesMapper cardUserGamesMapper;

    @GetMapping("/info")
    @ApiOperation(value = "用户信息")
    public ApiResult info(HttpServletRequest request) {
        // 在 request 中获取 session
        HttpSession session = request.getSession(false);
        if(ObjectUtils.isEmpty(session)) {
           return new ApiResult<>(-1,"当前用户为登入！",null);
        }
        // 获取 user
        CardUser user = (CardUser) session.getAttribute("user");
        // 复制到dto
        CardUserDto cardUserDto = new CardUserDto(user);
        // 查询比赛个数、产品个数
        Integer games = cardUserGamesMapper.getGamesNumByUserId(user.getId());
        Integer prizes = cardUserGamesMapper.getPrizesNumByUserId(user.getId());
        cardUserDto.setGames(games);
        cardUserDto.setProducts(prizes);
//        return new ApiResult<>(1,"成功", JSON.toJSONString(user);
        return new ApiResult<>(1,"成功",cardUserDto);
    }

    @GetMapping("/hit/{gameid}/{curpage}/{limit}")
    @ApiOperation(value = "我的奖品")
    @ApiImplicitParams({
            @ApiImplicitParam(name="gameid",value = "活动id（-1=全部）",dataType = "int",example = "1",required = true),
            @ApiImplicitParam(name = "curpage",value = "第几页",defaultValue = "1",dataType = "int", example = "1"),
            @ApiImplicitParam(name = "limit",value = "每页条数",defaultValue = "10",dataType = "int",example = "3")
    })
    public ApiResult hit(@PathVariable int gameid,@PathVariable int curpage,@PathVariable int limit,HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(ObjectUtils.isEmpty(session)) {
            return new ApiResult<>(-1,"登录超时",null);
        }
        CardUser user = (CardUser) session.getAttribute("user");
        Page<ViewCardUserHit> page = PageHelper.startPage(curpage,limit);
        List<ViewCardUserHit> viewCardUserHits = hitMapper.selectAll(user.getId(), gameid);
        PageInfo<ViewCardUserHit> pageInfo =new PageInfo<>(viewCardUserHits);
        return new ApiResult<>(1,"成功",new PageBean<>(curpage,limit,pageInfo.getTotal(),pageInfo.getList()));
    }


}
