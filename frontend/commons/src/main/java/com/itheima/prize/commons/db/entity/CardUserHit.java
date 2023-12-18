package com.itheima.prize.commons.db.entity;

import java.io.Serializable;
import java.util.Date;

public class CardUserHit implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column card_user_hit.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column card_user_hit.gameid
     *
     * @mbg.generated
     */
    private String gameid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column card_user_hit.userid
     *
     * @mbg.generated
     */
    private String userid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column card_user_hit.productid
     *
     * @mbg.generated
     */
    private String productid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column card_user_hit.hittime
     *
     * @mbg.generated
     */
    private Date hittime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table card_user_hit
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    public CardUserHit(String gameid, String userid, String productid, Date hittime) {
        this.gameid = gameid;
        this.userid = userid;
        this.productid = productid;
        this.hittime = hittime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameid() {
        return gameid;
    }

    public void setGameid(String gameid) {
        this.gameid = gameid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column card_user_hit.id
     *
     * @return the value of card_user_hit.id
     *
     * @mbg.generated
     */

    public Date getHittime() {
        return hittime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column card_user_hit.hittime
     *
     * @param hittime the value for card_user_hit.hittime
     *
     * @mbg.generated
     */
    public void setHittime(Date hittime) {
        this.hittime = hittime;
    }
}
