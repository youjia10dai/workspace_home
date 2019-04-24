/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.app.njry.procedure.source;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import main.utils.common.DateUtils;
import main.utils.common.StringUtils;
import main.utils.file.TextFileUtils;

/** 
 * @description �����������̵Ĳ�����Ϣ
 * @author ������
 * @date 2018-06-15 
 */
public class ProcedureVariable {

    //�������   ������  ��  ��������
    Map<String,String> inPara = new HashMap<String, String>();
    
    //�������
    Map<String,String> outPara = new HashMap<String, String>();
    
    /*
     * ��ʾ����ļ���
     */
    //�������
    List<String> declareParamList = new ArrayList<String>();
    
    //��ֵ���
    List<String> assignmentParamList = new ArrayList<String>();
    /**
     * @description ���ݹ����ļ�����
     * @param excelFile
     */
    public ProcedureVariable(File proceFile){
//        String[] fileContext = ;
//        String param = fileContext[0];
        /*
         * ��ʼ��ʱ   this��super ������ڵ�һ��
         * ��ʼ����˳���Ҫ��,����һ��һ����
         */
        this(TextFileUtils.getFileContext(proceFile,"utf-8")[0]);
    }
    
    
    /**
     * @description ���ݹ���ͷ����
     * @param str
     */
    public ProcedureVariable(String str){
        String param = StringUtils.getTableName(str);
        if(param.contains(",")){
            //�������
            String[] params = StringUtils.split(param, ",");
            for(String singleParam : params) {
                analysisSingleParam(singleParam);
            }
        }else{
            analysisSingleParam(param);
        }
    }


    /** 
     * @description ��������������
     * @author ������ 2018-06-15
     * @param param
     */ 
    private void analysisSingleParam(String param) {
        //��������
        String[] params = StringUtils.split(param, "\\s+");
        if(params.length == 2){
            inPara.put(params[0], params[1]);
            declareParamList.add("  "+params[0]+"          := "+new DateUtils().getToday("yyyyMMdd")+";");
            assignmentParamList.add("  "+params[0]+"          "+params[1]+";");
        }else if (params.length == 3) {
            if(params[1].contains("in")){
                inPara.put(params[0], params[2]);
            }else if(params[1].contains("out")){
                outPara.put(params[0], params[2]);
            }
            declareParamList.add("  "+params[0]+"          := "+new DateUtils().getToday("yyyyMMdd")+";");
            assignmentParamList.add("  "+params[0]+"          "+params[2]+";");
        }
    }
    
    /** 
     * @description ��ȡ���е��������
     * @author ������ 2018-06-15
     * @return
     */ 
    public List<String> getInparam(){
        List<String> list = new ArrayList<String>();
        for(Iterator iterator = inPara.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            list.add(key);
        }
        return list;
    }
}
