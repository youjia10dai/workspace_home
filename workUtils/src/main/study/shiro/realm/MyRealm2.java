/*
 * Copyright (c) 2019  南京瑞玥科技有限公司. All Rights Reserved.
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
 * @description 第一个域
 * @author 陈吕奖
 * @date 2019-01-02 
 */
public class MyRealm2 implements Realm{

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal(); //得到用户名
        String password = new String((char[])token.getCredentials()); //得到密码
        if(!"clj".equals(username)) {
            throw new UnknownAccountException(); //如果用户名错误
        }
        if(!"123".equals(password)) {
            throw new IncorrectCredentialsException(); //如果密码错误
        }
        //如果身份认证验证成功，返回一个AuthenticationInfo实现；
        return new SimpleAuthenticationInfo(username, password, getName());
    }

    @Override
    //返回一个唯一的Realm名字
    public String getName() {
        return "myrealm1";
    }

    @Override
    //判断此Realm是否支持此Token
    public boolean supports(AuthenticationToken token) {
        //仅支持UsernamePasswordToken 类型的Token
        return token instanceof UsernamePasswordToken;
    }

}