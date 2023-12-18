package com.itheima.prize.commons.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName card_user_hit
 */
@TableName(value ="card_user_hit")
public class CardUserHit implements Serializable {
    /**
     * 
     */
    @TableId(value = "id")
    private String id;

    /**
     * 活动
     */
    @TableField(value = "gameid")
    private String gameid;

    /**
     * 用户
     */
    @TableField(value = "userid")
    private String userid;

    /**
     * 奖品
     */
    @TableField(value = "productid")
    private String productid;

    /**
     * 中奖时间
     */
    @TableField(value = "hittime")
    private Date hittime;

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
     * 活动
     */
    public String getGameid() {
        return gameid;
    }

    /**
     * 活动
     */
    public void setGameid(String gameid) {
        this.gameid = gameid;
    }

    /**
     * 用户
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 用户
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 奖品
     */
    public String getProductid() {
        return productid;
    }

    /**
     * 奖品
     */
    public void setProductid(String productid) {
        this.productid = productid;
    }

    /**
     * 中奖时间
     */
    public Date getHittime() {
        return hittime;
    }

    /**
     * 中奖时间
     */
    public void setHittime(Date hittime) {
        this.hittime = hittime;
    }
}