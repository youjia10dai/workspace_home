/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.app.njry.procedure.detailtable.factory;

import main.app.njry.procedure.detailtable.entity.Table;


/** 
 * @description 表格工厂类接口
 * @author 陈吕奖
 * @date 2018-12-03 
 */
public interface TableFactory {
    
    /** 
     * @fields RESULTTYPE 结果表类型
     */ 
    int RESULTTYPE = 1;
    
    /** 
     * @fields DETAILTYPE 明细表类型
     */ 
    int DETAILTYPE = 2;
    
    public Table makeTable(int type);
}