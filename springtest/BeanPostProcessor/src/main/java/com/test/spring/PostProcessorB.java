package com.test.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
/**
 * bean后置处理器
 * @author zss
 *
 */
public class PostProcessorB implements BeanPostProcessor, Ordered{

    @Override
    public Object postProcessBeforeInitialization(Object bean,
            String beanName) throws BeansException {
        if ("narCodeService".equals(beanName)) {//过滤掉bean实例ID为narCodeService
            return bean;
        }
        System.out.println("B计划开始");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean,
            String beanName) throws BeansException {
        if ("narCodeService".equals(beanName)) {
            return bean;
        }
        System.out.println("B计划完毕!");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bean;
    }

    @Override
    public int getOrder() {
        return 0;
    }
    
}