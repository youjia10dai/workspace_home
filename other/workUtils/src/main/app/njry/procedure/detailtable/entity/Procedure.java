/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.app.njry.procedure.detailtable.entity;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** 
 * @description 过程
 * @author 陈吕奖
 * @date 2018-12-03 
 */
@Component("procedure")
public class Procedure{
    
    /** 
     * @fields procedureParam 过程的参数
     */ 
    public String procedureParam ="v_day";
    
    /** 
     * @fields tjDate 统计日期
     */ 
    public String tjDate ="tj_day";
    
    /** 
     * @fields itemType 过程类型
     */ 
    @Value("#{detailTableProperties.itemType}")
    public int itemType;
    
    /**
     * @description 初始化,获取过程项类型
     * @author 陈吕奖 2018-11-29
     * @param cnt_item
     */
    @PostConstruct
    public void init(){
        System.out.println(itemType == 2);
        if(itemType == 2){
            this.procedureParam = "v_mon";
            this.tjDate = "tj_mon";
        }
        System.out.println(tjDate);
    }
    
}
