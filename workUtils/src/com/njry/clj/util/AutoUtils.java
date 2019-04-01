/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package com.njry.clj.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.app.njry.email.StartEmail;
import main.app.njry.excel.ExcelUtils;
import main.app.njry.export.ExportHelper;
import main.app.njry.procedure.detailtable.ProcedureDetailTableHelper;
import main.app.njry.procedure.item.CreateProcedureItemHelper;
import main.app.njry.procedure.source.ProcedureSourceHelper;
import main.app.njry.procedure.source.entity.FullTableName;
import main.app.njry.procedure.validate.VaildateHelper;
import main.app.njry.treemenu.ReportCfg;
import main.app.njry.treemenu.TreeMenuHelper;
import main.helper.database.DBHelper;
import main.helper.database.sql.SQLHlper;
import main.helper.email.EmailHelper;
import main.helper.parse.JSPHelper;
import main.old.app.njry.email.DailyEmailHelper;
import main.old.helper.excel.ExcelStyleHelper;
import main.old.helper.excel.filter.CellValueFilter;
import main.source.database.ColumnInfoDemo;
import main.spring.database.multDataSource.DBContextHolder;
import main.study.spring.annotation.FileListUtils;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/** 
 * @description ִ�й��������·��
 * JUnit
 * @author ������
 * @date 2018-06-27 
 */
public class AutoUtils {

    public static ApplicationContext context =null;
    static{       
         context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }
    
    @Test//���������ļ�������
    public void test1() throws Exception{
        ExcelUtils eu = (ExcelUtils)context.getBean("excelUtils");
        eu.createConfigureTableByExcel(FileListUtils.file3, "t_clj20181017_tmp1");
    }
    
    @Test//����log���
    public void test2(){
        ExcelUtils eu = (ExcelUtils)context.getBean("excelUtils");
        eu.testLog();
    }
    
    @Test//�ֶ���Ϣ
    public void test3() throws Exception{
        ColumnInfoDemo eu = (ColumnInfoDemo)context.getBean("fieldInfo");
        eu.main();
    }
    
    @Test//�Զ�����select���
    public void test4(){
        SQLHlper eu = (SQLHlper)context.getBean("sqlhlper");
        eu.getSelectSqlByTableName("jtb.t_ryclj_3000443_grp@tonjzw3");
    }
    
    @Test//�Զ�����update���
    public void test5(){
        SQLHlper eu = (SQLHlper)context.getBean("sqlhlper");
        eu.getUpdateSqlByTableName("t_crm_pzjtmd");
    }
    
    @Test//��ȡJspҳ���������� 
    public void test6(){
    	JSPHelper eu = (JSPHelper)context.getBean("JSPUtils");
        eu.getRequestParam(JSPHelper.file1);
    }
    
    @Test//��ȡ���б�ͷ  JSPҳ����� 
    public void test7() throws Exception{
    	ExcelUtils eu = (ExcelUtils)context.getBean("excelUtils");
        eu.getTableHead(ExcelUtils.file20);
    }
    
    @Test//��ȡ���б�ͷ  JSPҳ����� 
    public void test8() throws Exception{
    	ExcelUtils eu = (ExcelUtils)context.getBean("excelUtils");
        eu.getMultirowTableHead(ExcelUtils.file21);
    }
    
    @Test//������������ 
    public void test9() throws Exception{
    	ExcelUtils eu = (ExcelUtils)context.getBean("excelUtils");
        eu.getBatchExport(ExcelUtils.file16,1006499);
    }
    
    @Test//����һ���ڵ�  35776  //?type=0 ���������Ӳ���
    public void test10(){
        TreeMenuHelper eu = (TreeMenuHelper)context.getBean("treeMenuHelper");
        eu.addNode("һ��ͨ�����û�����", "report/ywtpzyhlc!reportFrame.jspa");
    }

    @Test//����һ���ڵ�(ָ�����ڵ�)
    public void test13(){
        TreeMenuHelper eu = (TreeMenuHelper)context.getBean("treeMenuHelper");
        eu.addNode("35956","bbbb", "aaa");
    }
    
    @Test//�ƶ�һ���ڵ�(�ƶ����ֵܽڵ��Ա�)
    public void test11(){
        TreeMenuHelper eu = (TreeMenuHelper)context.getBean("treeMenuHelper");
        eu.moveNode("һ��ͨ�����û�����", "��������������");
    }
    
    @Test//���ڵ����Ȩ����Ϣ
    public void test14(){
        TreeMenuHelper eu = (TreeMenuHelper)context.getBean("treeMenuHelper");
        eu.addRoles("һ��ͨ�����û�����", "��������������");
    }
    
    @Test//���ڵ����ɶ�Ӧ��t_crm_report_cfg��¼
    public void test14_1(){
        TreeMenuHelper eu = (TreeMenuHelper)context.getBean("treeMenuHelper");
        ReportCfg report = new ReportCfg("20190304-DM-NJ-103379", "κ���", "NGP_RYCLJ_YWTPZYHLC");
        eu.insertReportConfigByNodeId("36256", report);
    }
    
    @Test//�����ݿ�����ݵ�����excel�ļ�
    public void test15() throws IOException{
        ExcelUtils eu = (ExcelUtils)context.getBean("excelUtils");
        DBHelper db= (DBHelper)context.getBean("DBHelper");
        List<Map<String,Object>> list = db.queryForList("select * from jtb.t_crm_ywhl_area_info@tonjzw6");
        eu.createExcel(ExcelUtils.file14, list, new String[]{"��Ԫ���:area_id","��Ԫ����:area_name"});
    }
    
    @Test//�޸�excel�ļ�,�޸ĵ�����Ԫ��
    public void test16() throws Exception{
        ExcelUtils eu = (ExcelUtils)context.getBean("excelUtils");
        eu.modifyCell(ExcelUtils.file14, 0, 0, "1");
        eu.colse();
    }
    
    @Test//�޸�excel�ļ�,�޸�һ�еİ�����(0��ʼ�޸�str.length�ĸ���)
    public void test17() throws Exception{
        ExcelUtils eu = (ExcelUtils)context.getBean("excelUtils");
        eu.modifyRow(ExcelUtils.file14, 0, new String[]{"���","���"});
        eu.colse();
    }

    @Test//�޸�excel�ļ�,�޸�һ��,���Ǵ�ָ�����±꿪ʼ�޸�
    public void test18() throws Exception{
        ExcelUtils eu = (ExcelUtils)context.getBean("excelUtils");
        eu.modifyRow(ExcelUtils.file14, 1, 10, new String[]{"���"});
        eu.colse();
    }

    @Test//�����ݿ�����ݵ�����excel�ļ�
    public void test19() throws IOException{
        ExcelUtils eu = (ExcelUtils)context.getBean("excelUtils");
        ExcelStyleHelper esh = new ExcelStyleHelper(new String[]{"20::1","0:0:1"}, new String[]{":1:1","0:0:1"}, new CellValueFilter("����"));
        /*
         * "0::1"  ����һ��
         * ":0:1"  ����һ��
         * "0:0:1" ����һ����Ԫ��
         * "0-1:0:1" ���ö���
         * "1-a:0:1" ���ö���(����)
         * ":0-3:1" ���ö���   �����ö��л����ʱ,��һ��ֻҪ����-������
         */
        DBHelper db= (DBHelper)context.getBean("DBHelper");
        List<Map<String,Object>> list = db.queryForList("select * from t_crm_ywhl_area_info");
        eu.createExcel(ExcelUtils.file14, list, new String[]{"��Ԫ���:area_id","��Ԫ����:area_name"},esh);
    }

    @Test//�Զ�������֤����,��������֤����
    public void test20() throws IOException{
        VaildateHelper eu = (VaildateHelper)context.getBean("vaildatePro");
        eu.destroy();
    }
    
    @Test//��֤sql����ܷ�ִ��(�������ɾ��,û����)
    public void test21() throws Exception{
        DBHelper db= (DBHelper)context.getBean("DBHelper");
        List<Map<String,Object>> list = db.queryForList("select node_id, exp_sql  from clj_20180816_tmp5 where exp_sql is not null");
        String node_id= "";
        String exp_sql= "";
        List<String> listRigth = new ArrayList<String>();
        List<String> errorRigth = new ArrayList<String>();
        for(Map<String, Object> map : list) {
            node_id=map.get("node_id").toString();
            exp_sql=map.get("exp_sql").toString();
            try{
                db.queryForList("select * from ("+exp_sql+") where rownum=1");
                listRigth.add(node_id);
            }catch(Exception e){
                errorRigth.add(node_id);
            }
            System.out.println("��ȷ����"+listRigth.size()+" ��������"+  errorRigth.size());
        }
        for(String string : errorRigth) {
            System.out.print(string+",");
        }
    }
    
    @Test//���һ��������
    public void test22() throws Exception{
        CreateProcedureItemHelper itemUtils = (CreateProcedureItemHelper)context.getBean("createProItem");
        itemUtils.addProItem("������-�籣����ר����͸","NGP_RYCLJ_SBJTZXST");
    }

    @Test//��ȡ���������Ϣ(����Դ��,�͹���������)
    public void test23() throws Exception{
        String cnt_Item = "3000495";
        ProcedureSourceHelper proInfo = (ProcedureSourceHelper)context.getBean("proInfo");
        //��ȡ����Դ��
        List<String> source = proInfo.getProSourceByCntItem(cnt_Item);
        proInfo.getDeleteStatement(source);  // �Զ����ɵ�ɾ�����
//        proInfo.getVariableInfo(source);      �Զ����ɹ��̱�����Ϣ
        //��������Դ��,��ȡ����,�Ѿ�������������ȥ����
        Set<FullTableName> allTableNames = proInfo.getAllTableNames(source);
        Set<FullTableName> tableNames = proInfo.getDependItemCntByTabName(allTableNames);
        for(FullTableName tableName : tableNames) {
            System.out.println(tableName);
        }
    }

    @Test//���ݹ���Դ�����ɾ�̬SQL
    public void test23_1() throws Exception{
        ProcedureSourceHelper proInfo = (ProcedureSourceHelper)context.getBean("proInfo");
        proInfo.getProcedureExecStatement("3000505");
    }

    @Test//ֻ��ȡ��̬SQL(���ɵĶ�̬����������,�ֶ�������һ��)
    public void test23_2() throws Exception{
        ProcedureSourceHelper proInfo = (ProcedureSourceHelper)context.getBean("proInfo");
        proInfo.printStaticSql("3000365");
    }

    @Test//��̬�л�����Դ
    public void test24() throws Exception{
        DBContextHolder.setDBType(DBContextHolder.zw3);
        DBHelper db= (DBHelper)context.getBean("DBHelper");
        System.out.println(db.queryForInt("select count(1) from t_user"));
    }
 
    @Test//�Զ����ɻ��ܱ�
    public void test26() throws Exception{
        ProcedureDetailTableHelper proInfo = (ProcedureDetailTableHelper)context.getBean("proDetail");
        proInfo.createDetailTables();
    }
    
    @Test//�����ʼ�����
    public void test27() throws Exception{
        EmailHelper email = (EmailHelper)context.getBean("emailHelper");
        email.send("test", "<h1>wocome</h1>", 
                new String[]{"D:\\�ҵļ����\\ͬ���ļ�\\search\\9.��Ŀ\\����������������\\exceģ��\\һ��ͨ�û�Ƿ��Ԥ��.xls",
                             "D:\\�ҵļ����\\ͬ���ļ�\\search\\9.��Ŀ\\�����ͷ����\\һ��ͨ�û�\\һ��ͨ�û��������ͷֲ�.xls"});
    }
    
    @Test//�����ʼ�HTML����
    public void test28() throws Exception{
        //δ��װ
        DailyEmailHelper dailyEmail = (DailyEmailHelper)context.getBean("dailyEmailHelper");
        dailyEmail.send("20190124");
    }
    
    @Test//�����ʼ�HTML����
    public void test29() throws Exception{
        StartEmail startMail = (StartEmail)context.getBean("startEmail");
        System.out.println(startMail.getContext("20190114","dsd"));
    }

    @Test//���ݿ����ݵ�����excel(��������)
    public void test30() throws Exception{
        ExportHelper exportHelper = (ExportHelper)context.getBean("exportHelper");
        exportHelper.exportByTableName("clj_20190221_tmp7");
    }
}