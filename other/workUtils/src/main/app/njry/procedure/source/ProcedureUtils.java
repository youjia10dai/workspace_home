/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */
package main.app.njry.procedure.source;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import main.utils.common.StringUtils;
import main.utils.file.TextFileUtils;

/**
 * @description 存储过程相关的帮助类
 * @author 陈吕奖
 * @date 2018-06-14 功能点 1.生成删表语句(就是获取文件中droptable中的表) 2.生成过程中变量对应的值(查看过程变量信息)
 */
public class ProcedureUtils {

    /**
     * @description 自动生成一个存储过程删除临时表的语句
     * @author 陈吕奖 2018-06-14
     * @param excelFile
     */
    public static void getDeleteStatement(File proceFile) {
        String[] fileContext = TextFileUtils.getFileContext(proceFile, "utf-8");
        getDeleteSrateByArray(fileContext);
    }

    /**
     * @description 自动生成删除语句
     * @author 陈吕奖 2018-09-21
     * @param fileContext
     */
    public static void getDeleteSrateByArray(String[] fileContext) {
        List<String> deleteList = new ArrayList<String>();
        List<String> renameList = new ArrayList<String>();
        //获取所有的表名  \\s*droptable\\('(.+)'\\)  使用正则表达式获取 
        for(String string : fileContext) {
            //获取存储过程中的所有删除语句
            if(string.contains("droptable")) {
                deleteList.add(StringUtils.getTableName(string.trim()));
            } else if(string.contains("rename")) {
                //获取所有的renam语句
                renameList.add(StringUtils.getTableName(string.trim()));
            }
        }
        //删除语句减去rename语句,生成最终的删除语句
        deleteList.removeAll(renameList);
        for(String string2 : deleteList) {
            if(string2.contains("tmp"))
                System.out.println("droptable(" + string2 + ");");
        }
    }

    /**
     * @description 获取一个存储过程的开头的变量信息 生成一个临时脚本,输出变量信息
     * @author 陈吕奖 2018-06-14
     */
    public static void getVariableInfo(File proceFile) {

        String[] fileContext = TextFileUtils.getFileContext(proceFile, "utf-8");
        getVariableInfoByArray(fileContext);
    }

    /**
     * @description 自动生成临时脚本
     * @author 陈吕奖 2018-09-21
     * @param fileContext
     */
    public static void getVariableInfoByArray(String[] fileContext) {
        //获取过程的调用参数
        String param = fileContext[0];
        ProcedureVariable var = new ProcedureVariable(param);
        //行号
        int lineNumber = 0;
        //获取所有的变量名
        List<String> variableList = new ArrayList<String>();
        //获取过程的局部变量
        for(int i = 1; i < fileContext.length; i++) {
            String string = fileContext[i];
            if(string.contains("*")) {
                // /**/这里面的内容不需要显示
                fileContext[i] = "";
                continue;
            }

            String[] split = string.split("\\s+");
            if(split.length >= 3) {
                variableList.add(split[1]);
            }
            //遇到begin就跳出当前循环
            if(string.contains("begin")) {
                lineNumber = i;
                break;
            }
        }
        //参数汇总
        variableList.addAll(0, var.getInparam());
        //记录下文件读取到的位置
        for(int i = lineNumber + 1; i < fileContext.length; i++) {
            String string = fileContext[i];
            //遇到begin就跳出当前循环
            if(string.contains("if")) {
                lineNumber = i;
                break;
            }
        }
        //生成临时过程
        System.out.println("declare");
        //将输入的参数也变成临时参数   生成声明语句
        for(String param1 : var.assignmentParamList) {
            System.out.println(param1);
        }
        for(int i = 1; i < lineNumber; i++) {
            String string = fileContext[i];
            if(!StringUtils.isEmpty(string)) {
                System.out.println(string);
            }
            if(string.contains("begin")) {
                //生成赋值语句
                for(String param1 : var.declareParamList) {
                    System.out.println(param1);
                }
            }
        }
        //生成参数的输出语句
        for(String param1 : variableList) {
            //
            System.out.println("  dbms_output.put_line('" + param1 + "的值为:'||" + param1 + ");");
        }
        System.out.println("end;");
    }

    public static void main(String[] args) {
        File file = new File("D:\\1.txt");
        getVariableInfo(file);
    }

}
