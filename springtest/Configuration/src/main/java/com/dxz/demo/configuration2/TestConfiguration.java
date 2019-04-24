package com.dxz.demo.configuration2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.dxz.demo.configuration2")
public class TestConfiguration {
	public TestConfiguration() {
		System.out.println("TestConfiguration容器启动初始化。。。");
	}
	
    // @Bean注解注册bean,同时可以指定初始化和销毁方法
    // @Bean(name="testBean",initMethod="start",destroyMethod="cleanUp")
    @Bean(name="testBean2",initMethod="start",destroyMethod="cleanUp")
    //bean的名称默认是方法名
    public TestBean testBean() {
        return new TestBean();
    }
}