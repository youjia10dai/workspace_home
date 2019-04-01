/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */
package util;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import entity.Friend;

/**
 * @description 加载配置工具类
 * @author 陈吕奖
 * @date 2018-12-07
 */
public class PropertyUtils {

    /**
     * @description 加载配置文件转换成map
     * @author 陈吕奖 2018-12-07
     * @param propertyName
     * @return
     */
    public static Map<String, String> loadProperties(String propertyName) {
        // 获得资源包
        ResourceBundle rb = ResourceBundle.getBundle(propertyName.trim());
        // 通过资源包拿到所有的key
        Enumeration<String> allKey = rb.getKeys();
        // 遍历key 得到 value
        Map<String, String> map = new HashMap<String, String>();
        while(allKey.hasMoreElements()) {
            String key = allKey.nextElement();
            String value = (String) rb.getString(key);
            map.put(key, value);
        }
        return map;
    }
}
