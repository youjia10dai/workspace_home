/*
 * Copyright (c) 2019  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.study.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;

/** 
 * @description ��һ����
 * @author ������
 * @date 2019-01-02 
 */
public class MyRealm2 implements Realm{

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal(); //�õ��û���
        String password = new String((char[])token.getCredentials()); //�õ�����
        if(!"clj".equals(username)) {
            throw new UnknownAccountException(); //����û�������
        }
        if(!"123".equals(password)) {
            throw new IncorrectCredentialsException(); //����������
        }
        //��������֤��֤�ɹ�������һ��AuthenticationInfoʵ�֣�
        return new SimpleAuthenticationInfo(username, password, getName());
    }

    @Override
    //����һ��Ψһ��Realm����
    public String getName() {
        return "myrealm1";
    }

    @Override
    //�жϴ�Realm�Ƿ�֧�ִ�Token
    public boolean supports(AuthenticationToken token) {
        //��֧��UsernamePasswordToken ���͵�Token
        return token instanceof UsernamePasswordToken;
    }

}