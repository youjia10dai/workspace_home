package com.dxz.demo.configuration2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestMain2 {
    public static void main(String[] args) {

        // @Configuration注解的spring容器加载方式，用AnnotationConfigApplicationContext替换ClassPathXmlApplicationContext
        ApplicationContext context = new AnnotationConfigApplicationContext(TestConfiguration.class);

        // 如果加载spring-context.xml文件：
        // ApplicationContext context = new
        // ClassPathXmlApplicationContext("spring-context.xml");

        // 获取bean
        TestBean tb = (TestBean) context.getBean("testBean2");
        tb.sayHello();
    }
}