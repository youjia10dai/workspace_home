package com.atguigu.gmall;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atguigu.gmall.service.OrderService;

public class MailApplication {

	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("���ش��.xml");
		ioc.start();
		OrderService bean = ioc.getBean(OrderService.class);
		bean.initOrder("1");
		System.out.println("�������");
		System.in.read();
	}
}
