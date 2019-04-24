package com.atguigu.gmall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.service.OrderService;
import com.atguigu.gmall.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class OrderServiceImpl implements OrderService {

	@Reference()
	UserService userService;
	
	@Override
	@HystrixCommand(fallbackMethod = "hello")
	public List<UserAddress> initOrder(String userId) {
		List<UserAddress> userAddressList = userService.getUserAddressList(userId);
		for (UserAddress userAddress : userAddressList) {
			System.out.println(userAddress);
		}
		return userAddressList;
	}

	public List<UserAddress> hello(String userId) {
		List<UserAddress> userAddressList = new ArrayList<UserAddress>();
		userAddressList.add(new UserAddress(2, "测试地址", "1", "222", "12132132",
				"Y"));
		return userAddressList;
	}
	
}
