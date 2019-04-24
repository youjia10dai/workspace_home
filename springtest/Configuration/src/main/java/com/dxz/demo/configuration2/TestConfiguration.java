package com.dxz.demo.configuration2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.dxz.demo.configuration2")
public class TestConfiguration {
	public TestConfiguration() {
		System.out.println("TestConfiguration����������ʼ��������");
	}
	
    // @Beanע��ע��bean,ͬʱ����ָ����ʼ�������ٷ���
    // @Bean(name="testBean",initMethod="start",destroyMethod="cleanUp")
    @Bean(name="testBean2",initMethod="start",destroyMethod="cleanUp")
    //bean������Ĭ���Ƿ�����
    public TestBean testBean() {
        return new TestBean();
    }
}