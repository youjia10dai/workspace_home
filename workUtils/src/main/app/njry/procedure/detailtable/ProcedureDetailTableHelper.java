/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
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
 * @description 自动生成过程的明细表
 * @author 陈吕奖
 * @date 2018-11-29 
 */
@Component("proDetail")
@DataSource(name = DBContextHolder.zw3)
public class ProcedureDetailTableHelper extends BaseHelper{
    
    /**
     * @fields tableFactory 表格工厂
     */
    @Autowired
    TableFactory tableFactory;
    
    @Autowired
    private PropertyConfigure config;
    
    public List<Table> tableList = new ArrayList<Table>();
    
    /**
     * @description 自动生成汇总表
     * @author 陈吕奖 2018-11-27
     * @param cnt_item
     * @param tableNames
     */
    public void createDetailTables(){
        //1.初始化信息
        init();
        //3.如果表不存在创建表
        createTables();
        //4.生成删除语句
        generateDeleteStatements();
        //4.生成插入语句
        generateInsertStatements();
        System.out.println("     commit;");
    }
    
    /**
     * @description 根据配置文件初始化
     * @author 陈吕奖 2018-11-29
     */
    private void init(){
        //获取所有的元素,根据元素创建所有表格对象
        String elements = config.getValue("elements");
        createTableObjects(elements);
    }
    
    /**
     * @description 创建表格多个对象
     * @author 陈吕奖 2018-11-29
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
     * @description 创建表格对象
     * @author 陈吕奖 2018-11-29
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
     * @description 创建表格
     * @author 陈吕奖 2018-12-03
     */
    private void createTables() {
        for(Table table : tableList) {
            table.createTable();
        }
    }
    
    /**
     * @description 生成数据删除语句
     * @author 陈吕奖 2018-11-29
     */
    private void generateDeleteStatements() {
        for(Table table : tableList) {
            table.generateDeleteStatement();
        }
    }
    
    /**
     * @description 生成插入语句
     * @author 陈吕奖 2018-11-29
     */
    private void generateInsertStatements(){
        for(Table table : tableList) {
            table.generateInsertStatement();
        }
    }
}