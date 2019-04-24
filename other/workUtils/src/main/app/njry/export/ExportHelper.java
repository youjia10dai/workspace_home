/*
 * Copyright (c) 2019  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.app.njry.export;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import main.helper.BaseHelper;
import main.helper.excel.ExcelCreateHelper;
import main.spring.database.aspect.DataSource;
import main.spring.database.multDataSource.DBContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/** 
 * @description 大量数据导出到excel文件中
 * @author 陈吕奖
 * @date 2019-02-21 
 */
@Component("exportHelper")
@Scope("prototype") //多例   因为这个帮助类自身有属性
@DataSource(name = DBContextHolder.zw3)
public class ExportHelper extends BaseHelper{

    /** 
     * @fields MAXCOUNT 一页的最大条数
     */ 
    public static final int MAXCOUNT = 5000;
    
    /** 
     * @fields pages 有多少页
     */ 
    private int pages;
    
    private int count;
    
    private int rowNumber = 1;
    
    private String tableName;
    
    private List<String> tableColumns;
    
    public static final String GETPAGESSQL = "select count(1) from ";
    
    public static final String getColumnSql = "select column_name from user_tab_cols where table_name = ? order by segment_column_id";
    
    public String getDataSql = " select * from ";
    
    
    @Autowired
    private ExcelCreateHelper excelHelper;
    

    public void exportByTableName(String tableName){
        init(tableName);
        tableColumns = getAllTableColumns();
        exportData();
    }

    public void exportByTableName(String tableName, String[] Columns){
        init(tableName, Columns);
        exportData();
    }
    
    public void exportBySql(String sql, String[] columns){
        String tableName = "(" + sql + ")";
        init(tableName, columns);
        getDataSql = sql;
        exportData();
    }
    
    private void init(String tableName, String[] columns) {
        init(tableName);
        tableColumns = Arrays.asList(columns);
    }
    
    /** 
     * @description 提供相关的数据
     * @author 陈吕奖 2019-02-21
     */ 
    private void init(String tableName) {
        this.tableName = tableName;
        getDataSql = getDataSql + tableName;
        pagesInit();
    }
    
    /** 
     * @description 页数出事化
     * @author 陈吕奖 2019-02-21
     */ 
    private void pagesInit() {
        count = db.queryForInt(GETPAGESSQL + tableName);
        pages = count % MAXCOUNT == 0 ? count / MAXCOUNT : count / MAXCOUNT + 1;
    }
    
    private List<String> getAllTableColumns(){
        List<String> columnNames = new ArrayList<String>();
        List<Map<String, Object>> queryForList = db.queryForList(getColumnSql, tableName.toUpperCase());
        for(Map<String, Object> map : queryForList) {
            columnNames.add(map.get("COLUMN_NAME").toString().toUpperCase());
        }
        return columnNames;
    }
    
    /** 
     * @description 导出数据
     * @author 陈吕奖 2019-02-21
     */ 
    private void exportData() {
        createExcel();
        exportDataToExcel();
        save();
    }

    /**
     * @description 创建excel文件
     * @author 陈吕奖 2019-02-22
     */ 
    private void createExcel() {
        excelHelper.create("D:\\data.xlsx", new String[]{"data"});
    }

    private void exportDataToExcel(){
        exportHeadData();
        exportBodyData();
    }
    
    private void exportHeadData(){
        excelHelper.modifyRow(0, tableColumns.toArray(new String[]{}));
    }
    
    private void exportBodyData(){
        for(int pageIndex = 1; pageIndex <= pages; pageIndex++){
            exportPageData(pageIndex);
        }
    }
    
    private void exportPageData(int pageIndex){
        String sql = "";
        int start = (pageIndex - 1)* MAXCOUNT + 1 ;
        int end = pageIndex * MAXCOUNT;
        end = end > count ? count : end;
        log.debug(getDataSql);
        sql = "SELECT * FROM (SELECT AA.*, rownum rr  FROM (" + getDataSql + 
              " )AA  )BB where rr <= " + end + " and rr >= " + start;
        System.out.println(sql);
        List<Map<String, Object>> data = db.queryForList(sql);
        for(Map<String, Object> map : data) {
            exportRowData(map);
        }
    }
    
    private void exportRowData(Map<String, Object> map){
        String[] datas = new String[tableColumns.size()];
        for(int i = 0; i < tableColumns.size(); i++) {
            datas[i] = map.get(tableColumns.get(i)) + "";
        }
        excelHelper.modifyRow(rowNumber, datas);
        rowNumber++;
        log.debug(rowNumber);
    }
    
    private void save(){
        excelHelper.write();
    }
}