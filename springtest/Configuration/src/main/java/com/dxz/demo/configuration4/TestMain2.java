package com.dxz.demo.configuration4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.dxz.demo.configuration.TestBean;
import com.dxz.demo.configuration3.TestBean2;

public class TestMain2 {
    public static void main(String[] args) {

        // @Configuration注解的spring容器加载方式，用AnnotationConfigApplicationContext替换ClassPathXmlApplicationContext
        ApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);

        // 如果加载spring-context.xml文件：
        // ApplicationContext context = new
        // ClassPathXmlApplicationContext("spring-context.xml");

        // 获取bean
        TestBean2 tb2 = (TestBean2) context.getBean("testBean2");
        tb2.sayHello();
        
        TestBean tb = (TestBean) context.getBean("testBean");
        tb.sayHello();
    }
}