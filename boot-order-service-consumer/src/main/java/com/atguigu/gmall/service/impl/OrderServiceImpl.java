package com.atguigu.gmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.service.OrderService;
import com.atguigu.gmall.service.UserService;

@Service
public class OrderServiceImpl implements OrderService {

	//@Autowired
	@Reference
	UserService userService;
	
	@Override
	public List<UserAddress> initOrder(String userId) {
		List<UserAddress> userAddressList = userService.getUserAddressList(userId);
		for (UserAddress userAddress : userAddressList) {
			System.out.println(userAddress);
		}
		return userAddressList;
	}

}
