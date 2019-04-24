/*
 * Copyright (c) 2019  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */
package main.study.shiro;

/** 
 * @description ������һ�仰��������������
 * @author ������
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
