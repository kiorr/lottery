package com.itheima.prize.api.action;

import com.github.pagehelper.Page;
import com.itheima.prize.commons.config.RedisKeys;
import com.itheima.prize.commons.db.entity.CardUser;
import com.itheima.prize.commons.db.entity.CardUserDto;
import com.itheima.prize.commons.db.entity.ViewCardUserHit;
import com.itheima.prize.commons.db.entity.ViewCardUserHitExample;
import com.itheima.prize.commons.db.mapper.CardUserGamesMapper;
import com.itheima.prize.commons.db.mapper.ViewCardUserHitMapper;
import com.itheima.prize.commons.utils.ApiResult;
import com.itheima.prize.commons.utils.PageBean;
import com.itheima.prize.commons.utils.RedisUtil;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

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
        CardUser user = (CardUser) request.getSession().getAttribute("user");
        Integer gamesNum = cardUserGamesMapper.getGamesNumByUserId(user.getId());
        Integer prizesNum = cardUserGamesMapper.getPrizesNumByUserId(user.getId());
        CardUserDto cardUserDto=new CardUserDto();
        BeanUtils.copyProperties(user,cardUserDto);
        cardUserDto.setGames(gamesNum);
        cardUserDto.setProducts(prizesNum);
        ApiResult<Object> apiResult = new ApiResult<>(1,"成功",cardUserDto);
        apiResult.setNow(new Date());
        return apiResult;
    }

    @GetMapping("/hit/{gameid}/{curpage}/{limit}")
    @ApiOperation(value = "我的奖品")
    @ApiImplicitParams({
            @ApiImplicitParam(name="gameid",value = "活动id（-1=全部）",dataType = "int",example = "1",required = true),
            @ApiImplicitParam(name = "curpage",value = "第几页",defaultValue = "1",dataType = "int", example = "1"),
            @ApiImplicitParam(name = "limit",value = "每页条数",defaultValue = "10",dataType = "int",example = "3")
    })
    public ApiResult hit(@PathVariable int gameid,@PathVariable int curpage,@PathVariable int limit,HttpServletRequest request) {
        PageHelper.startPage(curpage,limit);
        Page<ViewCardUserHit> page=hitMapper.page(gameid);
//        ApiResult<Object> apiResult = new ApiResult<>(1,"成功",page);
//        List<ViewCardUserHit> viewCardUserHits = hitMapper.selectByExample(new ViewCardUserHitExample());
//        ApiResult<List<ViewCardUserHit>> apiResult = new ApiResult<>(1, "成功", viewCardUserHits);
        long total = page.getTotal();
        ApiResult<PageBean<ViewCardUserHit>> apiResult = new ApiResult<>(1, "成功", new PageBean<ViewCardUserHit>(curpage, limit, total, page));
        return apiResult;
    }


}