package com.itheima.prize.commons.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @TableName sys_user
 */
@TableName(value ="sys_user")
public class SysUser implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id")
    private String id;

    /**
     * 登录账号
     */
    @TableField(value = "username")
    private String username;

    /**
     * 真实姓名
     */
    @TableField(value = "realname")
    private String realname;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * md5密码盐
     */
    @TableField(value = "salt")
    private String salt;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 生日
     */
    @TableField(value = "birthday")
    private Date birthday;

    /**
     * 性别(0-默认未知,1-男,2-女)
     */
    @TableField(value = "sex")
    private Integer sex;

    /**
     * 电子邮件
     */
    @TableField(value = "email")
    private String email;

    /**
     * 电话
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 登录会话的机构编码
     */
    @TableField(value = "org_code")
    private String orgCode;

    /**
     * 性别(1-正常,2-冻结)
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 删除状态(0-正常,1-已删除)
     */
    @TableField(value = "del_flag")
    private Integer delFlag;

    /**
     * 第三方登录的唯一标识
     */
    @TableField(value = "third_id")
    private String thirdId;

    /**
     * 第三方类型
     */
    @TableField(value = "third_type")
    private String thirdType;

    /**
     * 同步工作流引擎(1-同步,0-不同步)
     */
    @TableField(value = "activiti_sync")
    private Integer activitiSync;

    /**
     * 工号，唯一键
     */
    @TableField(value = "work_no")
    private String workNo;

    /**
     * 座机号
     */
    @TableField(value = "telephone")
    private String telephone;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 身份（1普通成员 2上级）
     */
    @TableField(value = "user_identity")
    private Integer userIdentity;

    /**
     * 负责部门
     */
    @TableField(value = "depart_ids")
    private String departIds;

    /**
     * 设备ID
     */
    @TableField(value = "client_id")
    private String clientId;

    /**
     * 上次登录选择租户ID
     */
    @TableField(value = "login_tenant_id")
    private Integer loginTenantId;

    /**
     * 流程入职离职状态
     */
    @TableField(value = "bpm_status")
    private String bpmStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    public String getId() {
        return id;
    }

    /**
     * 主键id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 登录账号
     */
    public String getUsername() {
        return username;
    }

    /**
     * 登录账号
     */
    public void setUsername(String username) {
        this.username = username;
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
     * 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * md5密码盐
     */
    public String getSalt() {
        return salt;
    }

    /**
     * md5密码盐
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 头像
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 头像
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 生日
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 生日
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 性别(0-默认未知,1-男,2-女)
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 性别(0-默认未知,1-男,2-女)
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 电子邮件
     */
    public String getEmail() {
        return email;
    }

    /**
     * 电子邮件
     */
    public void setEmail(String email) {
        this.email = email;
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
     * 登录会话的机构编码
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 登录会话的机构编码
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * 性别(1-正常,2-冻结)
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 性别(1-正常,2-冻结)
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 删除状态(0-正常,1-已删除)
     */
    public Integer getDelFlag() {
        return delFlag;
    }

    /**
     * 删除状态(0-正常,1-已删除)
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 第三方登录的唯一标识
     */
    public String getThirdId() {
        return thirdId;
    }

    /**
     * 第三方登录的唯一标识
     */
    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }

    /**
     * 第三方类型
     */
    public String getThirdType() {
        return thirdType;
    }

    /**
     * 第三方类型
     */
    public void setThirdType(String thirdType) {
        this.thirdType = thirdType;
    }

    /**
     * 同步工作流引擎(1-同步,0-不同步)
     */
    public Integer getActivitiSync() {
        return activitiSync;
    }

    /**
     * 同步工作流引擎(1-同步,0-不同步)
     */
    public void setActivitiSync(Integer activitiSync) {
        this.activitiSync = activitiSync;
    }

    /**
     * 工号，唯一键
     */
    public String getWorkNo() {
        return workNo;
    }

    /**
     * 工号，唯一键
     */
    public void setWorkNo(String workNo) {
        this.workNo = workNo;
    }

    /**
     * 座机号
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * 座机号
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * 创建人
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 创建人
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新人
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 更新人
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 身份（1普通成员 2上级）
     */
    public Integer getUserIdentity() {
        return userIdentity;
    }

    /**
     * 身份（1普通成员 2上级）
     */
    public void setUserIdentity(Integer userIdentity) {
        this.userIdentity = userIdentity;
    }

    /**
     * 负责部门
     */
    public String getDepartIds() {
        return departIds;
    }

    /**
     * 负责部门
     */
    public void setDepartIds(String departIds) {
        this.departIds = departIds;
    }

    /**
     * 设备ID
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * 设备ID
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * 上次登录选择租户ID
     */
    public Integer getLoginTenantId() {
        return loginTenantId;
    }

    /**
     * 上次登录选择租户ID
     */
    public void setLoginTenantId(Integer loginTenantId) {
        this.loginTenantId = loginTenantId;
    }

    /**
     * 流程入职离职状态
     */
    public String getBpmStatus() {
        return bpmStatus;
    }

    /**
     * 流程入职离职状态
     */
    public void setBpmStatus(String bpmStatus) {
        this.bpmStatus = bpmStatus;
    }
}