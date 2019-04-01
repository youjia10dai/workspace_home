/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
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
 * @description ����Excel�ļ�,�Զ��������
 * @author ������
 * @date 2018-06-27 ʵ�����µĹ��� 1.���� ���б�ͷ JSPҳ����ʾ���� 2.���� ���б�ͷ
 *       JSPҳ����ʾ����(�͹���1��ȶ�Ӹ��ֶ�ע��) 3.����excel�������ñ�,���������� 4.�������������Ĳ�������
 */
@Component("excelUtils")
public class ExcelUtils extends BaseHelper {
    //��ʾ����4   sheetΪ2
    public static File file2 = new File("C:\\Users\\Administrator\\Desktop\\����excel�ļ�\\2�������������\\2�������������.xls");
    //��ʾ����3   sheetΪ2
    public static File file1 = new File("C:\\Users\\Administrator\\Desktop\\����excel�ļ�\\��Эת��Ӫ��ָ��\\clj����Ӫ��ָ�����ñ�1.xls");
    //��ʾ����1
    public static File file5 = new File("D:\\����������ʾ.xls");
    //��ʾ����2
    public static File file6 = new File("C:\\Users\\Administrator\\Desktop\\����excel�ļ�\\�����г�4G\\CRM-�����г�4G+�ֻ���͸�ձ�.xls");//��ʾ����2
    //��ʾ����3
    public static File file3 = new File(
            "C:\\Users\\Administrator\\Desktop\\����excel�ļ�\\���ռ�������¼���20180607 new\\���ռ��Ž�����ϸ20180627-ҵ֧.xls");
    public static File file7 = new File("D:\\�ҵļ����\\ͬ���ļ�\\��������\\���ռ���ר����͸�ձ�\\���ռ���ר����͸�ʱ���.xls");
    public static File file8 = new File("D:\\�ҵļ����\\ͬ���ļ�\\��������\\һ�Ź���\\�ּ��ɲ����ñ�3.xls");
    public static File file9 = new File("D:\\�ҵļ����\\ͬ���ļ�\\��������\\һ�Ź���\\��ЭίԱӪ��ָ�����ñ�V3.xls");
    public static File file10 = new File("D:\\1�Ź���ת������.xls");
    public static File file11 = new File("D:\\����������ʾ.xls");
    public static File file12 = new File("D:\\2.xls");
    //��ʾ��������
    public static File file13 = new File("D:\\����.xls");
    public static File file14 = new File("D:\\test.xls");
    public static File file15 = new File("D:\\�ҵļ����\\ͬ���ļ�\\workspace\\excelInfo\\Э��������\\��������ϸ.xls");
    public static File file16 = new File("D:\\�ҵļ����\\ͬ���ļ�\\search\\9.��Ŀ\\����������������\\exceģ��\\�����û�����.xls");
    public static File file17 = new File("D:\\1.xls");
    public static File file18 = new File("D:\\�ҵļ����\\ͬ���ļ�\\workspace\\excelInfo\\�¾���С����ϸ\\�¾���С����ϸ.xls");
    public static File file19 = new File("D:\\�ҵļ����\\ͬ���ļ�\\search\\9.��Ŀ\\�����ͷ����\\һ��ͨ�û�\\һ��ͨ�û��������ͷֲ�.xls");
    public static File file20 = new File("D:\\�ҵļ����\\ͬ���ļ�\\search\\9.��Ŀ\\�����ͷ����\\��Сѧ������ר����͸\\1.xls");
    public static File file21 = new File("D:\\�ҵļ����\\ͬ���ļ�\\search\\9.��Ŀ\\�����ͷ����\\һ��ͨ�û�����ֲ��ձ�\\1.xls");
    /** 
     * @fields emh ����ͳһ�Ĺر���Դ
     * ��Ϊ�޸Ĳ����漰�����,�������޸�һ�ξ͹ر���Դ(���޸ľ��ټ�����Դ)
     * 
     */ 
    public ExcelHelper emh;
    
    /**
     * @description �������������Ĳ������
     * @author ������ 2018-06-27
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
     * @description ����excel�������ñ�,���������� ��һ�н������� �ڶ����ֶ����� �������ֶ�����
     * Ŀǰ�������������,����ִ�е��ǲ�û��ʹ��������(����Ч�ʲ���)
     * @author ������ 2018-06-05
     * @throws Exception 
     */
    @DataSource(name = DBContextHolder.zw3)
    public void createConfigureTableByExcel(File file, String tableName) throws Exception {
        ExcelHelper eh = new ExcelHelper(file);
        BatchSql batch = new BatchSql();
        int max = eh.getRows();
        //����excel��������
        String[] combStr = StringUtils.combString(eh.getRowContextByIndex(0), eh.getRowContextByIndex(1), eh
                .getRowContextByIndex(2));
        //���ɽ������
        SQLHlper.createTable(tableName, combStr, batch);

        List<String[]> list = null;

        //��������������
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
        //�ڿ���̨��ʾSQL���
        //batch.showSql();
        //��SQL���������ļ���
        //batch.outPutToFile("C:\\Users\\Administrator\\Desktop\\����excel�ļ�\\��ʱ������\\1.txt");
    }

    /**
     * @description ��ȡexcel��ͷ��Ϣ(��ȡexcel�ļ�)����JSPҳ���ͷ��Ϣ ���б�ͷ
     * @author ������ 2018-05-31 ���ɵ�����Ϊ
     *         <td>ͬ����������</td>
     *         <td>ͬ�����û���</td>
     *         tableName�� �������,�������ֶε�ע��
     * @throws Exception 
     */
    public void getTableHead(File file, String... tableName) throws Exception {
        //<td>ͳ���·�</td>
        ExcelHelper eh = new ExcelHelper(file);
        String[] cells = eh.getRowContextByIndex(0);//��ȡһ�е�����
        for(String string : cells) {
            System.out.println("<td>" + string + "</td>");
        }
        //<display:column title="ͳ���·�"  property="TJ_MON"/>
        String templet = "<display:column title=\"?0\"  property=\"?1\"/>";
        String[] contexts = new String[eh.getCols()];
        for(int i = 0; i < contexts.length; i++) {
            contexts[i] = templet;
        }
        String[] cells1 = eh.getRowContextByIndex(0);//��ȡһ�е�����
        String[] cells2 = eh.getRowContextByIndex(1);//��ȡ���е�����
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
     * @description ��ȡExcel��ͷ��Ϣ(��ȡexcel�ļ�) ����JSPҳ��  ���б�ͷ �ж��Ƿ�ռ���У����ڶ����Ƿ�Ϊ�� �ж�ռ���� : ���ұߵ�Ԫ���Ƿ�Ϊ��
     *              other : ����,����н����ɱ���ֶ�ע��
     * @author ������ 2018-05-31
     * @param sheet
     * @throws Exception 
     */
    public void getMultirowTableHead(File file, String... other) throws Exception {
        ExcelHelper eh = new ExcelHelper(file);
        //��������
        int cols = eh.getCols();//��ȡexcel������
        String[] cells1 = null;//��һ������
        String[] cells2 = null;//�ڶ�������
        String[] cells3 = null;//����������
        int count = 0;//����ͳ���ұߵĿհ��е�����
        String[] contexts = new String[cols];
        String templet = "<display:column title=\"?0\"  property=\"?1\"/>";
        for(int i = 0; i < contexts.length; i++) {
            contexts[i] = templet;
        }
        //���ɵ�һ������
        System.out.println("<tr class=\"mobile2\">");
        cells1 = eh.getRowContextByIndex(0);//��ȡһ�е�����
        cells2 = eh.getRowContextByIndex(1);//��ȡһ�е�����  ����һ���ȡ
        for(int j = 0; j < cols; j++) {
            //1.�ж�����һ���Ƿ�Ϊ��,����ռ���е���
            if(cells2[j] == "") {
                System.out.println("<td rowspan=\"2\">" + cells1[j] + "</td>");
                continue;
            }
            if(j < cols - 1) {
                if(cells1[j + 1] == "") {
                    //�ұߵ�Ԫ��Ϊ��
                    if(count == 0) {
                        count = 2;
                    } else {
                        count++;
                    }
                } else {
                    //�ұߵ�Ԫ��Ϊ��
                    if(count >= 2) {
                        //��ʾռ����
                        System.out.println("<td colspan=\"" + count + "\">" + cells1[j - count + 1] + "</td>");
                        count = 0;
                        continue;
                    } else {
                        //��ʾռһ��
                        System.out.println("<td>" + cells1[j] + "</td>");
                    }
                }
            }
            //�������һ��
            if(j == cols - 1) {
                if(cells1[j] == "") {
                    System.out.println("<td colspan=\"" + count + "\">" + cells1[j - count + 1] + "</td>");
                } else {
                    //���һ�в��ǿ�ֱ�����
                    System.out.println("<td>" + cells1[j] + "</td>");
                }
            }
        }
        System.out.println("</tr>");
        System.out.println();
        //���ɵڶ�������
        System.out.println("<tr class=\"mobile2\">");
        cells3 = eh.getRowContextByIndex(2);//��ȡ���е�����
        for(int j = 0; j < cols; j++) {
            if(cells2[j] == "") {
                System.out.println("<td style=\"display :none\">" + cells1[j] + "</td>");
            } else {
                System.out.println("<td>" + cells2[j] + "</td>");
            }
        }
        System.out.println("</tr>");
        System.out.println();
        //��ȡ����������,����������(������Ϊ���ע��)
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
     * @description ����һ��excel�ļ�
     * @author ������ 2018-07-25
     * @param file ������ļ�
     * @param dataList �ṩ������,������List<String[]> Ҳ������ List<Map>
     * @param title  ��һ�б���
     * @throws IOException
     */ 
    public void createExcel(File file, List<?> dataList, String[] title) throws IOException {
        ExcelHelper ewh = new ExcelHelper(file, title);
        log.debug(ewh.isWrite);
        if(dataList == null || dataList.size() == 0) {
            log.debug("����Ϊ��,����excelʧ��");
            return;
        }
        //isAssignableFrom�ж�c�Ƿ���Map����������   c=dataList.get(0).getClass()
        if(Map.class.isAssignableFrom(dataList.get(0).getClass())) {//)
            List<String[]> list = ewh.convert((List<Map<String,Object>>)dataList);
            ewh.createExcel(list);
        }else{
            ewh.createExcel((List<String[]>)dataList);
        }
    }
    
    /** 
     * @description ����һ��excel�ļ�(��������ʽ)
     * @author ������ 2018-07-25
     * @param file ������ļ�
     * @param dataList �ṩ������,������List<String[]> Ҳ������ List<Map>
     * @param title  ��һ�б���
     * @throws IOException
     */ 
    public void createExcel(File file, List<?> dataList, String[] title, ExcelStyleHelper esh) throws IOException {
        if(!file.exists()){
            file.createNewFile();
        }
        ExcelHelper ewh = new ExcelHelper(file, title);
        ewh.setExcelStyleHelper(esh);
        if(dataList == null || dataList.size() == 0) {
            log.debug("����Ϊ��,����excelʧ��");
            return;
        }
        //isAssignableFrom�ж�c�Ƿ���Map����������   c=dataList.get(0).getClass()
        if(Map.class.isAssignableFrom(dataList.get(0).getClass())) {//)
            List<String[]> list = ewh.convert((List<Map<String,Object>>)dataList);
            ewh.createExcel(list);
        }else{
            ewh.createExcel((List<String[]>)dataList);
        }
    }
    
    //�����޸ĵķ���,�޸�һ�ξ͹ر�����Դ(Ӧ�ø�Ϊ�����޸Ķ��,Ȼ��ͳһ�Ĺر���Դ)
    /**
     * @description �޸�һ���е�����
     * @author ������ 2018-07-25
     * @param file �����ļ�
     * @param context ����
     * @throws Exception
     */
    public void modifyRow(File file, int row, String[] context) throws Exception{
        emh = new ExcelHelper(file);
        emh.modifyRow(row, context);
    }
    
    /** 
     * @description �޸�һ�еĲ�����������
     * @author ������ 2018-07-25
     * @param file
     * @param row
     * @param col   ��col�±꿪ʼ,��context��length�±�
     * @param context
     * @throws Exception 
     */ 
    public void modifyRow(File file, int row, int col, String[] context) throws Exception{
        emh = new ExcelHelper(file);
        emh.modifyRow(row, col, context);
    }
    
    /** 
     * @description �޸�һ��ָ����Ԫ�������
     * @author ������ 2018-07-25
     * @param file
     * @param row   ��
     * @param col   ��
     * @param title    ����
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
     * @description ����log����Ƿ�
     * @author ������ 2018-07-30
     */
    public void testLog() {
        log.debug("d");
    }
}
