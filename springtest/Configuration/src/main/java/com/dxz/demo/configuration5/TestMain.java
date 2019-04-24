package com.dxz.demo.configuration5;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestMain {
    public static void main(String[] args) {

        // @Configuration注解的spring容器加载方式，用AnnotationConfigApplicationContext替换ClassPathXmlApplicationContexts
        ApplicationContext context = new AnnotationConfigApplicationContext(TestConfiguration.class);

         //bean
        TestBean tb = (TestBean) context.getBean("testBean");
        tb.sayHello();
        
        DataSource ds = (DataSource) context.getBean("dataSource");
        System.out.println(ds);
    }
}