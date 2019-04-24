package com.dxz.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {
	public TestConfiguration() {
		System.out.println("TestConfiguration����������ʼ��������");
	}
	
    // @Beanע��ע��bean,ͬʱ����ָ����ʼ�������ٷ���
    // @Bean(name="testBean",initMethod="start",destroyMethod="cleanUp")
    @Bean(name="testBean",initMethod="start",destroyMethod="cleanUp")
    //bean������Ĭ���Ƿ�����
    public TestBean testBean() {
        return new TestBean();
    }
}