/*
 * Copyright (c) 2019  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.app.njry.email;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.helper.BaseHelper;
import main.helper.excel.ExcelCreateHelper;
import main.helper.excel.RectangleRange;
import main.spring.database.aspect.DataSource;
import main.spring.database.multDataSource.DBContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @description 开门红Excel附件
 * @author 陈吕奖
 * @date 2019-02-01
 */
@Component("starEmailExcelAttach")
@Scope("prototype")
@DataSource(name = DBContextHolder.zw6)//在账务6进行操作
public class StarEmailExcelAttach extends BaseHelper{

    @Autowired
    private ExcelCreateHelper excelHelper; 
    
    //获取开门红数据
    private String originalContextSql = "select a.kpi_type_name, a.kpi_name, a.big_order_id, a.small_order_id, b.zhibiao_name, b.order_id," +
                            "                   nvl(c.C001, ' ') C001, nvl(c.C002, ' ') C002, nvl(c.C003, ' ') C003, nvl(c.C004, ' ') C004, nvl(c.C005, ' ') C005, nvl(c.C006, ' ') C006, " +
                            "                   nvl(c.C007, ' ') C007, nvl(c.C008, ' ') C008, nvl(c.C009, ' ') C009, nvl(c.C010, ' ') C010," +
                            "                   nvl(c.C011, ' ') C011, nvl(c.C012, ' ') C012" +
                            "              from t_crm_kmh2019_ztpj_cfg a, t_crm_kmh2019_ztpj_cfg2 b, t_crm_kmh2019_ztpj_content c"+
                            "             where a.kpi_type_id = b.kpi_type_id"+ 
                            "               and a.kpi_id = b.kpi_id"+
                            "               and a.kpi_type_id = c.kpi_type_id"+
                            "               and a.kpi_id = c.kpi_id"+
                            "               and b.zhibiao_id = c.zhibiao_id"+
                            "               and c.day_id = ?"  + 
                            "              order by big_order_id, small_order_id, order_id";
    
    //大标题
    private String titleBigSql = "select a.kpi_type_id, kpi_type_name name, count(1) cnt" +
                        "        from t_crm_kmh2019_ztpj_cfg a, t_crm_kmh2019_ztpj_cfg2 b, t_crm_kmh2019_ztpj_content c"+
                        "       where a.kpi_type_id = b.kpi_type_id"+ 
                        "         and a.kpi_id = b.kpi_id"+
                        "         and a.kpi_type_id = c.kpi_type_id"+
                        "         and a.kpi_id = c.kpi_id"+
                        "         and b.zhibiao_id = c.zhibiao_id"+
                        "         and c.day_id = ?" +
                        "       group by a.kpi_type_id, kpi_type_name" +
                        "       order by a.kpi_type_id";
    
    //小标题
    private String titleSmallSql = "select a.kpi_type_id, a.kpi_id, kpi_name name, count(1) cnt" +
                            "         from t_crm_kmh2019_ztpj_cfg a, t_crm_kmh2019_ztpj_cfg2 b, t_crm_kmh2019_ztpj_content c"+
                            "        where a.kpi_type_id = b.kpi_type_id"+ 
                            "          and a.kpi_id = b.kpi_id"+
                            "          and a.kpi_type_id = c.kpi_type_id"+
                            "          and a.kpi_id = c.kpi_id"+
                            "          and b.zhibiao_id = c.zhibiao_id"+
                            "          and c.day_id = ?" +
                            "        group by a.kpi_type_id, a.kpi_id, a.kpi_name" +
                            "        order by kpi_type_id, kpi_id";
    private String date;
    private List<String> titleNames = new ArrayList<String>();
    private String[] columns = new String[]{"zhibiao_name", "C001", "C002", "C003", "C004", "C005", "C006", "C007", "C008", "C009", "C010", "C011", "C012"};
    
    public File[] getAttchFiles(String date){
        init(date);
        File createFile = excelHelper.create("D:\\南京分公司重点工作日报test.xls", new String[]{"总体评价"});
        generateContext();
        return new File[]{createFile};
    }

    private void init(String date) {
        this.date = date;
    }

    /** 
     * @description 生成内容
     * @author 陈吕奖 2019-02-02
     */ 
    private void generateContext() {
        generateTableHead();
        generateTitle();
        generateData();
        generateStyle();
        saveFile();
    }

    /** 
     * @description 生成表头
     * @author 陈吕奖 2019-02-02
     */ 
    private void generateTableHead() {
        excelHelper.mergeCells(new RectangleRange(0, 0, 0, 15));
        excelHelper.mergeCells(new RectangleRange(1, 1, 0, 15));
        excelHelper.modifyCell(0, 0, "南京分公司重点工作日报 ");
        excelHelper.modifyCell(1, 0, "统计日期("+ date +")");
        excelHelper.modifyRow(2, new String[]{"编号", "考核大类", "考核项目", "周期", "全区", "玄秦", "雨花", "鼓建", "栖霞", "江宁", 
                                              "浦口", "六合", "溧水", "高淳", "市政企", "省政企"});
    }
    
    /** 
     * @description 生成标题
     * @author 陈吕奖 2019-02-02
     */ 
    private void generateTitle(){
        Map<String, Integer> titleSmallContext = getTitleContext(titleSmallSql);
        generateTitleExcel(titleSmallContext, 3, 2);
        Map<String, Integer> titleBigContext = getTitleContext(titleBigSql);
        generateTitleExcel(titleBigContext, 3, 1);
    }

    /** 
     * @description 这里用一句话描述这个方法的作用
     * @author 陈吕奖 2019-02-02
     * @param titleSmallContext
     */ 
    private void generateTitleExcel(Map<String, Integer> titleSmallContext, int rowIndex, int colIndex){
        int number = 1;
        for(String titleName : titleNames){
            Integer count = titleSmallContext.get(titleName);
            excelHelper.modifyCell(rowIndex, colIndex, titleName);
            excelHelper.mergeCells(new RectangleRange(rowIndex, rowIndex + count - 1, colIndex, colIndex));
            if(colIndex == 2){
                excelHelper.modifyCell(rowIndex, 0, number++ + "");
                excelHelper.mergeCells(new RectangleRange(rowIndex, rowIndex + count - 1, 0, 0));
            }
            log.debug(rowIndex);
            log.debug(rowIndex + count);
            rowIndex = rowIndex + count;
        }
    }

    /** 
     * @description 这里用一句话描述这个方法的作用
     * @author 陈吕奖 2019-02-02
     */ 
    private Map<String, Integer> getTitleContext(String sql) {
        Map<String, Integer> titleContexts = new HashMap<String, Integer>();
        List<Map<String, Object>> _titles = db.queryForList(sql, date);
        titleNames.clear();
        for(Map<String, Object> map : _titles) {
            String name = map.get("name").toString();
            titleNames.add(name);
            titleContexts.put(name, Integer.parseInt(map.get("cnt").toString()));
        }
        return titleContexts;
    }
    
    /** 
     * @description 生成数据
     * @author 陈吕奖 2019-02-02
     */ 
    private void generateData(){
        List<Map<String, Object>> contexts = db.queryForList(originalContextSql, date);
        // 3, 3  表示的是数据开始的位置
        generateDateExcel(contexts, 3, 3);
    }
    
    /** 
     * @description 这里用一句话描述这个方法的作用
     * @author 陈吕奖 2019-02-02
     * @param contexts
     */ 
    private void generateDateExcel(List<Map<String, Object>> contexts, int rowIndex, int colIndex){
        Map<String, Object> contextMap;
        for(int i=0; i<contexts.size(); i++){
            contextMap = contexts.get(i);
            List<String> columnContext = new ArrayList<String>();
            for(String column : columns){
                columnContext.add(contextMap.get(column).toString().trim());
            }
            if(!"".equals(columnContext)){
                excelHelper.modifyRow(rowIndex + i, colIndex, columnContext.toArray(new String[]{}));
            }
        }
    }

    /** 
     * @description 添加样式
     * @author 陈吕奖 2019-02-03
     */ 
    private void generateStyle(){
        excelHelper.setSheetStyle(1);
    }
    
    private void saveFile() {
        excelHelper.write();
    }
}