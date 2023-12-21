package com.itheima.prize.api.action;

import com.itheima.prize.commons.db.entity.CardUser;
import com.itheima.prize.commons.db.entity.CardUserDto;
import com.itheima.prize.commons.db.entity.ViewCardUserHit;
import com.itheima.prize.commons.db.service.CardUserGamesService;
import com.itheima.prize.commons.db.service.CardUserHitService;
import com.itheima.prize.commons.utils.ApiResult;
import com.itheima.prize.commons.utils.PageBean;
import com.itheima.prize.commons.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping(value = "/api/user")
@Api(tags = {"用户模块"})
public class UserController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CardUserHitService cardUserHitService;
    @Autowired
    private CardUserGamesService cardUserGamesService;

    @GetMapping("/info")
    @ApiOperation(value = "用户信息")
    public ApiResult info(HttpServletRequest request) {
        CardUser cardUser = (CardUser) request.getSession().getAttribute("user");
        Integer games = cardUserGamesService.getByUserId(cardUser.getId());
        Integer products = cardUserHitService.getByUserid(cardUser.getId());
        CardUserDto cardUserDto = new CardUserDto();
        BeanUtils.copyProperties(cardUser, cardUserDto);
        cardUserDto.setGames(games);
        cardUserDto.setProducts(products);
        Date date = new Date();
        ApiResult apiResult = new ApiResult(1, "成功", cardUserDto);
        apiResult.setNow(date);
        return apiResult;
    }

    @GetMapping("/hit/{gameid}/{curpage}/{limit}")
    @ApiOperation(value = "我的奖品")
    @ApiImplicitParams({
            @ApiImplicitParam(name="gameid",value = "活动id（-1=全部）",dataType = "int",example = "1",required = true),
            @ApiImplicitParam(name = "curpage",value = "第几页",defaultValue = "1",dataType = "int", example = "1"),
            @ApiImplicitParam(name = "limit",value = "每页条数",defaultValue = "10",dataType = "int",example = "3")
    })
    public ApiResult hit(@PathVariable String gameid,@PathVariable int curpage,@PathVariable int limit,HttpServletRequest request) {

        CardUser user = (CardUser) request.getSession().getAttribute("user");
        PageBean<ViewCardUserHit> page = cardUserHitService.selectByExample(curpage, limit, gameid, user.getId());
        ApiResult<PageBean> apiResult = new ApiResult<>(1, "成功", page);
        Date date = new Date();
        apiResult.setNow(date);
        return apiResult;
    }

}