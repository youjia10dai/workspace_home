/*
 * Copyright (c) 2019  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
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
 * @description ����Excel�İ�����
 * @author ������
 * @date 2019-01-30 
 */
@Component("excelCreateHelper")
@Scope("prototype") //����   ��Ϊ�������������������
public class ExcelCreateHelper extends ExcelBaseHelper{
    
    public ExcelCreateHelper(){
    }
    
    //����excel�ļ�
    public File create(String filePathAndName, String[] sheetNames){
        return create(new File(filePathAndName), sheetNames);
    }
    
    public File create(File excelFile, String[] sheetNames){
        File execlFile = createFile(excelFile, sheetNames);
        init();
        return execlFile;
    }
    
    /** 
     * @description ������һ�仰�����������������
     * @author ������ 2019-01-30
     * @param fileName
     * @param sheetNames
     */ 
    private File createFile(File excelFile, String[] sheetNames) {
        try {
           return createExcel(excelFile, sheetNames);
        }catch (Exception e) {
            new ExcelException("����excel�ļ�����");
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
    
    //����sheet����ʼ��sheet����
    private void crateSheet(String[] sheetNames){
        for(int i = 0; i < sheetNames.length; i++) {
            createSheet(sheetNames[i]);
        }
    }
}