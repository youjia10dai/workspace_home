package com;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.dxz.demo.configuration2.TestBean;
import com.dxz.demo.configuration2.TestConfiguration;
//�����ļ���λ��
public class Main{
	
	@Test
	public void springBean(){
		//��һ�ַ�ʽ
        // @Configurationע���spring�������ط�ʽ����AnnotationConfigApplicationContext�滻ClassPathXmlApplicationContext
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfiguration.class);
		//�ڶ��ַ�ʽ
		//ApplicationContext ctx = new AnnotationConfigApplicationContext();
		//ctx.register(TestConfiguration.class);
		
		
		context.registerShutdownHook();
        //��ȡbean
//       TestBean tb = (TestBean) context.getBean("testBean");
//       tb.sayHello();
       
       TestBean tb2 = (TestBean) context.getBean("testBean2");
       tb2.sayHello();
       
	}
	
	public void springGetBeanFromComponent() {
		
	} 
	
}