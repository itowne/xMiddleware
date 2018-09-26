package com.open.item.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.open.item.entity.enumObject.StatEnum;
import com.open.item.entity.enumObject.UserRoleEnum;

/**
 * 用户对象
 * 
 * @author towne
 * @date Sep 22, 2018
 */
@Entity
@Table(name = "t_user")
public class User implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6040326870747212982L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "login_name")
    private String loginName;

    @Column(name = "pwd")
    private String pwd;

    @Column(name = "mobile")
    private String mobile;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "user_role")
    private UserRoleEnum userRole;

    @Column(name = "user_stat")
    private StatEnum userStat;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "upd_time")
    private Date updTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public UserRoleEnum getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoleEnum userRole) {
        this.userRole = userRole;
    }

    public StatEnum getUserStat() {
        return userStat;
    }

    public void setUserStat(StatEnum userStat) {
        this.userStat = userStat;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

}
