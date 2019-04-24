/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.app.njry.procedure.detailtable.entity;

import main.helper.BaseHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** 
 * @description ��ϸ��
 * @author ������
 * @date 2018-12-03 
 */
@Component("tableObject")
public abstract class Table extends BaseHelper{
    
    @Autowired
    public Procedure procedure;
    
    /**
     * @fields ����
     */ 
    public String tableName;
    
    /** 
     * @fields ��ʱ����
     */ 
    public String tmpporaryTableName;
    
    /** 
     * @fields getColumnSql ��ȡ�����ֶε�sql
     */ 
    public String getColumnSql = "select column_name from user_tab_cols where table_name = ? order by segment_column_id";
    
    /**
     * @fields existTableNameSql �����жϱ����Ƿ����
     */ 
    public String existTableNameSql = "select count(1) from all_objects where object_name = ?";
   
    public void init(String tableName, String tmpporaryTableName){
        this.tableName = tableName;
        this.tmpporaryTableName = tmpporaryTableName;        
    }
    
    /** 
     * @description �жϱ����Ƿ����
     * @author ������ 2018-11-29
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
     * @description ������
     * @author ������ 2018-12-03
     */ 
    public abstract void createTable();
    
    /** 
     * @description ����ɾ�����
     * @author ������ 2018-12-03
     */ 
    public abstract void generateDeleteStatement();
    
    /** 
     * @description ���ɲ������
     * @author ������ 2018-12-03
     */ 
    public abstract void generateInsertStatement();
    
    /** 
     * @description ���ɽ������
     * @author ������ 2018-12-03
     * @return
     */ 
    public abstract String getCreateTableSql();
    
}
