package com.dxz.demo.configuration4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.dxz.demo.configuration.TestBean;
import com.dxz.demo.configuration3.TestBean2;

public class TestMain2 {
    public static void main(String[] args) {

        // @Configurationע���spring�������ط�ʽ����AnnotationConfigApplicationContext�滻ClassPathXmlApplicationContext
        ApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);

        // �������spring-context.xml�ļ���
        // ApplicationContext context = new
        // ClassPathXmlApplicationContext("spring-context.xml");

        // ��ȡbean
        TestBean2 tb2 = (TestBean2) context.getBean("testBean2");
        tb2.sayHello();
        
        TestBean tb = (TestBean) context.getBean("testBean");
        tb.sayHello();
    }
}