/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.old.helper.excel.filter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

/** 
 * @description ���ݵ�Ԫ�����ݹ���(�жϵ�Ԫ���Ƿ����ĳ���ַ���)
 * @author ������
 * @date 2018-07-27 
 */
public class CellValueFilter implements CellFilterI {

    public String context;
    
    public CellValueFilter(String context){
        this.context = context;
    }

    @Override
    public boolean accept(HSSFRow row, HSSFCell cell) {
        //�����Ԫ������
        System.out.println(cell.getStringCellValue());
        //�����ǰ������
        System.out.println("����к�"+cell.getCellNum());
        //�����ǰ������
        System.out.println("����к�"+row.getRowNum());
        if(cell.getStringCellValue().contains(context))
            return true;
        return false;
    }

}
