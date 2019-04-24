/*
 * Copyright (c) 2019  南京瑞玥科技有限公司. All Rights Reserved.
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
    
    @Test//导出表格所有内容
    public void test1() throws Exception{
        ExportHelper exportHelper = (ExportHelper)context.getBean("exportHelper");
        exportHelper.exportByTableName("clj_20190221_tmp7");
    }
    
    @Test//导出表格的指定字段
    public void test2() throws Exception{
        ExportHelper exportHelper = (ExportHelper)context.getBean("exportHelper");
        exportHelper.exportByTableName("clj_20190221_tmp7", new String[]{"CUSTOMER_ID", "ATTR_VALUE"});
    }
    
    @Test//提供sql语句,导出指定字段
    public void test3() throws Exception{
        ExportHelper exportHelper = (ExportHelper)context.getBean("exportHelper");
        exportHelper.exportBySql("select * from clj_20190221_tmp7 where cnt > 0", new String[]{"CUSTOMER_ID", "ATTR_VALUE"});
    }
    
}
