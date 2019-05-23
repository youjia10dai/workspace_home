package com.royasoft.account.impl.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author Clj 隐私 用户
 * @Date 2019-05-16
 */
@Entity
@Table(name = "vwt_hide_account")
public class Account implements Serializable {

    private static final long serialVersionUID = 2844212697496077633L;

    /**
     * 逻辑ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 用户姓名
     */
    @Column(name = "username")
    private String userName;

    /**
     * 用户号码
     */
    @Column(name = "telephone")
    private String telephone;

    /**
     * 部门
     */
    @Column(name = "department")
    private String department;

    /**
     * 创建日期
     */
    @Column(name = "createtime")
    private Date createTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}