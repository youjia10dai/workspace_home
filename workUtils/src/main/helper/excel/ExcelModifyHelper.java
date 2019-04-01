/*
 * Copyright (c) 2019  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
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
 * @description �޸Ļ��ȡExcel������
 * @author ������
 * @date 2019-01-30 
 */
@Component("excelModifyHelper")
@Scope("prototype") //����   ��Ϊ�������������������
public class ExcelModifyHelper extends ExcelBaseHelper{
    
    /** 
     * @fields input �ļ���,���ڹر���Դ(ֻ��ȡ�ļ�ʱ,ʹ������ͷ���Դ)
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
