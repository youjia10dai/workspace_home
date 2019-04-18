package com.atguigu.gmall.service.impl;

import java.util.List;

import org.springframework.util.StringUtils;

import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.service.UserService;


public class UserServiceStub implements UserService{

	private final UserService userService;
	
	/**
	 * ͨ�����������������Զ�̵�ʵ�ַ���
	 */
	public UserServiceStub(UserService userService) {
		super();
		System.out.println("��������");
		System.out.println(this.toString());
		this.userService = userService;
		System.out.println(this.userService.toString());
	}

	@Override
	public List<UserAddress> getUserAddressList(String userId) {
		System.out.println("���ñ��ش������");
		if(!StringUtils.isEmpty(userId)){
			return userService.getUserAddressList(userId);
		}
		return null;
	}

}
