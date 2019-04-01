/*
 * Copyright (c) 2019  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package test.helper.export;


import main.app.njry.export.ExportHelper;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ExportHelperTest {
    
    public static ApplicationContext context =null;
    static{       
         context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }
    
    @Test//���������������
    public void test1() throws Exception{
        ExportHelper exportHelper = (ExportHelper)context.getBean("exportHelper");
        exportHelper.exportByTableName("clj_20190221_tmp7");
    }
    
    @Test//��������ָ���ֶ�
    public void test2() throws Exception{
        ExportHelper exportHelper = (ExportHelper)context.getBean("exportHelper");
        exportHelper.exportByTableName("clj_20190221_tmp7", new String[]{"CUSTOMER_ID", "ATTR_VALUE"});
    }
    
    @Test//�ṩsql���,����ָ���ֶ�
    public void test3() throws Exception{
        ExportHelper exportHelper = (ExportHelper)context.getBean("exportHelper");
        exportHelper.exportBySql("select * from clj_20190221_tmp7 where cnt > 0", new String[]{"CUSTOMER_ID", "ATTR_VALUE"});
    }
    
}
