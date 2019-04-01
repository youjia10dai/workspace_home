/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.app.njry.procedure.validate.entity;

import java.util.List;
import main.helper.json.JsonObjectHelper;

/**
 * @description ��������һ����֤�ֶεľ�����Ϣ
 * @author ������
 * @date 2018-08-22
 */
public class VaildateColumn {

    /**
     * @fields name �ֶε���������
     */
    public String name;
    
    /**
     * @fields right ��ȷ������
     */
    public String right;
    
    /**
     * @fields columnName �ֶ�����
     */
    public String columnName;

    /**
     * @fields require �ֶ��Ƿ��Ҫ
     */
    public boolean require;

    /** 
     * @fields columnType �ֶε�����
     */ 
    public String columnType;
    
    /** 
     * @fields range �ֶ�ȡֵ��Χ
     */ 
    public List<String> range;
    
    /** 
     * @fields repeat �Ƿ����ظ�
     */ 
    public boolean repeat;

    /** 
     * @fields json �洢�����json����(����mapʹ��,���ӵķ���)
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