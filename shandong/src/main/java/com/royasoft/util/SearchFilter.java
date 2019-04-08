package com.royasoft.util;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.StringUtils;

import com.google.common.collect.Maps;

/**
 * 查询过滤器 <br>
 * 
 * @author jxue
 */
public class SearchFilter {
    /**
     * 
     * 条件枚举 <br>
     * EQ 等于 , LIKE LIKE , GT 大于 , LT 小于 , GTE 大于等于 , LTE 小于等于 IN, OR
     * 
     * @author jxue
     */
    public enum Operator {
        EQ, NE, LIKE, LLIKE, RLIKE, GT, LT, GTE, LTE, IN, OREQ, ORNE, ORLIKE, ORGT, ORLT, ORGTE, ORLTE, ORIN
    }

    /** 字段名 */
    public String fieldName;

    /** 值 */
    public Object value;

    /** 连接符 */
    public Operator operator;

    public SearchFilter(String fieldName, Operator operator, Object value) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
    }

    /**
     * searchParams中key的格式为OPERATOR_FIELDNAME
     */
    public static Map<String, SearchFilter> parse(Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = Maps.newHashMap();

        for (Entry<String, Object> entry : searchParams.entrySet()) {
            // 过滤掉空值
            String key = entry.getKey();
            Object value = entry.getValue();
            if (StringUtils.isEmpty(value)) {
                continue;
            }

            // 拆分operator与filedAttribute
            String[] names = StringUtils.split(key, "_");
            if (names.length != 2) {
                throw new IllegalArgumentException(key + " is not a valid search filter name");
            }
            String filedName = names[1];
            Operator operator = Operator.valueOf(names[0]);
            // 创建searchFilter
            SearchFilter filter = new SearchFilter(filedName, operator, value);
            filters.put(key, filter);
        }

        return filters;
    }

}