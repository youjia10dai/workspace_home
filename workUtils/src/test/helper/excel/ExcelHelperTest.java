/*
 * Copyright (c) 2019  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
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
 * @description ������һ�仰��������������
 * @author ������
 * @date 2019-01-31 
 */
public class ExcelHelperTest {
    public static ApplicationContext context =null;
    static{       
         context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }
    
    @Test//��ȡ��Ԫ������
    public void test1() throws Exception{
        ExcelModifyHelper excelHelper = (ExcelModifyHelper)context.getBean("excelModifyHelper");
        excelHelper.load("D:\\�Ʒ��û�.xlsx");
        System.out.println(excelHelper.getCellContext(5, 5)); //���Ի�ȡһ����Ԫ��
        excelHelper.close();
    }
    
    @Test//��ȡһ������
    public void test2() throws Exception{
        ExcelModifyHelper excelHelper = (ExcelModifyHelper)context.getBean("excelModifyHelper");
        excelHelper.load("D:\\1.xls");
//        System.out.println(excelHelper.getCellContext(47, 1)); //���Ի�ȡһ����Ԫ��
        String[] context = excelHelper.getColContextByIndex(5);//���Ի�ȡһ��
        System.out.println(context.length);
        for(int i = 0; i < context.length; i++) {
            System.out.println("���:" + context[i]);
        }
        excelHelper.close();
    }
    
    @Test//��ȡһ������
    public void test3() throws Exception{
        ExcelModifyHelper excelHelper = (ExcelModifyHelper)context.getBean("excelModifyHelper");
        excelHelper.load("D:\\1.xls");
//        System.out.println(excelHelper.getCellContext(47, 1)); //���Ի�ȡһ����Ԫ��
//        String[] context = excelHelper.getColContextByIndex(3);//���Ի�ȡһ��
        String[] context = excelHelper.getRowContextByIndex(3);//���Ի�ȡһ��
        System.out.println(context.length);
        for(int i = 0; i < context.length; i++) {
            System.out.println(context[i]);
        }
        excelHelper.close();
    }
    
    @Test//��ȡ��������
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
    
    @Test//��ȡ��������
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
    
    @Test//��ȡ��������
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
    
    @Test//���һ��sheet
    public void test7() throws Exception{
        ExcelModifyHelper excelHelper = (ExcelModifyHelper)context.getBean("excelModifyHelper");
        excelHelper.load("D:\\1.xlsx");
        excelHelper.createSheet("���");
        excelHelper.write();
        excelHelper.close();
    }
    
    @Test//�޸�һ����Ԫ��
    public void test8() throws Exception{
        ExcelModifyHelper excelHelper = (ExcelModifyHelper)context.getBean("excelModifyHelper");
        excelHelper.load("D:\\1.xlsx");
        System.out.print(excelHelper.getCellContext(0, 0));
        excelHelper.modifyCell(6, 5, "1122");
        excelHelper.write();
        excelHelper.close();
    }
    
    @Test//�޸�һ������
    public void test9() throws Exception{
        ExcelModifyHelper excelHelper = (ExcelModifyHelper)context.getBean("excelModifyHelper");
        excelHelper.load("D:\\2.xlsx");
        excelHelper.modifyCol(1, new String[]{"111","˭˭˭","adasdPP��"});
        excelHelper.write();
        excelHelper.close();
    }
    
    @Test//�޸�һ������
    public void test10() throws Exception{
        ExcelModifyHelper excelHelper = (ExcelModifyHelper)context.getBean("excelModifyHelper");
        excelHelper.load("D:\\2.xls");
        excelHelper.modifyCol(2, 3, new String[]{"111","˭˭˭","adasdPP��"});
        excelHelper.write();
        excelHelper.close();
    }
    
    @Test//�޸�һ������
    public void test11() throws Exception{
        ExcelModifyHelper excelHelper = (ExcelModifyHelper)context.getBean("excelModifyHelper");
        excelHelper.load("D:\\2.xls");
        excelHelper.modifyRow(1, new String[]{"111", "˭˭˭", "adasdPP��"});
        excelHelper.write();
        excelHelper.close();
    }
    
    @Test//�޸�һ������(��ָ��λ�ÿ�ʼ)
    public void test12() throws Exception{
        ExcelModifyHelper excelHelper = (ExcelModifyHelper)context.getBean("excelModifyHelper");
        excelHelper.load("D:\\2.xls");
        excelHelper.modifyRow(6, 1, new String[]{"111", "˭˭˭", "adasdPP��"});
        excelHelper.write();
        excelHelper.close();
    }
    
    @Test//����һ��Excel
    public void test13() throws Exception{
        ExcelCreateHelper excelHelper = (ExcelCreateHelper)context.getBean("excelCreateHelper");
        excelHelper.create("D:\\3.xlsx", new String[]{"���", "���2"});
        excelHelper.modifyRow(6, new String[]{"111", "˭˭˭", "adasdPP��"});
        excelHelper.write();
    }
    
    @Test//������ʽ   ����������
    public void test14() throws Exception{
        ExcelModifyHelper excelHelper = (ExcelModifyHelper)context.getBean("excelModifyHelper");
        excelHelper.load("D:\\test11.xlsx");
        excelHelper.modifyCol(2, 1, new String[]{"111", "˭˭˭", "adasdPP��"});
        excelHelper.setStyle(excelHelper.getCellByRectangleRange(new RectangleRange(1, 2, 3, 4)), 1);
        excelHelper.write();
    }
    
    @Test//�ϲ���Ԫ��
    public void test15() throws Exception{
        ExcelModifyHelper excelHelper = (ExcelModifyHelper)context.getBean("excelModifyHelper");
        excelHelper.load("D:\\test11.xlsx");
        excelHelper.mergeCells(new RectangleRange(1, 2, 3, 4));
        excelHelper.write();
    }
    
    @Test//�����ʼ��ձ�����ĸ���
    public void test16() throws Exception{
        StarEmailExcelAttach excelHelper = (StarEmailExcelAttach)context.getBean("starEmailExcelAttach");
        excelHelper.getAttchFiles("20190114");
    }
}