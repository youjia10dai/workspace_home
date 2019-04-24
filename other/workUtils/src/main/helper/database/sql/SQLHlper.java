/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.helper.database.sql;

import java.util.List;
import main.helper.database.column.Column;
import main.helper.database.column.ColumnHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/** 
 * @description �Զ�����SQL���
 * @author ������
 * @date 2018-06-05 
 */
@Component("sqlhlper")
public class SQLHlper {

    //"\r\n" ��ӡ�Ļ���
    /** 
     * @fields fieldHelper ���ݿ��ֶ���Ϣ������
     */ 
    private ColumnHelper fieldHelper;

    /** 
     * @description 1.���ɽ������ 2.�����ֶ����ע����� 3.���ɱ��insert���ģ��
     * @author ������ 2018-06-05
     * @param tableName  ����
     * @param paramStr   str1,str2,str3
     *          str1 ��ʾ�ֶ���
     *          str2 ��ʾ�ֶ�����  ��Ĭ�ϵ��ֶ�����
     *          str3 ��ʾ�ֶ�ע��
     * @param batchSql  �洢sql���ļ���
     */ 
    public static void createTable(String tableName, String[] paramStr, BatchSql batchSql){
        //
        StringBuilder createSql = new StringBuilder();
        StringBuilder insertSql = new StringBuilder();
        
        //�Ȼ�ȡ����ĳ���
        int length = paramStr[0].split(",").length;
        for(String string : paramStr) {
            String[] split = string.split(",");
            
            createSql.append(split[1]);
            insertSql.append(split[1]+",");
            if(length == 1){
                createSql.append("  varchar2(4000),");
            }else {
                createSql.append(" "+split[2]+",");
            }
            if(length == 3){
                //���ֶ�����ע��
                batchSql.addBatchAfter("comment on column "+ tableName +"."+ split[1] +" is '"+ split[0] +"'");
            }
        }
        String sql = " create table " + tableName + " (" + createSql.substring(0, createSql.length()-1)
        + ")";
        batchSql.addBatch(sql); 
        //�������ģ��
        String insertPlaceholder = "insert into "+ tableName + " ("+ insertSql.substring(0, insertSql.length()-1) +")" +"values ("+getplaceholder(paramStr.length)+")";
        String insert = "insert into "+ tableName + " ("+ insertSql.substring(0, insertSql.length()-1) +")";
        batchSql.setInsertPlaceholderStr(insertPlaceholder);
        batchSql.setInsertString(insert);
    }

    /** 
     * @description ���ɹ̶�������չλ��
     *              (?,?,?,?)
     * @author ������ 2018-06-06
     * @param count
     * @return
     */ 
    public static String getplaceholder(int count)
    {
        StringBuilder placeholder = new StringBuilder();
        for(int i = 1; i <= count; i++)
        {
            placeholder.append("?,");
        }
        return placeholder.substring(0, placeholder.length()-1);
    }
    
    /** 
     * @description ������һ�仰�����������������
     * @author ������ 2018-06-06
     * @param paramStr  �����������
     * @param insertSql �������
     */
    public static void createInsert(List<String[]> listParam, BatchSql batchSql){
        for(String[] strings : listParam) {
            //�������ݿ�ֱ�ӿ���ʹ�õ�
            StringBuilder insert = new StringBuilder("values(");
            for(String string : strings) {
                if(string == ""){
                    insert.append("null,");
                }else {
                    insert.append("'"+string+"',");
                }
                
                if(string==""){
                    string = null;
                }
            }
            //ֱ�����а�
            batchSql.addBatchAfter(batchSql.getInsertString()+insert.substring(0,insert.length()-1)+")");
            //ռλ������
//            batchSql.addBatchAfter(batchSql.getInsertStr(), strings);
        }
    }
    
    /**
     * ���ݱ����Զ����ɸ������
     * @throws Exception 
     */
    public void getUpdateSqlByTableName(String tableName){
        List<Column> dateList = fieldHelper.getFieldInfoByTableName(tableName);
        StringBuilder sb=new StringBuilder();
        String columnName="";
        sb.append("update "+tableName+ " ");
        sb.append("set ");
        for (int i = 0,length = dateList.size(); i < length; i++) {
            columnName = dateList.get(i).name;
            sb.append(columnName+" = "+columnName+"");
            if(i != length-1){
                sb.append(", ");
            }
        }
        sb.append(" where id = id");
        System.out.println(sb);
    }
    
    /** 
     * @description ���ݱ�������select���
     * @author ������ 2018-06-29
     * @param tableName
     */ 
    public void getSelectSqlByTableName(String tableName){
        List<Column> dateList = fieldHelper.getFieldInfoByTableName(tableName);
        StringBuilder sb=new StringBuilder();
        String columnName="";
        sb.append("select ");
        for (int i = 0,length = dateList.size(); i < length; i++) {
            columnName = dateList.get(i).name;
            if("date".equals(dateList.get(i).type))
            {
                sb.append("to_char("+columnName+",'yyyy-mm-dd') "+ columnName);
            }else {
                sb.append(columnName);
            }
            if(i!= length-1){
                sb.append(", ");
            }
        }
        sb.append(" from " +tableName + " where 1 = 1");
        System.out.println(sb);
    }
    
    /** 
     * @Description: �� id in (....) ���� id in (...) or id in (...)
     * in (...)�е�
     * @author ������ 2018-07-30
     * @modify 
     * @param id
     * @param list
     * @return
     */ 
    private String getString(String id, List<String> list) {
        StringBuffer sb = new StringBuffer();
        String returnString = "";
        if(list.size() == 0 || null == list) {
            returnString = sb.append(id).append("=''").toString();
        }
        for(int i = 0; i < list.size(); i++) {
            if(i == 0) {
                sb.append(id);
                sb.append(" in (");
            }
            sb.append("'");
            sb.append(list.get(i).toString());
            sb.append("'");
            if(i >= 900 && i < list.size() - 1) {
                if(i % 900 == 0) {
                    sb.append(") or ");
                    sb.append(id);
                    sb.append(" in (");
                } else {
                    sb.append(",");
                }
            } else {
                if(i < list.size() - 1) {
                    sb.append(",");
                }
            }
            if(i == list.size() - 1) {
                sb.append(")");
            }
        }
        returnString = sb.toString();
        System.out.println(returnString);
        return returnString;
    }
    
    public ColumnHelper getFieldHelper() {
        return fieldHelper;
    }
    
    @Autowired
    public void setFieldHelper(ColumnHelper fieldHelper) {
        this.fieldHelper = fieldHelper;
    }
    
}
