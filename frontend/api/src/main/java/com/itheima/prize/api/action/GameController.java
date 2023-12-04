package com.itheima.prize.api.action;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
       List<CardGame> cardGames = gameMapper.selectAllGame(status);
        Page<CardGame> page = PageHelper.startPage(curpage,limit);
        PageInfo<CardGame> gamePageInfo = new PageInfo<>(cardGames);
        return new ApiResult<>(1,"成功",new PageBean<>(curpage,limit,gamePageInfo.getTotal(),gamePageInfo.getList()));
    }

    @GetMapping("/info/{gameid}")
    @ApiOperation(value = "活动信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="gameid",value = "活动id",example = "1",required = true)
    })
    public ApiResult<CardGame> info(@PathVariable int gameid) {

        CardGame cardGame = gameMapper.selectByPrimaryKey(gameid);

        return new ApiResult<>(1,"成功",cardGame);
    }

    @GetMapping("/products/{gameid}")
    @ApiOperation(value = "奖品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="gameid",value = "活动id",example = "1",required = true)
    })
    public ApiResult<List<CardProductDto>> products(@PathVariable int gameid) {

        List<CardProductDto> cardProductDtos = gameMapper.selectProductByGameid(gameid);
        return new ApiResult<>(1,"成功",cardProductDtos);
    }

    @GetMapping("/hit/{gameid}/{curpage}/{limit}")
    @ApiOperation(value = "中奖列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="gameid",value = "活动id",dataType = "int",example = "1",required = true),
            @ApiImplicitParam(name = "curpage",value = "第几页",defaultValue = "1",dataType = "int", example = "1",required = true),
            @ApiImplicitParam(name = "limit",value = "每页条数",defaultValue = "10",dataType = "int",example = "3",required = true)
    })
    public ApiResult<PageBean<ViewCardUserHit>> hit(@PathVariable int gameid,@PathVariable int curpage,@PathVariable int limit) {

        // 查询符合条件的数据
        List<ViewCardUserHit> viewCardUserHits = hitMapper.selectAllByGameid(gameid);
        // 进行分页
        Page<ViewCardUserHit> viewCardUserHitPage = PageHelper.startPage(curpage,limit);
        PageInfo<ViewCardUserHit> pageInfo = new PageInfo<>(viewCardUserHits);

        return new ApiResult<>(1,"成功",new PageBean<>(curpage,limit,pageInfo.getTotal(),pageInfo.getList()));
    }


}
