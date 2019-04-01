/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */
package main.old.helper.excel.filter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * @description 单元格筛选器
 * @author 陈吕奖
 * @date 2018-07-27
 */ 
public interface CellFilterI {
    
    /** 
     * @description 判断单元格和行是否满足筛选条件
     * @author 陈吕奖 2018-07-30
     * @param row
     * @param cell
     * @return
     */ 
    public boolean accept(HSSFRow row,HSSFCell cell);
}
