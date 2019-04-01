/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.app.njry.procedure.validate.entity;

import java.util.List;
import main.helper.json.JsonObjectHelper;

/**
 * @description 用于描述一个验证字段的具体信息
 * @author 陈吕奖
 * @date 2018-08-22
 */
public class VaildateColumn {

    /**
     * @fields name 字段的中文名称
     */
    public String name;
    
    /**
     * @fields right 正确的数据
     */
    public String right;
    
    /**
     * @fields columnName 字段名称
     */
    public String columnName;

    /**
     * @fields require 字段是否必要
     */
    public boolean require;

    /** 
     * @fields columnType 字段的类型
     */ 
    public String columnType;
    
    /** 
     * @fields range 字段取值范围
     */ 
    public List<String> range;
    
    /** 
     * @fields repeat 是否能重复
     */ 
    public boolean repeat;

    /** 
     * @fields json 存储对象的json对象(当成map使用,更加的方便)
     */ 
    public JsonObjectHelper json; 

    public JsonObjectHelper getJson() {
        return json;
    }

    public void setJson(JsonObjectHelper json) {
        this.json = json;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public boolean isRequire() {
        return require;
    }

    public void setRequire(boolean require) {
        this.require = require;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public List<String> getRange() {
        return range;
    }

    public void setRange(List<String> range) {
        this.range = range;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }
}