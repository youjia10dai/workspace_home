package com.dxz.demo.configuration3;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestMain2 {
    public static void main(String[] args) {

        // @Configurationע���spring�������ط�ʽ����AnnotationConfigApplicationContext�滻ClassPathXmlApplicationContext
        ApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);

        // �������spring-context.xml�ļ���
        // ApplicationContext context = new
        // ClassPathXmlApplicationContext("spring-context.xml");

        // ��ȡbean
        TestBean2 tb = (TestBean2) context.getBean("testBean2");
        tb.sayHello();
    }
}