/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.helper.database.sql;

import java.util.List;
import main.helper.database.column.Column;
import main.helper.database.column.ColumnHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/** 
 * @description 自动生成SQL语句
 * @author 陈吕奖
 * @date 2018-06-05 
 */
@Component("sqlhlper")
public class SQLHlper {

    //"\r\n" 打印的换行
    /** 
     * @fields fieldHelper 数据库字段信息帮助类
     */ 
    private ColumnHelper fieldHelper;

    /** 
     * @description 1.生成建表语句 2.生成字段添加注释语句 3.生成表的insert语句模板
     * @author 陈吕奖 2018-06-05
     * @param tableName  表名
     * @param paramStr   str1,str2,str3
     *          str1 表示字段名
     *          str2 表示字段类型  有默认的字段类型
     *          str3 表示字段注释
     * @param batchSql  存储sql语句的集合
     */ 
    public static void createTable(String tableName, String[] paramStr, BatchSql batchSql){
        //
        StringBuilder createSql = new StringBuilder();
        StringBuilder insertSql = new StringBuilder();
        
        //先获取数组的长度
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
                //给字段生成注释
                batchSql.addBatchAfter("comment on column "+ tableName +"."+ split[1] +" is '"+ split[0] +"'");
            }
        }
        String sql = " create table " + tableName + " (" + createSql.substring(0, createSql.length()-1)
        + ")";
        batchSql.addBatch(sql); 
        //插入语句模板
        String insertPlaceholder = "insert into "+ tableName + " ("+ insertSql.substring(0, insertSql.length()-1) +")" +"values ("+getplaceholder(paramStr.length)+")";
        String insert = "insert into "+ tableName + " ("+ insertSql.substring(0, insertSql.length()-1) +")";
        batchSql.setInsertPlaceholderStr(insertPlaceholder);
        batchSql.setInsertString(insert);
    }

    /** 
     * @description 生成固定数量的展位符
     *              (?,?,?,?)
     * @author 陈吕奖 2018-06-06
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
     * @description 这里用一句话描述这个方法的作用
     * @author 陈吕奖 2018-06-06
     * @param paramStr  带插入的数据
     * @param insertSql 插入语句
     */
    public static void createInsert(List<String[]> listParam, BatchSql batchSql){
        for(String[] strings : listParam) {
            //生成数据库直接可以使用的
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
            //直接运行版
            batchSql.addBatchAfter(batchSql.getInsertString()+insert.substring(0,insert.length()-1)+")");
            //占位符符版
//            batchSql.addBatchAfter(batchSql.getInsertStr(), strings);
        }
    }
    
    /**
     * 根据表名自动生成更新语句
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
     * @description 根据表名生成select语句
     * @author 陈吕奖 2018-06-29
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
     * @Description: 将 id in (....) 便曾 id in (...) or id in (...)
     * in (...)中的
     * @author 陈吕奖 2018-07-30
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
