package com;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.dxz.demo.configuration2.TestBean;
import com.dxz.demo.configuration2.TestConfiguration;
//配置文件的位置
public class Main{
	
	@Test
	public void springBean(){
		//第一种方式
        // @Configuration注解的spring容器加载方式，用AnnotationConfigApplicationContext替换ClassPathXmlApplicationContext
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfiguration.class);
		//第二种方式
		//ApplicationContext ctx = new AnnotationConfigApplicationContext();
		//ctx.register(TestConfiguration.class);
		
		
		context.registerShutdownHook();
        //获取bean
//       TestBean tb = (TestBean) context.getBean("testBean");
//       tb.sayHello();
       
       TestBean tb2 = (TestBean) context.getBean("testBean2");
       tb2.sayHello();
       
	}
	
	public void springGetBeanFromComponent() {
		
	} 
	
}