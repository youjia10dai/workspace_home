/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
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
 * @description �������ù�����
 * @author ������
 * @date 2018-12-07
 */
public class PropertyUtils {

    /**
     * @description ���������ļ�ת����map
     * @author ������ 2018-12-07
     * @param propertyName
     * @return
     */
    public static Map<String, String> loadProperties(String propertyName) {
        // �����Դ��
        ResourceBundle rb = ResourceBundle.getBundle(propertyName.trim());
        // ͨ����Դ���õ����е�key
        Enumeration<String> allKey = rb.getKeys();
        // ����key �õ� value
        Map<String, String> map = new HashMap<String, String>();
        while(allKey.hasMoreElements()) {
            String key = allKey.nextElement();
            String value = (String) rb.getString(key);
            map.put(key, value);
        }
        return map;
    }
}
