package com.royasoft.util;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期格式化类 用于jpa动态查询 格式化日期
 * 
 * @author qinp
 * 
 */
public class DateFormat {

    private static final Logger logger = LoggerFactory.getLogger(DateFormat.class);

    private final static String startPrefix = "start_time_";
    private final static String endPrefix = "end_time_";

    public static Map<String, Object> formatDate(Map<String, Object> conditions) {
        logger.debug("转化日期格式，传入的conditions为{}", conditions);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Iterator<String> keys = conditions.keySet().iterator();
            Map<String, Object> tempMap = new HashMap<String, Object>();
            String startKeyName = "";
            String endKeyName = "";
            while (keys.hasNext()) {
                String key = keys.next();
                String value = "";
                String keyName = "";
                if (key.startsWith(startPrefix)) {// 对开始时间格式处理
                    startKeyName = key;
                    value = (String) conditions.get(key);
                    keyName = key.substring(startPrefix.length());
                    if (!"".equals(value))
                        tempMap.put("GT_" + keyName, sdf.parse(value));
                }
                if (key.startsWith(endPrefix)) {// 对结束时间格式处理
                    endKeyName = key;
                    value = (String) conditions.get(key);
                    keyName = key.substring(endPrefix.length());
                    if (!"".equals(value))
                        tempMap.put("LT_" + keyName, sdf.parse(value));
                }
            }
            conditions.remove(startKeyName);//删除转化之前的key
            conditions.remove(endKeyName);
            conditions.putAll(tempMap);
        } catch (Exception e) {
            logger.error("转化日期格式异常", e);
        }

        return conditions;
    }
}