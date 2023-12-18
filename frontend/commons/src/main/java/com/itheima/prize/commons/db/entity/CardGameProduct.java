package com.itheima.prize.commons.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 
 * @TableName card_game_product
 */
@TableName(value ="card_game_product")
public class CardGameProduct implements Serializable {
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
     * 奖品
     */
    @TableField(value = "productid")
    private String productid;

    /**
     * 数量
     */
    @TableField(value = "amount")
    private Integer amount;

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
     * 数量
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * 数量
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}