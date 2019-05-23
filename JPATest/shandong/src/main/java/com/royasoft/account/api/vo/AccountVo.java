package com.royasoft.account.api.vo;

import java.io.Serializable;

import javax.persistence.Id;

/**
 * @author Clj 账户Vo对象, 维护账户信息
 *
 */
public class AccountVo implements Serializable {

    @Override
    public String toString() {
        return "AccountVo [userId=" + userId + ", userName=" + userName + ", telephone=" + telephone + ", department=" + department + ", createTime="
                + createTime + "]";
    }

    private static final long serialVersionUID = -6951118699972779305L;

    /**
     * 用户id
     */
    @Id
    private String userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户号码
     */
    private String telephone;

    /**
     * 部门
     */
    private String department;

    /**
     * 创建日期
     */
    private String createTime;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
