/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */
package main.old.helper.excel.filter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * @description ��Ԫ��ɸѡ��
 * @author ������
 * @date 2018-07-27
 */ 
public interface CellFilterI {
    
    /** 
     * @description �жϵ�Ԫ������Ƿ�����ɸѡ����
     * @author ������ 2018-07-30
     * @param row
     * @param cell
     * @return
     */ 
    public boolean accept(HSSFRow row,HSSFCell cell);
}
