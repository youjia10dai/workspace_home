/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.helper.database.column;

/** 
 * @description ����һ���ֶε�����(ֻ��װ�˳��õ�������Ϣ)
 * @author ������
 * @date 2018-06-29 
 */
public class Column {

    /** 
     * @fields name �ֶ�����
     */ 
    public String name;
    
    /** 
     * @fields type �ֶ�����
     */ 
    public String type;

    /**
     * @description ��ʼ���ֶ����ֺ�����
     * @param name
     * @param type
     */
    public Column(String name, String type) {
        super();
        this.name = name;
        this.type = type;
    }
    
    
}
