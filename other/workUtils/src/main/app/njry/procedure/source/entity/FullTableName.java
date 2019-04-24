/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.app.njry.procedure.source.entity;

/** 
 * @description 用于描述一个完整的表名
 * 例如:jtb.tableName@tonjzw3
 * @author 陈吕奖
 * @date 2018-10-08 
 */
public class FullTableName {

    /** 
     * @fields userName 用户名
     */ 
    public String userName;
    
    /** 
     * @fields tableName 表名
     */ 
    private String tableName;
    
    /** 
     * @fields dataBaseName 数据库名
     */ 
    public String dataBaseName;

    /** 
     * @fields cntItem 过程项
     */ 
    public String cntItem;
    
    /** 
     * @fields type 表的类型,默认为0  日表   1为月表
     */ 
    public int type = 0;
    
    public String getTableName() 
    {
        return tableName;
    }

    public void setTableName(String tableName) 
    {
        if(tableName.endsWith("_"))
            this.tableName = tableName.substring(0, tableName.length() - 1);
        else
            this.tableName = tableName;
    }

    @Override
    public String toString() 
    {
        return userName+"."+tableName+"@to"+dataBaseName+"  过程项为"+cntItem;
    }
    
    //根据三个属性去重
    @Override
    public int hashCode() 
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataBaseName == null) ? 0 : dataBaseName.hashCode());
        result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) 
    {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        final FullTableName other = (FullTableName) obj;
        if(dataBaseName == null) {
            if(other.dataBaseName != null)
                return false;
        } else if(!dataBaseName.equals(other.dataBaseName))
            return false;
        if(tableName == null) {
            if(other.tableName != null)
                return false;
        } else if(!tableName.equals(other.tableName))
            return false;
        if(userName == null) {
            if(other.userName != null)
                return false;
        } else if(!userName.equals(other.userName))
            return false;
        return true;
    }
}
