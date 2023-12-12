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

    /**
     * 从session中获取用户对象
     * @param request
     * @return 返回用户的dto对象
     */
    @GetMapping("/info")
    @ApiOperation(value = "用户信息")
    public ApiResult info(HttpServletRequest request) {
        CardUser user =(CardUser) request.getSession().getAttribute("user");
        CardUserDto userDto = new CardUserDto();
        //属性拷贝
        BeanUtils.copyProperties(user,userDto);
        //将密码设置为 null
        userDto.setPasswd(null);
        userDto.setIdcard(null);
        //查找游戏对象
        userDto.setGames(cardUserGamesMapper.getGamesNumByUserId(userDto.getId()));
        userDto.setProducts(cardUserGamesMapper.getPrizesNumByUserId(userDto.getId()));
        return new ApiResult(1,"成功",userDto);
    }

    @GetMapping("/hit/{gameid}/{curpage}/{limit}")
    @ApiOperation(value = "我的奖品")
    @ApiImplicitParams({
            @ApiImplicitParam(name="gameid",value = "活动id（-1=全部）",dataType = "int",example = "1",required = true),
            @ApiImplicitParam(name = "curpage",value = "第几页",defaultValue = "1",dataType = "int", example = "1"),
            @ApiImplicitParam(name = "limit",value = "每页条数",defaultValue = "10",dataType = "int",example = "3")
    })
    public ApiResult hit(@PathVariable int gameid,@PathVariable int curpage,@PathVariable int limit,HttpServletRequest request) {
        CardUser user =(CardUser) request.getSession().getAttribute("user");
        if (user==null)
            return new ApiResult(0,"登录超时",null);
        Page<ViewCardUserHit> page = PageHelper.startPage(curpage,limit,"hittime DESC");
        Page<ViewCardUserHit> pageResult=hitMapper.selectPageList(page,gameid,user.getId());

        return new ApiResult(1,"成功", new PageBean<>(curpage, limit, pageResult.getTotal(), pageResult.getResult()));
    }


}