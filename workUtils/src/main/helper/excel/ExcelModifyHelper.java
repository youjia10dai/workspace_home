/*
 * Copyright (c) 2019  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.helper.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/** 
 * @description 修改或读取Excel帮助类
 * @author 陈吕奖
 * @date 2019-01-30 
 */
@Component("excelModifyHelper")
@Scope("prototype") //多例   因为这个帮助类自身有属性
public class ExcelModifyHelper extends ExcelBaseHelper{
    
    /** 
     * @fields input 文件流,用于关闭资源(只读取文件时,使用这个释放资源)
     */ 
    private FileInputStream input;
    
    public void load(String filePathAndName){
        load(new File(filePathAndName));
        init();
    }
    
    public void load(File excelFile){
        try {
            this.excelFile = excelFile;
            input = new FileInputStream(excelFile);
            String name = excelFile.getName();
            log.debug(name);
            if(name.endsWith(".xls")){
                log.debug("1");
                wb = new HSSFWorkbook(input);
            }else if (name.endsWith(".xlsx")) {
                log.debug("2");
                wb = new XSSFWorkbook(input);
            }
            initSheet();
            currentheet = wb.getSheetAt(0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void initSheet(){
        for(int i = 0; i < wb.getNumberOfSheets(); i++){
            sheets.add(wb.getSheetAt(i));
        }
    }
    
    public void close(){
        if(wb != null && input != null)
        {
            try {
                input.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
