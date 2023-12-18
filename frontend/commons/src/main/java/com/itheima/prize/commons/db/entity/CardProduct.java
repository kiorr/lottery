package com.itheima.prize.commons.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @TableName card_product
 */
@TableName(value ="card_product")
public class CardProduct implements Serializable {
    /**
     * 
     */
    @TableId(value = "id")
    private String id;

    /**
     * 奖品名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 图片
     */
    @TableField(value = "pic")
    private String pic;

    /**
     * 简介
     */
    @TableField(value = "info")
    private String info;

    /**
     * 市场价
     */
    @TableField(value = "price")
    private BigDecimal price;

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
     * 奖品名称
     */
    public String getName() {
        return name;
    }

    /**
     * 奖品名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 图片
     */
    public String getPic() {
        return pic;
    }

    /**
     * 图片
     */
    public void setPic(String pic) {
        this.pic = pic;
    }

    /**
     * 简介
     */
    public String getInfo() {
        return info;
    }

    /**
     * 简介
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * 市场价
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 市场价
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}