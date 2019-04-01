/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */
package main.app.njry.excel;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import main.helper.BaseHelper;
import main.helper.database.sql.BatchSql;
import main.helper.database.sql.SQLHlper;
import main.old.helper.excel.ExcelHelper;
import main.old.helper.excel.ExcelStyleHelper;
import main.spring.database.aspect.DataSource;
import main.spring.database.multDataSource.DBContextHolder;
import main.utils.common.StringUtils;
import org.springframework.stereotype.Component;


/**
 * @description 记载Excel文件,自动生成语句
 * @author 陈吕奖
 * @date 2018-06-27 实现以下的功能 1.生成 单行表头 JSP页面显示内容 2.生成 多行表头
 *       JSP页面显示内容(和功能1相比多加个字段注释) 3.根据excel创建配置表,并插入数据 4.生成批量导出的插入数据
 */
@Component("excelUtils")
public class ExcelUtils extends BaseHelper {
    //演示功能4   sheet为2
    public static File file2 = new File("C:\\Users\\Administrator\\Desktop\\报表excel文件\\2个审核流程需求\\2个审核流程需求.xls");
    //演示功能3   sheet为2
    public static File file1 = new File("C:\\Users\\Administrator\\Desktop\\报表excel文件\\政协转网营销指数\\clj副本营销指数配置表1.xls");
    //演示功能1
    public static File file5 = new File("D:\\批量导出演示.xls");
    //演示功能2
    public static File file6 = new File("C:\\Users\\Administrator\\Desktop\\报表excel文件\\集团市场4G\\CRM-集团市场4G+手机渗透日报.xls");//演示功能2
    //演示功能3
    public static File file3 = new File(
            "C:\\Users\\Administrator\\Desktop\\报表excel文件\\拍照集团摸底录入表20180607 new\\拍照集团建档明细20180627-业支.xls");
    public static File file7 = new File("D:\\我的坚果云\\同步文件\\工作内容\\拍照集团专线渗透日报\\拍照集团专线渗透率报表.xls");
    public static File file8 = new File("D:\\我的坚果云\\同步文件\\工作内容\\一号工程\\局级干部配置表3.xls");
    public static File file9 = new File("D:\\我的坚果云\\同步文件\\工作内容\\一号工程\\政协委员营销指数配置表V3.xls");
    public static File file10 = new File("D:\\1号工程转网需求.xls");
    public static File file11 = new File("D:\\导入数据演示.xls");
    public static File file12 = new File("D:\\2.xls");
    //演示导入数据
    public static File file13 = new File("D:\\集团.xls");
    public static File file14 = new File("D:\\test.xls");
    public static File file15 = new File("D:\\我的坚果云\\同步文件\\workspace\\excelInfo\\协销和行商\\新入网明细.xls");
    public static File file16 = new File("D:\\我的坚果云\\同步文件\\search\\9.项目\\增加批量导出功能\\exce模板\\拍照用户留存.xls");
    public static File file17 = new File("D:\\1.xls");
    public static File file18 = new File("D:\\我的坚果云\\同步文件\\workspace\\excelInfo\\新聚类小区明细\\新聚类小区明细.xls");
    public static File file19 = new File("D:\\我的坚果云\\同步文件\\search\\9.项目\\报表表头生成\\一网通用户\\一网通用户接入类型分布.xls");
    public static File file20 = new File("D:\\我的坚果云\\同步文件\\search\\9.项目\\报表表头生成\\中小学互联网专线渗透\\1.xls");
    public static File file21 = new File("D:\\我的坚果云\\同步文件\\search\\9.项目\\报表表头生成\\一网通用户宽带分布日报\\1.xls");
    /** 
     * @fields emh 用于统一的关闭资源
     * 因为修改操作涉及到多次,不可能修改一次就关闭资源(再修改就再加载资源)
     * 
     */ 
    public ExcelHelper emh;
    
    /**
     * @description 生成批量导出的插入语句
     * @author 陈吕奖 2018-06-27
     * @param file
     * @param node_id
     * @throws Exception 
     */
    public void getBatchExport(File file, int node_id) throws Exception {
        ExcelHelper eh = new ExcelHelper(file);
        String[] context = eh.getRowContextByIndex(0);
        String[] context2 = eh.getRowContextByIndex(1);
        for(int i = 0; i < eh.getCols(); i++) {
            System.out.println("insert into t_export_plan_tab "
                    + "(tab_id, col_name,col_title,orderid,status ,col_width)" + "values (" + node_id + ",'"
                    + context2[i] + "','" + context[i] + "'," + (i + 1) + ",1,20);");
        }
    }

    /**
     * @description 根据excel创建配置表,并插入数据 第一行解释名称 第二行字段名称 第三行字段类型
     * 目前如果数据量过大,可以执行但是并没有使用批处理(所以效率不高)
     * @author 陈吕奖 2018-06-05
     * @throws Exception 
     */
    @DataSource(name = DBContextHolder.zw3)
    public void createConfigureTableByExcel(File file, String tableName) throws Exception {
        ExcelHelper eh = new ExcelHelper(file);
        BatchSql batch = new BatchSql();
        int max = eh.getRows();
        //插入excel表中数据
        String[] combStr = StringUtils.combString(eh.getRowContextByIndex(0), eh.getRowContextByIndex(1), eh
                .getRowContextByIndex(2));
        //生成建表语句
        SQLHlper.createTable(tableName, combStr, batch);

        List<String[]> list = null;

        //生成输入插入语句
        if(max > 10005) {
            int i = 3;
            for(int j = 10003; j < max; i = j + 1, j = i + 10000) {
                list = eh.getRowByRange(i, j);
                SQLHlper.createInsert(list, batch);
                db.doInTransaction(batch);
            }
            if(i < max) {
                list = eh.getRowByRange(i, max - 1);
                SQLHlper.createInsert(list, batch);
                db.doInTransaction(batch);
            }
        } else {
            list = eh.getRowByRange(3);
            SQLHlper.createInsert(list, batch);
            db.doInTransaction(batch);
        }
        //在控制台显示SQL语句
        //batch.showSql();
        //将SQL语句输出到文件中
        //batch.outPutToFile("C:\\Users\\Administrator\\Desktop\\报表excel文件\\临时跑数据\\1.txt");
    }

    /**
     * @description 获取excel表头信息(读取excel文件)生成JSP页面表头信息 单行表头
     * @author 陈吕奖 2018-05-31 生成的数据为
     *         <td>同事网集团数</td>
     *         <td>同事网用户数</td>
     *         tableName： 输入表名,将生成字段的注释
     * @throws Exception 
     */
    public void getTableHead(File file, String... tableName) throws Exception {
        //<td>统计月份</td>
        ExcelHelper eh = new ExcelHelper(file);
        String[] cells = eh.getRowContextByIndex(0);//获取一行的数据
        for(String string : cells) {
            System.out.println("<td>" + string + "</td>");
        }
        //<display:column title="统计月份"  property="TJ_MON"/>
        String templet = "<display:column title=\"?0\"  property=\"?1\"/>";
        String[] contexts = new String[eh.getCols()];
        for(int i = 0; i < contexts.length; i++) {
            contexts[i] = templet;
        }
        String[] cells1 = eh.getRowContextByIndex(0);//获取一行的数据
        String[] cells2 = eh.getRowContextByIndex(1);//获取二行的数据
        for(int j = 0; j < eh.getCols(); j++) {
            contexts[j] = contexts[j].replace("?0", cells1[j]);
            contexts[j] = contexts[j].replace("?1", cells2[j]);
            if(tableName.length > 0) {
                System.out.println("comment on column " + tableName[0] + "." + cells2[j] + " is '" + cells1[j] + "';");
            }
        }
        for(int i = 0; i < contexts.length; i++) {
            System.out.println(contexts[i]);
        }
    }

    /**
     * @description 获取Excel表头信息(读取excel文件) 生成JSP页面  多行表头 判断是否占两行：看第二行是否为空 判断占几列 : 看右边单元格是否为空
     *              other : 表名,如果有将生成表的字段注释
     * @author 陈吕奖 2018-05-31
     * @param sheet
     * @throws Exception 
     */
    public void getMultirowTableHead(File file, String... other) throws Exception {
        ExcelHelper eh = new ExcelHelper(file);
        //基本数据
        int cols = eh.getCols();//获取excel的列数
        String[] cells1 = null;//第一行数据
        String[] cells2 = null;//第二行数据
        String[] cells3 = null;//第三行数据
        int count = 0;//用于统计右边的空白列的数量
        String[] contexts = new String[cols];
        String templet = "<display:column title=\"?0\"  property=\"?1\"/>";
        for(int i = 0; i < contexts.length; i++) {
            contexts[i] = templet;
        }
        //生成第一行数据
        System.out.println("<tr class=\"mobile2\">");
        cells1 = eh.getRowContextByIndex(0);//获取一行的数据
        cells2 = eh.getRowContextByIndex(1);//获取一行的数据  两列一起获取
        for(int j = 0; j < cols; j++) {
            //1.判断下面一行是否为空,生成占两行的列
            if(cells2[j] == "") {
                System.out.println("<td rowspan=\"2\">" + cells1[j] + "</td>");
                continue;
            }
            if(j < cols - 1) {
                if(cells1[j + 1] == "") {
                    //右边单元格为空
                    if(count == 0) {
                        count = 2;
                    } else {
                        count++;
                    }
                } else {
                    //右边单元格不为空
                    if(count >= 2) {
                        //表示占多列
                        System.out.println("<td colspan=\"" + count + "\">" + cells1[j - count + 1] + "</td>");
                        count = 0;
                        continue;
                    } else {
                        //表示占一列
                        System.out.println("<td>" + cells1[j] + "</td>");
                    }
                }
            }
            //处理最后一列
            if(j == cols - 1) {
                if(cells1[j] == "") {
                    System.out.println("<td colspan=\"" + count + "\">" + cells1[j - count + 1] + "</td>");
                } else {
                    //最后一行不是空直接输出
                    System.out.println("<td>" + cells1[j] + "</td>");
                }
            }
        }
        System.out.println("</tr>");
        System.out.println();
        //生成第二行数据
        System.out.println("<tr class=\"mobile2\">");
        cells3 = eh.getRowContextByIndex(2);//获取三行的数据
        for(int j = 0; j < cols; j++) {
            if(cells2[j] == "") {
                System.out.println("<td style=\"display :none\">" + cells1[j] + "</td>");
            } else {
                System.out.println("<td>" + cells2[j] + "</td>");
            }
        }
        System.out.println("</tr>");
        System.out.println();
        //获取第三行数据,第四行数据(第四行为表的注释)
        for(int j = 0; j < cols; j++) {
            if(cells2[j] == "") {
                contexts[j] = contexts[j].replace("?0", cells1[j]);
                contexts[j] = contexts[j].replace("?1", cells3[j]);
                if(other.length > 0) {
                    System.out.println("comment on column " + other[0] + "." + cells3[j] + " is '" + cells1[j] + "';");
                }
            } else {
                contexts[j] = contexts[j].replace("?0", cells2[j]);
                contexts[j] = contexts[j].replace("?1", cells3[j]);
                if(other.length > 0) {
                    System.out.println("comment on column " + other[0] + "." + cells3[j] + " is '" + cells2[j] + "';");
                }
            }
        }
        System.out.println();
        for(int i = 0; i < contexts.length; i++) {
            System.out.println(contexts[i]);
        }
    }

    /** 
     * @description 创建一个excel文件
     * @author 陈吕奖 2018-07-25
     * @param file 输出的文件
     * @param dataList 提供的数据,可以是List<String[]> 也可以是 List<Map>
     * @param title  第一行标题
     * @throws IOException
     */ 
    public void createExcel(File file, List<?> dataList, String[] title) throws IOException {
        ExcelHelper ewh = new ExcelHelper(file, title);
        log.debug(ewh.isWrite);
        if(dataList == null || dataList.size() == 0) {
            log.debug("数据为空,创建excel失败");
            return;
        }
        //isAssignableFrom判断c是否是Map类的子类或父类   c=dataList.get(0).getClass()
        if(Map.class.isAssignableFrom(dataList.get(0).getClass())) {//)
            List<String[]> list = ewh.convert((List<Map<String,Object>>)dataList);
            ewh.createExcel(list);
        }else{
            ewh.createExcel((List<String[]>)dataList);
        }
    }
    
    /** 
     * @description 创建一个excel文件(并设置样式)
     * @author 陈吕奖 2018-07-25
     * @param file 输出的文件
     * @param dataList 提供的数据,可以是List<String[]> 也可以是 List<Map>
     * @param title  第一行标题
     * @throws IOException
     */ 
    public void createExcel(File file, List<?> dataList, String[] title, ExcelStyleHelper esh) throws IOException {
        if(!file.exists()){
            file.createNewFile();
        }
        ExcelHelper ewh = new ExcelHelper(file, title);
        ewh.setExcelStyleHelper(esh);
        if(dataList == null || dataList.size() == 0) {
            log.debug("数据为空,创建excel失败");
            return;
        }
        //isAssignableFrom判断c是否是Map类的子类或父类   c=dataList.get(0).getClass()
        if(Map.class.isAssignableFrom(dataList.get(0).getClass())) {//)
            List<String[]> list = ewh.convert((List<Map<String,Object>>)dataList);
            ewh.createExcel(list);
        }else{
            ewh.createExcel((List<String[]>)dataList);
        }
    }
    
    //三个修改的方法,修改一次就关闭了资源(应该改为可以修改多次,然后统一的关闭资源)
    /**
     * @description 修改一整行的内容
     * @author 陈吕奖 2018-07-25
     * @param file 最终文件
     * @param context 数据
     * @throws Exception
     */
    public void modifyRow(File file, int row, String[] context) throws Exception{
        emh = new ExcelHelper(file);
        emh.modifyRow(row, context);
    }
    
    /** 
     * @description 修改一行的部分连续内容
     * @author 陈吕奖 2018-07-25
     * @param file
     * @param row
     * @param col   从col下标开始,到context的length下标
     * @param context
     * @throws Exception 
     */ 
    public void modifyRow(File file, int row, int col, String[] context) throws Exception{
        emh = new ExcelHelper(file);
        emh.modifyRow(row, col, context);
    }
    
    /** 
     * @description 修改一个指定单元格的内容
     * @author 陈吕奖 2018-07-25
     * @param file
     * @param row   行
     * @param col   列
     * @param title    内容
     * @throws Exception 
     */ 
    public void modifyCell(File file, int row, int col, String context) throws Exception{
        emh = new ExcelHelper(file);
        emh.modifyCell(row, col, context);
    }
    
    public void colse(){
        if(emh!= null){
            try {
                emh.write();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void test(File file) throws Exception{
        ExcelHelper erh = new ExcelHelper(file);        
    }
    
    /** 
     * @description 测试log组件是否
     * @author 陈吕奖 2018-07-30
     */
    public void testLog() {
        log.debug("d");
    }
}
