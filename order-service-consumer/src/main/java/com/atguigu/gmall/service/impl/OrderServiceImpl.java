package com.atguigu.gmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.service.OrderService;
import com.atguigu.gmall.service.UserService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	UserService userService;
	
	@Override
	public void initOrder(String userId) {
		//1.��ѯ�û����ջ���ַ
		List<UserAddress> userAddressList = userService.getUserAddressList(userId);
		for (UserAddress userAddress : userAddressList) {
			System.out.println(userAddress);
		}
	}

}
