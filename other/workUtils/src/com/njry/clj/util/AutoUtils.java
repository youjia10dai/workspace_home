/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
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
 * @description 执行工具类的总路口
 * JUnit
 * @author 陈吕奖
 * @date 2018-06-27 
 */
public class AutoUtils {

    public static ApplicationContext context =null;
    static{       
         context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }
    
    @Test//根据配置文件创建表
    public void test1() throws Exception{
        ExcelUtils eu = (ExcelUtils)context.getBean("excelUtils");
        eu.createConfigureTableByExcel(FileListUtils.file3, "t_clj20181017_tmp1");
    }
    
    @Test//测试log组件
    public void test2(){
        ExcelUtils eu = (ExcelUtils)context.getBean("excelUtils");
        eu.testLog();
    }
    
    @Test//字段信息
    public void test3() throws Exception{
        ColumnInfoDemo eu = (ColumnInfoDemo)context.getBean("fieldInfo");
        eu.main();
    }
    
    @Test//自动生成select语句
    public void test4(){
        SQLHlper eu = (SQLHlper)context.getBean("sqlhlper");
        eu.getSelectSqlByTableName("jtb.t_ryclj_3000443_grp@tonjzw3");
    }
    
    @Test//自动生成update语句
    public void test5(){
        SQLHlper eu = (SQLHlper)context.getBean("sqlhlper");
        eu.getUpdateSqlByTableName("t_crm_pzjtmd");
    }
    
    @Test//获取Jsp页面的请求参数 
    public void test6(){
    	JSPHelper eu = (JSPHelper)context.getBean("JSPUtils");
        eu.getRequestParam(JSPHelper.file1);
    }
    
    @Test//获取单行表头  JSP页面代码 
    public void test7() throws Exception{
    	ExcelUtils eu = (ExcelUtils)context.getBean("excelUtils");
        eu.getTableHead(ExcelUtils.file20);
    }
    
    @Test//获取多行表头  JSP页面代码 
    public void test8() throws Exception{
    	ExcelUtils eu = (ExcelUtils)context.getBean("excelUtils");
        eu.getMultirowTableHead(ExcelUtils.file21);
    }
    
    @Test//批量导出功能 
    public void test9() throws Exception{
    	ExcelUtils eu = (ExcelUtils)context.getBean("excelUtils");
        eu.getBatchExport(ExcelUtils.file16,1006499);
    }
    
    @Test//插入一个节点  35776  //?type=0 这种条件加不了
    public void test10(){
        TreeMenuHelper eu = (TreeMenuHelper)context.getBean("treeMenuHelper");
        eu.addNode("一网通拍照用户留存", "report/ywtpzyhlc!reportFrame.jspa");
    }

    @Test//插入一个节点(指定父节点)
    public void test13(){
        TreeMenuHelper eu = (TreeMenuHelper)context.getBean("treeMenuHelper");
        eu.addNode("35956","bbbb", "aaa");
    }
    
    @Test//移动一个节点(移动到兄弟节点旁边)
    public void test11(){
        TreeMenuHelper eu = (TreeMenuHelper)context.getBean("treeMenuHelper");
        eu.moveNode("一网通拍照用户留存", "政企宽带净增报表");
    }
    
    @Test//给节点添加权限信息
    public void test14(){
        TreeMenuHelper eu = (TreeMenuHelper)context.getBean("treeMenuHelper");
        eu.addRoles("一网通拍照用户留存", "政企宽带净增报表");
    }
    
    @Test//给节点生成对应的t_crm_report_cfg记录
    public void test14_1(){
        TreeMenuHelper eu = (TreeMenuHelper)context.getBean("treeMenuHelper");
        ReportCfg report = new ReportCfg("20190304-DM-NJ-103379", "魏蕴婕", "NGP_RYCLJ_YWTPZYHLC");
        eu.insertReportConfigByNodeId("36256", report);
    }
    
    @Test//将数据库的数据导出成excel文件
    public void test15() throws IOException{
        ExcelUtils eu = (ExcelUtils)context.getBean("excelUtils");
        DBHelper db= (DBHelper)context.getBean("DBHelper");
        List<Map<String,Object>> list = db.queryForList("select * from jtb.t_crm_ywhl_area_info@tonjzw6");
        eu.createExcel(ExcelUtils.file14, list, new String[]{"单元编号:area_id","单元名称:area_name"});
    }
    
    @Test//修改excel文件,修改单个单元格
    public void test16() throws Exception{
        ExcelUtils eu = (ExcelUtils)context.getBean("excelUtils");
        eu.modifyCell(ExcelUtils.file14, 0, 0, "1");
        eu.colse();
    }
    
    @Test//修改excel文件,修改一行的把内容(0开始修改str.length的个数)
    public void test17() throws Exception{
        ExcelUtils eu = (ExcelUtils)context.getBean("excelUtils");
        eu.modifyRow(ExcelUtils.file14, 0, new String[]{"你好","你好"});
        eu.colse();
    }

    @Test//修改excel文件,修改一行,但是从指定的下标开始修改
    public void test18() throws Exception{
        ExcelUtils eu = (ExcelUtils)context.getBean("excelUtils");
        eu.modifyRow(ExcelUtils.file14, 1, 10, new String[]{"你好"});
        eu.colse();
    }

    @Test//将数据库的数据导出成excel文件
    public void test19() throws IOException{
        ExcelUtils eu = (ExcelUtils)context.getBean("excelUtils");
        ExcelStyleHelper esh = new ExcelStyleHelper(new String[]{"20::1","0:0:1"}, new String[]{":1:1","0:0:1"}, new CellValueFilter("中心"));
        /*
         * "0::1"  设置一行
         * ":0:1"  设置一列
         * "0:0:1" 设置一个单元格
         * "0-1:0:1" 设置多行
         * "1-a:0:1" 设置多行(到底)
         * ":0-3:1" 设置多列   在设置多行或多列时,另一个只要不带-就行了
         */
        DBHelper db= (DBHelper)context.getBean("DBHelper");
        List<Map<String,Object>> list = db.queryForList("select * from t_crm_ywhl_area_info");
        eu.createExcel(ExcelUtils.file14, list, new String[]{"单元编号:area_id","单元名称:area_name"},esh);
    }

    @Test//自动生成验证过程,并生成验证数据
    public void test20() throws IOException{
        VaildateHelper eu = (VaildateHelper)context.getBean("vaildatePro");
        eu.destroy();
    }
    
    @Test//验证sql语句能否执行(这个可以删了,没有用)
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
            System.out.println("正确数量"+listRigth.size()+" 错误数量"+  errorRigth.size());
        }
        for(String string : errorRigth) {
            System.out.print(string+",");
        }
    }
    
    @Test//添加一个过程项
    public void test22() throws Exception{
        CreateProcedureItemHelper itemUtils = (CreateProcedureItemHelper)context.getBean("createProItem");
        itemUtils.addProItem("陈吕奖-社保集团专线渗透","NGP_RYCLJ_SBJTZXST");
    }

    @Test//获取过程相关信息(过程源码,和过程依赖项)
    public void test23() throws Exception{
        String cnt_Item = "3000495";
        ProcedureSourceHelper proInfo = (ProcedureSourceHelper)context.getBean("proInfo");
        //获取过程源码
        List<String> source = proInfo.getProSourceByCntItem(cnt_Item);
        proInfo.getDeleteStatement(source);  // 自动生成的删表语句
//        proInfo.getVariableInfo(source);      自动生成过程变量信息
        //解析过程源码,获取表名,已经根据三个属性去过重
        Set<FullTableName> allTableNames = proInfo.getAllTableNames(source);
        Set<FullTableName> tableNames = proInfo.getDependItemCntByTabName(allTableNames);
        for(FullTableName tableName : tableNames) {
            System.out.println(tableName);
        }
    }

    @Test//根据过程源码生成静态SQL
    public void test23_1() throws Exception{
        ProcedureSourceHelper proInfo = (ProcedureSourceHelper)context.getBean("proInfo");
        proInfo.getProcedureExecStatement("3000505");
    }

    @Test//只获取静态SQL(生成的动态过程有问题,手动调整了一下)
    public void test23_2() throws Exception{
        ProcedureSourceHelper proInfo = (ProcedureSourceHelper)context.getBean("proInfo");
        proInfo.printStaticSql("3000365");
    }

    @Test//动态切换数据源
    public void test24() throws Exception{
        DBContextHolder.setDBType(DBContextHolder.zw3);
        DBHelper db= (DBHelper)context.getBean("DBHelper");
        System.out.println(db.queryForInt("select count(1) from t_user"));
    }
 
    @Test//自动生成汇总表
    public void test26() throws Exception{
        ProcedureDetailTableHelper proInfo = (ProcedureDetailTableHelper)context.getBean("proDetail");
        proInfo.createDetailTables();
    }
    
    @Test//测试邮件发送
    public void test27() throws Exception{
        EmailHelper email = (EmailHelper)context.getBean("emailHelper");
        email.send("test", "<h1>wocome</h1>", 
                new String[]{"D:\\我的坚果云\\同步文件\\search\\9.项目\\增加批量导出功能\\exce模板\\一网通用户欠费预测.xls",
                             "D:\\我的坚果云\\同步文件\\search\\9.项目\\报表表头生成\\一网通用户\\一网通用户接入类型分布.xls"});
    }
    
    @Test//生成邮件HTML内容
    public void test28() throws Exception{
        //未封装
        DailyEmailHelper dailyEmail = (DailyEmailHelper)context.getBean("dailyEmailHelper");
        dailyEmail.send("20190124");
    }
    
    @Test//生成邮件HTML内容
    public void test29() throws Exception{
        StartEmail startMail = (StartEmail)context.getBean("startEmail");
        System.out.println(startMail.getContext("20190114","dsd"));
    }

    @Test//数据库数据导出到excel(大量数据)
    public void test30() throws Exception{
        ExportHelper exportHelper = (ExportHelper)context.getBean("exportHelper");
        exportHelper.exportByTableName("clj_20190221_tmp7");
    }
}