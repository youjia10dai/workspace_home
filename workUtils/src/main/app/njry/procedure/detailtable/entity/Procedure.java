/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.app.njry.procedure.detailtable.entity;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** 
 * @description ����
 * @author ������
 * @date 2018-12-03 
 */
@Component("procedure")
public class Procedure{
    
    /** 
     * @fields procedureParam ���̵Ĳ���
     */ 
    public String procedureParam ="v_day";
    
    /** 
     * @fields tjDate ͳ������
     */ 
    public String tjDate ="tj_day";
    
    /** 
     * @fields itemType ��������
     */ 
    @Value("#{detailTableProperties.itemType}")
    public int itemType;
    
    /**
     * @description ��ʼ��,��ȡ����������
     * @author ������ 2018-11-29
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
