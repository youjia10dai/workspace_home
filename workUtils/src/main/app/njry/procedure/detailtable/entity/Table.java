/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.app.njry.procedure.detailtable.entity;

import main.helper.BaseHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** 
 * @description 明细表
 * @author 陈吕奖
 * @date 2018-12-03 
 */
@Component("tableObject")
public abstract class Table extends BaseHelper{
    
    @Autowired
    public Procedure procedure;
    
    /**
     * @fields 表名
     */ 
    public String tableName;
    
    /** 
     * @fields 临时表名
     */ 
    public String tmpporaryTableName;
    
    /** 
     * @fields getColumnSql 获取所有字段的sql
     */ 
    public String getColumnSql = "select column_name from user_tab_cols where table_name = ? order by segment_column_id";
    
    /**
     * @fields existTableNameSql 用于判断表名是否存在
     */ 
    public String existTableNameSql = "select count(1) from all_objects where object_name = ?";
   
    public void init(String tableName, String tmpporaryTableName){
        this.tableName = tableName;
        this.tmpporaryTableName = tmpporaryTableName;        
    }
    
    /** 
     * @description 判断表名是否存在
     * @author 陈吕奖 2018-11-29
     * @param tableName
     * @return
     */
    public boolean isExistTable(String tableName){
        int count = db.queryForInt(existTableNameSql, tableName.toUpperCase());
        if(count != 1){
            return false;
        }
        return true;
    }
    
    /**
     * @description 创建表
     * @author 陈吕奖 2018-12-03
     */ 
    public abstract void createTable();
    
    /** 
     * @description 生成删除语句
     * @author 陈吕奖 2018-12-03
     */ 
    public abstract void generateDeleteStatement();
    
    /** 
     * @description 生成插入语句
     * @author 陈吕奖 2018-12-03
     */ 
    public abstract void generateInsertStatement();
    
    /** 
     * @description 生成建表语句
     * @author 陈吕奖 2018-12-03
     * @return
     */ 
    public abstract String getCreateTableSql();
    
}
