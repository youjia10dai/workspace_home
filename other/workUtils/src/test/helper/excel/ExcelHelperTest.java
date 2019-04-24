/*
 * Copyright (c) 2019  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package test.helper.excel;

import java.util.List;
import main.app.njry.email.StarEmailExcelAttach;
import main.helper.excel.ExcelCreateHelper;
import main.helper.excel.ExcelModifyHelper;
import main.helper.excel.RectangleRange;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/** 
 * @description 这里用一句话描述这个类的作用
 * @author 陈吕奖
 * @date 2019-01-31 
 */
public class ExcelHelperTest {
    public static ApplicationContext context =null;
    static{       
         context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }
    
    @Test//获取单元格内容
    public void test1() throws Exception{
        ExcelModifyHelper excelHelper = (ExcelModifyHelper)context.getBean("excelModifyHelper");
        excelHelper.load("D:\\计费用户.xlsx");
        System.out.println(excelHelper.getCellContext(5, 5)); //测试获取一个单元格
        excelHelper.close();
    }
    
    @Test//获取一列内容
    public void test2() throws Exception{
        ExcelModifyHelper excelHelper = (ExcelModifyHelper)context.getBean("excelModifyHelper");
        excelHelper.load("D:\\1.xls");
//        System.out.println(excelHelper.getCellContext(47, 1)); //测试获取一个单元格
        String[] context = excelHelper.getColContextByIndex(5);//测试获取一列
        System.out.println(context.length);
        for(int i = 0; i < context.length; i++) {
            System.out.println("输出:" + context[i]);
        }
        excelHelper.close();
    }
    
    @Test//获取一行内容
    public void test3() throws Exception{
        ExcelModifyHelper excelHelper = (ExcelModifyHelper)context.getBean("excelModifyHelper");
        excelHelper.load("D:\\1.xls");
//        System.out.println(excelHelper.getCellContext(47, 1)); //测试获取一个单元格
//        String[] context = excelHelper.getColContextByIndex(3);//测试获取一列
        String[] context = excelHelper.getRowContextByIndex(3);//测试获取一行
        System.out.println(context.length);
        for(int i = 0; i < context.length; i++) {
            System.out.println(context[i]);
        }
        excelHelper.close();
    }
    
    @Test//获取多列内容
    public void test4() throws Exception{
        ExcelModifyHelper excelHelper = (ExcelModifyHelper)context.getBean("excelModifyHelper");
        excelHelper.load("D:\\1.xlsx");
        List<String[]> multiColContextByRange = excelHelper.getMultiColContextByRange(3,4);
        for(String[] context : multiColContextByRange) {
            for(int i = 0; i < context.length; i++) {
                System.out.println(context[i]);
            }
            System.out.println(context.length);
        }
        excelHelper.close();
    }
    
    @Test//获取多行内容
    public void test5() throws Exception{
        ExcelModifyHelper excelHelper = (ExcelModifyHelper)context.getBean("excelModifyHelper");
        excelHelper.load("D:\\1.xls");
        excelHelper.setCurrentSheet(0);
        List<String[]> multiColContextByRange = excelHelper.getMultiRowContextByRange(3,4);
        for(String[] context : multiColContextByRange) {
            for(int i = 0; i < context.length; i++) {
                System.out.println(context[i]);
            }
            System.out.println(context.length);
        }
        excelHelper.close();
    }
    
    @Test//获取矩形内容
    public void test6() throws Exception{
        ExcelModifyHelper excelHelper = (ExcelModifyHelper)context.getBean("excelModifyHelper");
        excelHelper.load("D:\\1.xlsx");
        List<String[]> multiColContextByRange = excelHelper.getContextByRectangleRange(new RectangleRange(1,2,3,4));
        for(String[] context : multiColContextByRange) {
            for(int i = 0; i < context.length; i++) {
                System.out.println(context[i]);
            }
            System.out.println(context.length);
        }
        excelHelper.close();
    }
    
    @Test//添加一个sheet
    public void test7() throws Exception{
        ExcelModifyHelper excelHelper = (ExcelModifyHelper)context.getBean("excelModifyHelper");
        excelHelper.load("D:\\1.xlsx");
        excelHelper.createSheet("你好");
        excelHelper.write();
        excelHelper.close();
    }
    
    @Test//修改一个单元格
    public void test8() throws Exception{
        ExcelModifyHelper excelHelper = (ExcelModifyHelper)context.getBean("excelModifyHelper");
        excelHelper.load("D:\\1.xlsx");
        System.out.print(excelHelper.getCellContext(0, 0));
        excelHelper.modifyCell(6, 5, "1122");
        excelHelper.write();
        excelHelper.close();
    }
    
    @Test//修改一列数据
    public void test9() throws Exception{
        ExcelModifyHelper excelHelper = (ExcelModifyHelper)context.getBean("excelModifyHelper");
        excelHelper.load("D:\\2.xlsx");
        excelHelper.modifyCol(1, new String[]{"111","谁谁谁","adasdPP岛"});
        excelHelper.write();
        excelHelper.close();
    }
    
    @Test//修改一列数据
    public void test10() throws Exception{
        ExcelModifyHelper excelHelper = (ExcelModifyHelper)context.getBean("excelModifyHelper");
        excelHelper.load("D:\\2.xls");
        excelHelper.modifyCol(2, 3, new String[]{"111","谁谁谁","adasdPP岛"});
        excelHelper.write();
        excelHelper.close();
    }
    
    @Test//修改一行数据
    public void test11() throws Exception{
        ExcelModifyHelper excelHelper = (ExcelModifyHelper)context.getBean("excelModifyHelper");
        excelHelper.load("D:\\2.xls");
        excelHelper.modifyRow(1, new String[]{"111", "谁谁谁", "adasdPP岛"});
        excelHelper.write();
        excelHelper.close();
    }
    
    @Test//修改一行数据(从指定位置开始)
    public void test12() throws Exception{
        ExcelModifyHelper excelHelper = (ExcelModifyHelper)context.getBean("excelModifyHelper");
        excelHelper.load("D:\\2.xls");
        excelHelper.modifyRow(6, 1, new String[]{"111", "谁谁谁", "adasdPP岛"});
        excelHelper.write();
        excelHelper.close();
    }
    
    @Test//创建一个Excel
    public void test13() throws Exception{
        ExcelCreateHelper excelHelper = (ExcelCreateHelper)context.getBean("excelCreateHelper");
        excelHelper.create("D:\\3.xlsx", new String[]{"你好", "你好2"});
        excelHelper.modifyRow(6, new String[]{"111", "谁谁谁", "adasdPP岛"});
        excelHelper.write();
    }
    
    @Test//设置样式   先设置内容
    public void test14() throws Exception{
        ExcelModifyHelper excelHelper = (ExcelModifyHelper)context.getBean("excelModifyHelper");
        excelHelper.load("D:\\test11.xlsx");
        excelHelper.modifyCol(2, 1, new String[]{"111", "谁谁谁", "adasdPP岛"});
        excelHelper.setStyle(excelHelper.getCellByRectangleRange(new RectangleRange(1, 2, 3, 4)), 1);
        excelHelper.write();
    }
    
    @Test//合并单元格
    public void test15() throws Exception{
        ExcelModifyHelper excelHelper = (ExcelModifyHelper)context.getBean("excelModifyHelper");
        excelHelper.load("D:\\test11.xlsx");
        excelHelper.mergeCells(new RectangleRange(1, 2, 3, 4));
        excelHelper.write();
    }
    
    @Test//创建邮件日报所需的附件
    public void test16() throws Exception{
        StarEmailExcelAttach excelHelper = (StarEmailExcelAttach)context.getBean("starEmailExcelAttach");
        excelHelper.getAttchFiles("20190114");
    }
}