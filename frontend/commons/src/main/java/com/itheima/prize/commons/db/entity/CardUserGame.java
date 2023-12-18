package com.itheima.prize.commons.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName card_user_game
 */
@TableName(value ="card_user_game")
public class CardUserGame implements Serializable {
    /**
     * 
     */
    @TableId(value = "id")
    private String id;

    /**
     * 用户id
     */
    @TableField(value = "userid")
    private String userid;

    /**
     * 活动id
     */
    @TableField(value = "gameid")
    private String gameid;

    /**
     * 参与时间
     */
    @TableField(value = "createtime")
    private Date createtime;

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
     * 用户id
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 用户id
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 活动id
     */
    public String getGameid() {
        return gameid;
    }

    /**
     * 活动id
     */
    public void setGameid(String gameid) {
        this.gameid = gameid;
    }

    /**
     * 参与时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 参与时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}