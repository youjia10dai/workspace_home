/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
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
 * @description 用于描述过程的参数信息
 * @author 陈吕奖
 * @date 2018-06-15 
 */
public class ProcedureVariable {

    //输入参数   参数名  和  参数类型
    Map<String,String> inPara = new HashMap<String, String>();
    
    //输出参数
    Map<String,String> outPara = new HashMap<String, String>();
    
    /*
     * 显示结果的集合
     */
    //声明语句
    List<String> declareParamList = new ArrayList<String>();
    
    //赋值语句
    List<String> assignmentParamList = new ArrayList<String>();
    /**
     * @description 根据过程文件解析
     * @param excelFile
     */
    public ProcedureVariable(File proceFile){
//        String[] fileContext = ;
//        String param = fileContext[0];
        /*
         * 初始化时   this和super 必须放在第一行
         * 初始化有顺序的要求,必须一步一步来
         */
        this(TextFileUtils.getFileContext(proceFile,"utf-8")[0]);
    }
    
    
    /**
     * @description 根据过程头解析
     * @param str
     */
    public ProcedureVariable(String str){
        String param = StringUtils.getTableName(str);
        if(param.contains(",")){
            //多个参数
            String[] params = StringUtils.split(param, ",");
            for(String singleParam : params) {
                analysisSingleParam(singleParam);
            }
        }else{
            analysisSingleParam(param);
        }
    }


    /** 
     * @description 解析出单个参数
     * @author 陈吕奖 2018-06-15
     * @param param
     */ 
    private void analysisSingleParam(String param) {
        //单个参数
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
     * @description 获取所有的输入参数
     * @author 陈吕奖 2018-06-15
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
