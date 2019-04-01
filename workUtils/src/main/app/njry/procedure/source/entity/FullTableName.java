/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.app.njry.procedure.source.entity;

/** 
 * @description ��������һ�������ı���
 * ����:jtb.tableName@tonjzw3
 * @author ������
 * @date 2018-10-08 
 */
public class FullTableName {

    /** 
     * @fields userName �û���
     */ 
    public String userName;
    
    /** 
     * @fields tableName ����
     */ 
    private String tableName;
    
    /** 
     * @fields dataBaseName ���ݿ���
     */ 
    public String dataBaseName;

    /** 
     * @fields cntItem ������
     */ 
    public String cntItem;
    
    /** 
     * @fields type �������,Ĭ��Ϊ0  �ձ�   1Ϊ�±�
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
        return userName+"."+tableName+"@to"+dataBaseName+"  ������Ϊ"+cntItem;
    }
    
    //������������ȥ��
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
