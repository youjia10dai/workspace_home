/*
 * Copyright (c) 2019  南京瑞玥科技有限公司. All Rights Reserved.
 */
package main.study.shiro;

/** 
 * @description 这里用一句话描述这个类的作用
 * @author 陈吕奖
 * @date 2019-01-02 
 */
public interface ShiroHelperI {

    public abstract boolean login(String name, String password);

    public abstract void setAttribute(Object key, Object value);

    public abstract <T> T getAttribute(Object key);

    public abstract String getUserName();

    public abstract boolean hasRole(String roleName);

    public abstract boolean hasPermitted(String permittedName);

    public abstract void logout();

}
