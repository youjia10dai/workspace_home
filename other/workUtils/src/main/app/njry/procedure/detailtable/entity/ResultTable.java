/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.app.njry.procedure.detailtable.entity;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/** 
 * @description �ĸ��㼶�Ľ����
 * @author ������
 * @date 2018-12-03 
 */
@Component("resultTable")
@Scope("prototype")
public class ResultTable extends Table{
    
    @Override
    public void createTable(){
        //���Ѿ����ھ��˳�
        if(isExistTable(tableName)){
            return;
        }
        //������
        db.update(getCreateTableSql());
    }
    
    @Override
    public void generateDeleteStatement() {
        System.out.println("     v_sql := ' delete from "+ tableName +" where "+ procedure.tjDate +" = ' || "+ procedure.procedureParam +";");
        System.out.println("     execute immediate v_sql;");
        System.out.println();
    }
    
    @Override
    public void generateInsertStatement() {
        String columnNames = getAllTableColumns(tmpporaryTableName);
        System.out.println("     v_sql := ' insert into " + tableName);
        System.out.println("                      (" + columnNames + procedure.tjDate +")");
        System.out.println("                select " + columnNames + "'|| "+ procedure.procedureParam +" ||' "+ procedure.tjDate);
        System.out.println("                  from "+ tmpporaryTableName + " ';");
        System.out.println("     execute immediate v_sql;");
        System.out.println();
    }

    /** 
     * @description ��ȡ���������ֶ�
     * @author ������ 2018-11-29
     * @param tableName
     * @return
     */ 
    private String getAllTableColumns(String tableName){
        StringBuilder columnNames = new StringBuilder();
        String columnName = "";
        List<Map<String, Object>> queryForList = db.queryForList(getColumnSql, tableName.toUpperCase());
        for(Map<String, Object> map : queryForList) {
            columnName += map.get("COLUMN_NAME").toString().toLowerCase() + ", ";
            if(columnName.length() >= 50){
                columnNames.append(columnName + "\n");
                columnName = "                       ";
            }
        }
        if(!"".equals(columnName.trim()))
            return columnNames.append(columnName).toString();
        return columnNames.substring(0, columnNames.length() - 1).toString();
    }
    
    @Override
    public String getCreateTableSql() {
        String sql = "  create table "+ tableName +" as"+
                     "  select cast(null as number) "+ procedure.tjDate +", a.* " +
                     "    from "+ tmpporaryTableName +" a " +
                     "   where 1 = 2";
        return sql;
    }

}