/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.helper.database.column;

/** 
 * @description 描述一个字段的属性(只封装了常用的属性信息)
 * @author 陈吕奖
 * @date 2018-06-29 
 */
public class Column {

    /** 
     * @fields name 字段名称
     */ 
    public String name;
    
    /** 
     * @fields type 字段类型
     */ 
    public String type;

    /**
     * @description 初始化字段名字和类型
     * @param name
     * @param type
     */
    public Column(String name, String type) {
        super();
        this.name = name;
        this.type = type;
    }
    
    
}
