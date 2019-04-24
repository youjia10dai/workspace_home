/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.grammar;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import main.helper.database.DBHelper;
import org.springframework.stereotype.Component;


/** 
 * @description ����
 * ��ִ�������ʼ������,��ִ��Spring��ʼ������
 * @author ������
 * @date 2018-12-03 
 */
@Component("testInit")
public class ����Spring�ĳ�ʼ��ע�� {

    public DBHelper db;//����6
    
    public ����Spring�ĳ�ʼ��ע��(){
        System.out.println("�����ʼ������");
        System.out.println("��Ա�Ƿ�ע��" + db == null);
    }
    
    @PostConstruct
    public void init(){
        System.out.println("Spring��ʼ������");
        System.out.println("��Ա�Ƿ�ע��" + db == null);
    }
    
    @Resource(name = "DBHelper")
    public void setDb(DBHelper db) {
        this.db = db;
    }
}
