package com.itheima.prize.commons.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @TableName view_card_user_hit
 */
public class ViewCardUserHit implements Serializable {
    /**
     *
     */
    private String id;

    /**
     * 活动主题
     */
    private String title;

    /**
     * 字典项文本
     */
    private String type;

    /**
     * 登录账号
     */
    private String uname;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 电话
     */
    private String phone;

    /**
     * 奖品名称
     */
    private String name;

    /**
     * 市场价
     */
    private BigDecimal price;

    /**
     * 活动
     */
    private String gameid;

    /**
     * 用户
     */
    private String userid;

    /**
     * 奖品
     */
    private String productid;

    /**
     * 中奖时间
     */
    private Date hittime;

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
     * 字典项文本
     */
    public String getType() {
        return type;
    }

    /**
     * 字典项文本
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 登录账号
     */
    public String getUname() {
        return uname;
    }

    /**
     * 登录账号
     */
    public void setUname(String uname) {
        this.uname = uname;
    }

    /**
     * 真实姓名
     */
    public String getRealname() {
        return realname;
    }

    /**
     * 真实姓名
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }

    /**
     * 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
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