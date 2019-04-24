package com.atguigu.gmall;

import java.io.IOException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.atguigu.gmall.service.OrderService;

public class MailApplication {

	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("consumer.xml");
		ioc.start();
		OrderService bean1 = ioc.getBean(OrderService.class);
		bean1.initOrder("1");
		System.out.println("调用完成");
		System.in.read();
	}
}
