/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.old.helper.excel.filter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

/** 
 * @description 根据单元格内容过滤(判断单元格是否包含某个字符串)
 * @author 陈吕奖
 * @date 2018-07-27 
 */
public class CellValueFilter implements CellFilterI {

    public String context;
    
    public CellValueFilter(String context){
        this.context = context;
    }

    @Override
    public boolean accept(HSSFRow row, HSSFCell cell) {
        //输出单元格内容
        System.out.println(cell.getStringCellValue());
        //输出当前的列数
        System.out.println("输出列号"+cell.getCellNum());
        //输出当前的行数
        System.out.println("输出行号"+row.getRowNum());
        if(cell.getStringCellValue().contains(context))
            return true;
        return false;
    }

}
