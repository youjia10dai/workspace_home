/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.app.njry.procedure.detailtable.factory;

import main.app.njry.procedure.detailtable.entity.DetailTable;
import main.app.njry.procedure.detailtable.entity.ResultTable;
import main.app.njry.procedure.detailtable.entity.Table;
import main.app.njry.procedure.detailtable.exception.InvalidTableType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @description 创建表
 * @author 陈吕奖
 * @date 2018-12-03 
 */
@Component("tableFactroy")
public class TableFactoryImp implements TableFactory{
    
    @Autowired
    ClassPathXmlApplicationContext spring;
    
    @Override
    public Table makeTable(int type) {
        switch(type){
            case RESULTTYPE:
                return spring.getBean(ResultTable.class);
            case DETAILTYPE:
                return spring.getBean(DetailTable.class);
            default:
                throw new InvalidTableType("无效的表类型");
        }
    }

}
