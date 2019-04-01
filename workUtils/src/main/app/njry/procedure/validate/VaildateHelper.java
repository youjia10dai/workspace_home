/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.app.njry.procedure.validate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import main.app.njry.excel.ExcelUtils;
import main.app.njry.procedure.validate.entity.VaildateColumn;
import main.app.njry.procedure.validate.entity.VaildateSql;
import main.helper.BaseHelper;
import main.helper.json.JsonArrayHelper;
import main.helper.json.JsonObjectHelper;
import main.old.helper.excel.ExcelHelper;
import main.study.spring.annotation.FileListUtils;
import main.utils.common.PatternUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/** 
 * @description �Զ�������֤���̵Ļ�����֤(Ҳ�������һЩ�򵥵�������Ч����֤)
 * ����:
 *  1.����
 *  2.����������,��������,���ֻ�����
 * @author ������
 * @date 2018-07-27 
 */
@Scope("prototype")//����
@Component("vaildatePro")
public class VaildateHelper extends BaseHelper {

    public ExcelUtils excelUtils;

    /*
     * ���bean��ͨ���в����Ĺ��캯���Զ����������
     * ����ͨ�����ֲ���
     * ����
     *  1.��ȡ���������ģ���ļ�
     *  2.����ģ���ļ�,�Զ�������֤sql
     *  3.˼�����д,���Ը��ӷ�������һ���������͵���֤
     *  ʹ��ָ�����캯������bean
     */
    @Autowired(required = true)//��ʾʹ��������캯��������Bean����
    public VaildateHelper(@Qualifier(value="excelHelper") ExcelHelper excel, @Qualifier(value="file")File file){
        log.info("ִ���вι��캯��");
        //û�а취�������excel���г�ʼ��(��Ҫ�����������Խ��г�ʼ��)
        this.excel = excel;
        /*
         * ʵ�ʿ�����,file����Ӧ����ͨ��һ����������,
         * �ٵ��ö���ĳ�ʼ������
         */
        excel.init(file, null);
    }
    
    /**
     * @description ֪ʶ��:�Ժ�����ж���������Ҫһ���ղεĹ��캯��
     */
    public VaildateHelper(){
        log.info("ִ���޲ι��캯��");
    }

    /** 
     * @description ����excel�ļ�
     * ��ʼ����Ҫ������(��excel�е����ݷ�װ��list<map>)
     * @author ������ 2018-08-06
     * @throws Exception 
     */
    @PostConstruct//�����ʼ������
    public void loadExcel() throws Exception {
        //��ʼ��excel�ļ�
        System.out.println("���г�ʼ��");
        System.out.println("��:"+excel.getCols()+" ��:"+excel.getRows());
        //list�д�ŵ���String[]
        List<String[]> list = new ArrayList<String[]>();
        for(int i = 0; i < excel.getCols(); i++) {
            list.add(excel.getColContextByIndex(i));
        }
        //��˼Ϊ: 1.���ǽ�list<String[]> ת�����ַ���  2.���ַ���ת����JosnArray����
        //3.��JosnArray ת��Ϊ JsonArrayHelper(���ǽ�JosnArray����List��)
        //4.��JsonArrayHelper ת��ΪListColumns
        //5.��ListColumns ת�����ַ���
        getSql(getColumns(JsonUtils.getJsonArray(list)));
    }

    /** 
     * @description ����Json��������Column����
     * @author ������ 2018-08-21
     * @param jsonArrayHelper
     */ 
    private List<VaildateColumn> getColumns(JsonArrayHelper json) {
        List<VaildateColumn> list =new ArrayList<VaildateColumn>();
        for(int i = 0; i < json.size(); i++) {
            JsonObjectHelper helper = json.get(i);
            //��ȡ���е�keyֵ,�ٸ���keyֵ��ȡvalueֵ
            //����Mapʹ��̫�鷳,ת���ɶ���ʹ��
            VaildateColumn col =  helper.toBean(VaildateColumn.class);
            col.json = helper;
            list.add(col);
        }
        return list;
    }

    /** 
     * @description ����Column���϶������ɼ򵥵���֤SQL
     * @author ������ 2018-08-22
     * @param list
     * @throws Exception 
     */ 
    public void getSql(List<VaildateColumn> list) throws Exception{
        List<String> title = new ArrayList<String>();
        List<String> right = new ArrayList<String>();
        for(VaildateColumn column : list) {
            title.add(column.getName());
            right.add(column.getRight());
            //ͨcolumn�ж��ֶ��Ƿ����ظ�,��������ظ�,���ɶ�Ӧ��sql���
            if(!column.repeat){
                System.out.println(PatternUtils.replaceAll(VaildateSql.repeat, PatternUtils.reg, addKey(column.json)));
            }
            if(column.isRequire()){
                //����
                if(column.range.size() != 0){
                    //������ָ���ķ�����
                    //System.out.println(SQLString.range);
                    //��rangeת����tipRange��sqlRange  tipRange��ʾ����ʾ�����,sqlRange ��ʾ��sql�����
                    System.out.println(PatternUtils.replaceAll(VaildateSql.requireRange, PatternUtils.reg, addKey(column.json)));
                    continue;
                }else if("number".equals(column.columnType)){
                    //��������������
                    System.out.println(PatternUtils.replaceAll(VaildateSql.requireNumber, PatternUtils.reg, column.json));
                    continue;
                }else if("date".equals(column.columnType)){
                    //��������������
                    System.out.println(PatternUtils.replaceAll(VaildateSql.requireDate, PatternUtils.reg, column.json));
                    continue;
                }else{
                    //ֻ�Ǳ���
                    System.out.println(PatternUtils.replaceAll(VaildateSql.require, PatternUtils.reg, column.json));
                    continue;
                }
            }else if("number".equals(column.columnType)){
                //��������������
                System.out.println(PatternUtils.replaceAll(VaildateSql.number, PatternUtils.reg, column.json));
                continue;
            }else if("date".equals(column.columnType)){
                //��������������
                System.out.println(PatternUtils.replaceAll(VaildateSql.date, PatternUtils.reg, column.json));
                continue;
            }else if(column.range.size() != 0){
                //������ʲô��Χ��
                System.out.println(PatternUtils.replaceAll(VaildateSql.range, PatternUtils.reg, addKey(column.json)));
                continue;
            }
        }
        //�����Զ�������֤������
        //2.����excel�ļ�
        excelUtils.createExcel(FileListUtils.file1, getVaildateContext(right.toArray(new String[]{})), title.toArray(new String[]{}));
    }
    
    /** 
     * @description ���������ֶε���֤����
     * @author ������ 2018-08-28
     * @param right ������ȷ����
     * @return
     */ 
    private List<String[]> getVaildateContext(String[] right){
        List<String[]> contexts = new ArrayList<String[]>();
        //��һ����ȷ��¼
        contexts.add(right);
        /* ���е�һ���ֶ��������ɴ����,����������ȷ��
         * ���������� һ����Ϊ��  һ��Ϊ �ַ���"test"
         * ������String[]�ĸ���Ϊ�ֶ�����������
         */
        int length = right.length*2;
        for(int i = 0; i < length; i++) {
            String[] rightCoty = java.util.Arrays.copyOf(right, right.length);
            try {
                if(i%2 == 0){
                    rightCoty[i/2] = "";
                }else{
                    rightCoty[i/2] = "a";
                }
                System.out.println(i);
                contexts.add(rightCoty);
            }
            catch (Exception e) {
                log.debug("���鿽��ʧ��");
            }
        }
        return contexts;
    }
    
    /** 
     * �������������������е�,���Զ��������ﲢ��˽��(��û�ж�����һ����������)
     * @description Ϊmap�������������ֵ��
     * @author ������ 2018-08-28
     * @param map
     */ 
    private JsonObjectHelper addKey(JsonObjectHelper map){
        String range = map.get("range");
        map.put("tipRange", range.substring(1, range.length()-1).replaceAll("\"", ""));
        map.put("sqlRange", range.substring(1, range.length()-1).replaceAll("\"", "''"));
        return map;
    }
    
    /** 
     * @description �ͷ���Դ()
     * @author ������ 2018-08-21
     * ���ٵ�ע��ֻ�ڵ����������Ч
     */ 
    public void destroy(){
        System.out.println("�ͷ���Դ");
        //�ͷ�excelռ�õ���Դ
        excel.close();
    }
    
    //ע��excel�İ�����(���췽��ע��)
    public ExcelHelper excel;
    
    //ע��
    @Autowired
    public void setExcelUtils(ExcelUtils excelUtils) {
        this.excelUtils = excelUtils;
    }
}