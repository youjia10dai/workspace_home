package com.atguigu.gmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

/**
 * SpringBoot和Dubbo整合的三种方式
 * 1.导入dubbo-starter,在application.propertes配置属性,
 *   使用@Service[暴露服务], 使用@Reference[引用服务]
 *   并且必须开启 @EnableDubbo
 *   @EnableDubbo 主要是开启包扫描,如果在application.propertes中配置包扫描的路径,就不需要开启了
 * 2.保留dubbo 的xml配置文件
 * 	  导入dubbo-starter,使用@ImportResource导入dubbo配置文件即可
 * 3.使用注解API的方式:
 * 	 将每一个组件手动创建到容器中,让dubbo扫描其他的组件
 */	 

//@EnableDubbo//开启基于注解的dubbo功能   第一种方式
//@ImportResource(locations="classpath:provider.xml")  第二种方式
@EnableDubbo()//第三种方式
@SpringBootApplication
@EnableHystrix
public class BootUserServiceProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootUserServiceProviderApplication.class, args);
	}

}
