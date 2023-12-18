package com.itheima.prize.api.action;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.prize.commons.db.entity.*;
import com.itheima.prize.commons.db.mapper.CardGameMapper;
import com.itheima.prize.commons.db.mapper.GameLoadMapper;
import com.itheima.prize.commons.db.mapper.ViewCardUserHitMapper;
import com.itheima.prize.commons.utils.ApiResult;
import com.itheima.prize.commons.utils.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/game")
@Api(tags = {"活动模块"})
public class GameController {
    @Autowired
    private GameLoadMapper loadMapper;
    @Autowired
    private CardGameMapper gameMapper;
    @Autowired
    private ViewCardUserHitMapper hitMapper;

    @GetMapping("/list/{status}/{curpage}/{limit}")
    @ApiOperation(value = "活动列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="status",value = "活动状态（-1=全部，0=未开始，1=进行中，2=已结束）",example = "-1",required = true),
            @ApiImplicitParam(name = "curpage",value = "第几页",defaultValue = "1",dataType = "int", example = "1",required = true),
            @ApiImplicitParam(name = "limit",value = "每页条数",defaultValue = "10",dataType = "int",example = "3",required = true)
    })
    public ApiResult list(@PathVariable int status,@PathVariable int curpage,@PathVariable int limit) {
        //分页
        Page<Object> page = PageHelper.startPage(curpage, limit);
        CardGameExample example = new CardGameExample();
        if(status != -1){
            example.createCriteria().andStatusEqualTo(status);
        }
        //返回活动列表
        List<CardGame> cardGames = gameMapper.selectByExample(example);
        long total = page.getTotal();
        PageBean<CardGame> pageBean = new PageBean<>(curpage, limit, total, cardGames);
        return new ApiResult(1,"成功", pageBean);
    }

    @GetMapping("/info/{gameid}")
    @ApiOperation(value = "活动信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="gameid",value = "活动id",example = "1",required = true)
    })
    public ApiResult<CardGame> info(@PathVariable int gameid) {
        //获得活动信息列表
        CardGame list =gameMapper.selectByPrimaryKey(gameid);
        ApiResult<CardGame> result = new ApiResult<>(1, "成功", list);
        result.setNow(new Date());
        return result;
    }

    @GetMapping("/products/{gameid}")
    @ApiOperation(value = "奖品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="gameid",value = "活动id",example = "1",required = true)
    })
    public ApiResult<List<CardProductDto>> products(@PathVariable int gameid) {
        //获得奖品信息
        List<CardProductDto> list =loadMapper.getByGameId(gameid);
        ApiResult<List<CardProductDto>> result=new ApiResult<List<CardProductDto>>(1, "成功", list);
        result.setNow(new Date());
        return result;
    }

    @GetMapping("/hit/{gameid}/{curpage}/{limit}")
    @ApiOperation(value = "中奖列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="gameid",value = "活动id",dataType = "int",example = "1",required = true),
            @ApiImplicitParam(name = "curpage",value = "第几页",defaultValue = "1",dataType = "int", example = "1",required = true),
            @ApiImplicitParam(name = "limit",value = "每页条数",defaultValue = "10",dataType = "int",example = "3",required = true)
    })
    public ApiResult<PageBean<ViewCardUserHit>> hit(@PathVariable int gameid,@PathVariable int curpage,@PathVariable int limit) {
        //获得中奖列表
        Page<Object> page = PageHelper.startPage(curpage, limit);
        ViewCardUserHitExample example = new ViewCardUserHitExample();
        example.createCriteria().andGameidEqualTo(gameid);
        List<ViewCardUserHit> list=hitMapper.selectByExample(example);
        return new ApiResult<>(1,"成功",
                new PageBean<ViewCardUserHit>(curpage,limit,page.getTotal(), list));

    }


}
