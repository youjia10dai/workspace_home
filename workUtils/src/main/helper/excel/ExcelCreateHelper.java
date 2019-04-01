/*
 * Copyright (c) 2019  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.helper.excel;

import java.io.File;
import java.io.IOException;
import main.old.helper.excel.exception.ExcelException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/** 
 * @description 创建Excel的帮助类
 * @author 陈吕奖
 * @date 2019-01-30 
 */
@Component("excelCreateHelper")
@Scope("prototype") //多例   因为这个帮助类自身有属性
public class ExcelCreateHelper extends ExcelBaseHelper{
    
    public ExcelCreateHelper(){
    }
    
    //创建excel文件
    public File create(String filePathAndName, String[] sheetNames){
        return create(new File(filePathAndName), sheetNames);
    }
    
    public File create(File excelFile, String[] sheetNames){
        File execlFile = createFile(excelFile, sheetNames);
        init();
        return execlFile;
    }
    
    /** 
     * @description 这里用一句话描述这个方法的作用
     * @author 陈吕奖 2019-01-30
     * @param fileName
     * @param sheetNames
     */ 
    private File createFile(File excelFile, String[] sheetNames) {
        try {
           return createExcel(excelFile, sheetNames);
        }catch (Exception e) {
            new ExcelException("创建excel文件出错");
        }
        return null;
    }
    
    private File createExcel(File _excelFile, String[] sheetNames) throws IOException{
        excelFile = _excelFile;
        if(!excelFile.exists()){
            excelFile.createNewFile();
        }
        String name = excelFile.getName();
        if(name.endsWith(".xls")){
            wb = new HSSFWorkbook();
        }else if (name.endsWith(".xlsx")) {
            wb = new SXSSFWorkbook(1000);
        }
        crateSheet(sheetNames);
        currentheet = wb.getSheetAt(0);
        return excelFile;
    }
    
    //创建sheet并初始化sheet名字
    private void crateSheet(String[] sheetNames){
        for(int i = 0; i < sheetNames.length; i++) {
            createSheet(sheetNames[i]);
        }
    }
}