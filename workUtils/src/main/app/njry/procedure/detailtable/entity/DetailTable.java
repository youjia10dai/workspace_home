/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.app.njry.procedure.detailtable.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/** 
 * @description ��ϸ��
 * @author ������
 * @date 2018-12-03 
 */
@Component("detailTable")
@Scope("prototype")
public class DetailTable extends Table{

    @Override
    public void generateDeleteStatement() {
    }

    @Override
    public void generateInsertStatement() {        
    }

    @Override
    public String getCreateTableSql() {
        
        return null;
    }
    
    @Override
    public void createTable() {
        System.out.println("     droptable('"+ tableName +"'||v_mon);");
        System.out.println("     v_sql := ' rename "+ tmpporaryTableName +" to "+ tableName +"'||v_mon;");
        System.out.println("     execute immediate v_sql;");
        System.out.println();
    }

}
