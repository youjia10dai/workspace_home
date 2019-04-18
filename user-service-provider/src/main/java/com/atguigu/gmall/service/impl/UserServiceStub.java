package com.atguigu.gmall.service.impl;

import java.util.List;

import org.springframework.util.StringUtils;

import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.service.UserService;


public class UserServiceStub implements UserService{

	private final UserService userService;
	
	/**
	 * 通过这个构造器将传入远程的实现方法
	 */
	public UserServiceStub(UserService userService) {
		super();
		System.out.println("创建对象");
		System.out.println(this.toString());
		this.userService = userService;
		System.out.println(this.userService.toString());
	}

	@Override
	public List<UserAddress> getUserAddressList(String userId) {
		System.out.println("调用本地存根方法");
		if(!StringUtils.isEmpty(userId)){
			return userService.getUserAddressList(userId);
		}
		return null;
	}

}