package com.itheima.prize.commons.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName card_game
 */
@TableName(value ="card_game")
public class CardGame implements Serializable {
    /**
     * 
     */
    @TableId(value = "id")
    private String id;

    /**
     * 活动主题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 活动宣传图
     */
    @TableField(value = "pic")
    private String pic;

    /**
     * 活动简介
     */
    @TableField(value = "info")
    private String info;

    /**
     * 开始时间
     */
    @TableField(value = "starttime")
    private Date starttime;

    /**
     * 结束时间
     */
    @TableField(value = "endtime")
    private Date endtime;

    /**
     * 类型
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 状态
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 最大抽奖次数
     */
    @TableField(value = "maxenter")
    private Integer maxenter;

    /**
     * 最大中奖概率
     */
    @TableField(value = "maxratio")
    private Integer maxratio;

    /**
     * 最大中奖个数
     */
    @TableField(value = "maxgoal")
    private Integer maxgoal;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public String getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 活动主题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 活动主题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 活动宣传图
     */
    public String getPic() {
        return pic;
    }

    /**
     * 活动宣传图
     */
    public void setPic(String pic) {
        this.pic = pic;
    }

    /**
     * 活动简介
     */
    public String getInfo() {
        return info;
    }

    /**
     * 活动简介
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * 开始时间
     */
    public Date getStarttime() {
        return starttime;
    }

    /**
     * 开始时间
     */
    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    /**
     * 结束时间
     */
    public Date getEndtime() {
        return endtime;
    }

    /**
     * 结束时间
     */
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    /**
     * 类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 最大抽奖次数
     */
    public Integer getMaxenter() {
        return maxenter;
    }

    /**
     * 最大抽奖次数
     */
    public void setMaxenter(Integer maxenter) {
        this.maxenter = maxenter;
    }

    /**
     * 最大中奖概率
     */
    public Integer getMaxratio() {
        return maxratio;
    }

    /**
     * 最大中奖概率
     */
    public void setMaxratio(Integer maxratio) {
        this.maxratio = maxratio;
    }

    /**
     * 最大中奖个数
     */
    public Integer getMaxgoal() {
        return maxgoal;
    }

    /**
     * 最大中奖个数
     */
    public void setMaxgoal(Integer maxgoal) {
        this.maxgoal = maxgoal;
    }
}