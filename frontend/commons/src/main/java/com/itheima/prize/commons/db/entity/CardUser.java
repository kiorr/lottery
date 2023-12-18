package com.itheima.prize.commons.db.entity;

import java.io.Serializable;
import java.util.Date;

public class CardUser implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column card_user.id
     *
     * @mbg.generated
     */
    public CardUser(SysUser sysUser){
        this.id = sysUser.getId();
        this.uname=sysUser.getUsername();
        this.pic=sysUser.getAvatar();
        this.passwd=sysUser.getPassword();
        this.realname=sysUser.getRealname();
        //DOTO 座机号改成身份证号
        //this.idcard= sysUser.getOrgCode();
        this.phone=sysUser.getPhone();
        //status改成会员等级
        this.level= sysUser.getStatus();
        this.createtime=sysUser.getCreateTime();
        this.updatetime=sysUser.getUpdateTime();
    }
    public CardUser(){

    }
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column card_user.uname
     *
     * @mbg.generated
     */
    private String uname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column card_user.pic
     *
     * @mbg.generated
     */
    private String pic;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column card_user.passwd
     *
     * @mbg.generated
     */
    private String passwd;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column card_user.realname
     *
     * @mbg.generated
     */
    private String realname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column card_user.idcard
     *
     * @mbg.generated
     */
    private String idcard;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column card_user.phone
     *
     * @mbg.generated
     */
    private String phone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column card_user.level
     *
     * @mbg.generated
     */
    private Integer level;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column card_user.createtime
     *
     * @mbg.generated
     */
    private Date createtime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column card_user.updatetime
     *
     * @mbg.generated
     */
    private Date updatetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table card_user
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column card_user.id
     *
     * @return the value of card_user.id
     *
     * @mbg.generated
     */


    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column card_user.id
     *
     * @param id the value for card_user.id
     *
     * @mbg.generated
     */


    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column card_user.uname
     *
     * @return the value of card_user.uname
     *
     * @mbg.generated
     */
    public String getUname() {
        return uname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column card_user.uname
     *
     * @param uname the value for card_user.uname
     *
     * @mbg.generated
     */
    public void setUname(String uname) {
        this.uname = uname == null ? null : uname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column card_user.pic
     *
     * @return the value of card_user.pic
     *
     * @mbg.generated
     */
    public String getPic() {
        return pic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column card_user.pic
     *
     * @param pic the value for card_user.pic
     *
     * @mbg.generated
     */
    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column card_user.passwd
     *
     * @return the value of card_user.passwd
     *
     * @mbg.generated
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column card_user.passwd
     *
     * @param passwd the value for card_user.passwd
     *
     * @mbg.generated
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column card_user.realname
     *
     * @return the value of card_user.realname
     *
     * @mbg.generated
     */
    public String getRealname() {
        return realname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column card_user.realname
     *
     * @param realname the value for card_user.realname
     *
     * @mbg.generated
     */
    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column card_user.idcard
     *
     * @return the value of card_user.idcard
     *
     * @mbg.generated
     */
    public String getIdcard() {
        return idcard;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column card_user.idcard
     *
     * @param idcard the value for card_user.idcard
     *
     * @mbg.generated
     */
    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column card_user.phone
     *
     * @return the value of card_user.phone
     *
     * @mbg.generated
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column card_user.phone
     *
     * @param phone the value for card_user.phone
     *
     * @mbg.generated
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column card_user.level
     *
     * @return the value of card_user.level
     *
     * @mbg.generated
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column card_user.level
     *
     * @param level the value for card_user.level
     *
     * @mbg.generated
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column card_user.createtime
     *
     * @return the value of card_user.createtime
     *
     * @mbg.generated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column card_user.createtime
     *
     * @param createtime the value for card_user.createtime
     *
     * @mbg.generated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column card_user.updatetime
     *
     * @return the value of card_user.updatetime
     *
     * @mbg.generated
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column card_user.updatetime
     *
     * @param updatetime the value for card_user.updatetime
     *
     * @mbg.generated
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}
