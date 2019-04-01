/*
 * Copyright (c) 2019  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.helper.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

/** 
 * @description ���ݵ�Ԫ�����ͻ�ȡ��Ԫ������
 * @author ������
 * @date 2019-01-31 
 */
public class CellContextUtils {

    public static String getCellContext(Cell cell){
        if(cell == null)
            return "";
        String contexts = "";
        if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
            contexts = getNumber(cell);
        }else{
            contexts = getString(cell);
        }
        return contexts;
    }
    
    public static String getNumber(Cell cell){
        return (new Double(cell.getNumericCellValue())).longValue()+"";
    }
    
    public static String getString(Cell cell){
        return cell.getStringCellValue().trim();
    }
}
