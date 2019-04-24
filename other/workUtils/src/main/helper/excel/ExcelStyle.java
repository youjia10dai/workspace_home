/*
 * Copyright (c) 2019  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.helper.excel;

import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

/** 
 * @description Excel样式
 * @author 陈吕奖
 * @date 2019-02-01 
 */
public class ExcelStyle {
    
    private ExcelBaseHelper excelHelper;
    
    public ExcelStyle(ExcelBaseHelper excelHelper){
        this.excelHelper = excelHelper;
    }

    /** 
     * @description 设置单元格样式
     * @author 陈吕奖 2019-02-01
     * @param cell
     * @param style
     */ 
    public void setExcelStyle(List<Cell> cells, int style) {
        for(Cell cell : cells) {
            setExcelStyle(cell, style);
        }
    }

    /** 
     * @description 这里用一句话描述这个方法的作用
     * @author 陈吕奖 2019-02-01
     * @param style
     * @param cell
     */ 
    public void setExcelStyle(Cell cell, int style) {
        //根据不同的style,为cell设置不同的样式
        CellStyle cellStyle = excelHelper.wb.createCellStyle();
        //如果使用cell.getCellStyle,那么修改的是所有的cell的样式
        switch (style) {
            case 1://可以使用一个对象进行封装
                //居中
                cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
                //右边框
                cellStyle.setBorderRight(CellStyle.BORDER_THIN);
                //设置背景色(设置背景颜色需要设置两个值)
                cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);//设置前景填充样式
                cellStyle.setFillForegroundColor((short)55);//前景填充色
                break;
            case 100:
                //设置宽高
                break;
            default:
                break;
        }
        //设置样式
        cell.setCellStyle(cellStyle);
    }
    
    /** 
     * @description 设置样式表样式
     * @author 陈吕奖 2019-02-01
     * @param styleNum
     */ 
    public void setSheetStyle(int styleNum) {
        //sheet.setDefaultColumnWidth((short)20);
        //设置单行的宽度
        //设置一行的高度有效(一行一行设置)
        //sheet.getRow(0).setHeight((short)(20*200));
        switch (styleNum) {
            case 1:
                excelHelper.currentheet.setColumnWidth((short) 0, (short) (40 * 256));
                break;
            case 100:
                //设置宽高
                break;
            default:
                break;
        }
    }
}
