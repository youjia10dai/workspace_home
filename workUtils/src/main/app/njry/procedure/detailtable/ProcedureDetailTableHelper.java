/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.app.njry.procedure.detailtable;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import main.app.njry.procedure.detailtable.entity.Table;
import main.app.njry.procedure.detailtable.factory.TableFactory;
import main.helper.BaseHelper;
import main.spring.config.PropertyConfigure;
import main.spring.database.aspect.DataSource;
import main.spring.database.multDataSource.DBContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description �Զ����ɹ��̵���ϸ��
 * @author ������
 * @date 2018-11-29 
 */
@Component("proDetail")
@DataSource(name = DBContextHolder.zw3)
public class ProcedureDetailTableHelper extends BaseHelper{
    
    /**
     * @fields tableFactory ��񹤳�
     */
    @Autowired
    TableFactory tableFactory;
    
    @Autowired
    private PropertyConfigure config;
    
    public List<Table> tableList = new ArrayList<Table>();
    
    /**
     * @description �Զ����ɻ��ܱ�
     * @author ������ 2018-11-27
     * @param cnt_item
     * @param tableNames
     */
    public void createDetailTables(){
        //1.��ʼ����Ϣ
        init();
        //3.��������ڴ�����
        createTables();
        //4.����ɾ�����
        generateDeleteStatements();
        //4.���ɲ������
        generateInsertStatements();
        System.out.println("     commit;");
    }
    
    /**
     * @description ���������ļ���ʼ��
     * @author ������ 2018-11-29
     */
    private void init(){
        //��ȡ���е�Ԫ��,����Ԫ�ش������б�����
        String elements = config.getValue("elements");
        createTableObjects(elements);
    }
    
    /**
     * @description �������������
     * @author ������ 2018-11-29
     */
    private void createTableObjects(String elements){
        StringTokenizer st = new StringTokenizer(elements, ",");
        while(st.hasMoreElements()) {
            String nextToken = st.nextToken();
            String createdTableName = config.getValue(nextToken);
            String tmpporaryName = config.getValue(nextToken + "Tmpporary");
            int type = Integer.parseInt(config.getValue(nextToken + "Type"));
            createTableObject(createdTableName, tmpporaryName, type);
        }
    }
    
    /**
     * @description ����������
     * @author ������ 2018-11-29
     * @param tmpporaryName
     */
    private void createTableObject(String createdTableName, String tmpporaryName, int type){
        if(tmpporaryName != null){
            Table table = tableFactory.makeTable(type);
            table.init(createdTableName, tmpporaryName);
            tableList.add(table);
        }
    }
    
    /**
     * @description �������
     * @author ������ 2018-12-03
     */
    private void createTables() {
        for(Table table : tableList) {
            table.createTable();
        }
    }
    
    /**
     * @description ��������ɾ�����
     * @author ������ 2018-11-29
     */
    private void generateDeleteStatements() {
        for(Table table : tableList) {
            table.generateDeleteStatement();
        }
    }
    
    /**
     * @description ���ɲ������
     * @author ������ 2018-11-29
     */
    private void generateInsertStatements(){
        for(Table table : tableList) {
            table.generateInsertStatement();
        }
    }
}