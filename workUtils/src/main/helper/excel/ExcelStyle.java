/*
 * Copyright (c) 2019  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.helper.excel;

import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

/** 
 * @description Excel��ʽ
 * @author ������
 * @date 2019-02-01 
 */
public class ExcelStyle {
    
    private ExcelBaseHelper excelHelper;
    
    public ExcelStyle(ExcelBaseHelper excelHelper){
        this.excelHelper = excelHelper;
    }

    /** 
     * @description ���õ�Ԫ����ʽ
     * @author ������ 2019-02-01
     * @param cell
     * @param style
     */ 
    public void setExcelStyle(List<Cell> cells, int style) {
        for(Cell cell : cells) {
            setExcelStyle(cell, style);
        }
    }

    /** 
     * @description ������һ�仰�����������������
     * @author ������ 2019-02-01
     * @param style
     * @param cell
     */ 
    public void setExcelStyle(Cell cell, int style) {
        //���ݲ�ͬ��style,Ϊcell���ò�ͬ����ʽ
        CellStyle cellStyle = excelHelper.wb.createCellStyle();
        //���ʹ��cell.getCellStyle,��ô�޸ĵ������е�cell����ʽ
        switch (style) {
            case 1://����ʹ��һ��������з�װ
                //����
                cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
                //�ұ߿�
                cellStyle.setBorderRight(CellStyle.BORDER_THIN);
                //���ñ���ɫ(���ñ�����ɫ��Ҫ��������ֵ)
                cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);//����ǰ�������ʽ
                cellStyle.setFillForegroundColor((short)55);//ǰ�����ɫ
                break;
            case 100:
                //���ÿ��
                break;
            default:
                break;
        }
        //������ʽ
        cell.setCellStyle(cellStyle);
    }
    
    /** 
     * @description ������ʽ����ʽ
     * @author ������ 2019-02-01
     * @param styleNum
     */ 
    public void setSheetStyle(int styleNum) {
        //sheet.setDefaultColumnWidth((short)20);
        //���õ��еĿ��
        //����һ�еĸ߶���Ч(һ��һ������)
        //sheet.getRow(0).setHeight((short)(20*200));
        switch (styleNum) {
            case 1:
                excelHelper.currentheet.setColumnWidth((short) 0, (short) (40 * 256));
                break;
            case 100:
                //���ÿ��
                break;
            default:
                break;
        }
    }
}
