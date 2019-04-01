/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.app.njry.procedure.detailtable.factory;

import main.app.njry.procedure.detailtable.entity.Table;


/** 
 * @description ��񹤳���ӿ�
 * @author ������
 * @date 2018-12-03 
 */
public interface TableFactory {
    
    /** 
     * @fields RESULTTYPE ���������
     */ 
    int RESULTTYPE = 1;
    
    /** 
     * @fields DETAILTYPE ��ϸ������
     */ 
    int DETAILTYPE = 2;
    
    public Table makeTable(int type);
}