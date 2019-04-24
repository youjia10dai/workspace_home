package com.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//配置文件的位置
//若当前配置文件名=当前测试类名-context.xml 就可以在当前目录中查找@ContextConfiguration()
@ContextConfiguration("classpath:config/spring.xml")
public class Main{

  @Autowired
  //自动装配
  private ApplicationContext cxf;
	
	/**
	 * 测试SpringBean获取
	 */
	@Test
	public void springBean(){
		/*
		 * 执行的顺序为
		 * 	1.postProcessBeforeInitialization
		 * 	2.init
		 * 	3.postProcessAfterInitialization
		 */
	}
}