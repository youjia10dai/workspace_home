/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
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
 * @description Spring框架下的配置文件加载
 * @author 陈吕奖
 * @date 2018-11-27 
 */
//不要在使用注解注入,配置文件中已经注入
public class PropertyConfigure extends PropertyPlaceholderConfigurer{
    
    private Map<String, String> ctxPropertiesMap = new HashMap<String, String>();
    public PropertyConfigure(){
        System.out.println(this);
    }
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException 
    {
        //spring载入配置文件(具体原理还在研究中)
        super.processProperties(beanFactoryToProcess, props);
        //便利加载的配置文件的键值，并根据键值获取到value值，然后一同保存进map对象
        for (Object key : props.keySet()){
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }
    }

    /** 
     * @description 此方法根据map对象的键获取其value的值
     * @author 陈吕奖 2018-11-29
     * @param name
     * @return
     */ 
    public String getValue(String name)
    {
        return ctxPropertiesMap.get(name);
    }
    
    /** 
     * @description 返回所有的value
     * @author 陈吕奖 2018-11-29
     * @return
     */ 
    public String[] getValues(){
        return new ArrayList<String>(ctxPropertiesMap.values()).toArray(new String[]{});
    }
}
