/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.spring.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/** 
 * @description Spring����µ������ļ�����
 * @author ������
 * @date 2018-11-27 
 */
//��Ҫ��ʹ��ע��ע��,�����ļ����Ѿ�ע��
public class PropertyConfigure extends PropertyPlaceholderConfigurer{
    
    private Map<String, String> ctxPropertiesMap = new HashMap<String, String>();
    public PropertyConfigure(){
        System.out.println(this);
    }
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException 
    {
        //spring���������ļ�(����ԭ�����о���)
        super.processProperties(beanFactoryToProcess, props);
        //�������ص������ļ��ļ�ֵ�������ݼ�ֵ��ȡ��valueֵ��Ȼ��һͬ�����map����
        for (Object key : props.keySet()){
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }
    }

    /** 
     * @description �˷�������map����ļ���ȡ��value��ֵ
     * @author ������ 2018-11-29
     * @param name
     * @return
     */ 
    public String getValue(String name)
    {
        return ctxPropertiesMap.get(name);
    }
    
    /** 
     * @description �������е�value
     * @author ������ 2018-11-29
     * @return
     */ 
    public String[] getValues(){
        return new ArrayList<String>(ctxPropertiesMap.values()).toArray(new String[]{});
    }
}
