/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */
package main.app.njry.procedure.source;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import main.utils.common.StringUtils;
import main.utils.file.TextFileUtils;

/**
 * @description �洢������صİ�����
 * @author ������
 * @date 2018-06-14 ���ܵ� 1.����ɾ�����(���ǻ�ȡ�ļ���droptable�еı�) 2.���ɹ����б�����Ӧ��ֵ(�鿴���̱�����Ϣ)
 */
public class ProcedureUtils {

    /**
     * @description �Զ�����һ���洢����ɾ����ʱ������
     * @author ������ 2018-06-14
     * @param excelFile
     */
    public static void getDeleteStatement(File proceFile) {
        String[] fileContext = TextFileUtils.getFileContext(proceFile, "utf-8");
        getDeleteSrateByArray(fileContext);
    }

    /**
     * @description �Զ�����ɾ�����
     * @author ������ 2018-09-21
     * @param fileContext
     */
    public static void getDeleteSrateByArray(String[] fileContext) {
        List<String> deleteList = new ArrayList<String>();
        List<String> renameList = new ArrayList<String>();
        //��ȡ���еı���  \\s*droptable\\('(.+)'\\)  ʹ��������ʽ��ȡ 
        for(String string : fileContext) {
            //��ȡ�洢�����е�����ɾ�����
            if(string.contains("droptable")) {
                deleteList.add(StringUtils.getTableName(string.trim()));
            } else if(string.contains("rename")) {
                //��ȡ���е�renam���
                renameList.add(StringUtils.getTableName(string.trim()));
            }
        }
        //ɾ������ȥrename���,�������յ�ɾ�����
        deleteList.removeAll(renameList);
        for(String string2 : deleteList) {
            if(string2.contains("tmp"))
                System.out.println("droptable(" + string2 + ");");
        }
    }

    /**
     * @description ��ȡһ���洢���̵Ŀ�ͷ�ı�����Ϣ ����һ����ʱ�ű�,���������Ϣ
     * @author ������ 2018-06-14
     */
    public static void getVariableInfo(File proceFile) {

        String[] fileContext = TextFileUtils.getFileContext(proceFile, "utf-8");
        getVariableInfoByArray(fileContext);
    }

    /**
     * @description �Զ�������ʱ�ű�
     * @author ������ 2018-09-21
     * @param fileContext
     */
    public static void getVariableInfoByArray(String[] fileContext) {
        //��ȡ���̵ĵ��ò���
        String param = fileContext[0];
        ProcedureVariable var = new ProcedureVariable(param);
        //�к�
        int lineNumber = 0;
        //��ȡ���еı�����
        List<String> variableList = new ArrayList<String>();
        //��ȡ���̵ľֲ�����
        for(int i = 1; i < fileContext.length; i++) {
            String string = fileContext[i];
            if(string.contains("*")) {
                // /**/����������ݲ���Ҫ��ʾ
                fileContext[i] = "";
                continue;
            }

            String[] split = string.split("\\s+");
            if(split.length >= 3) {
                variableList.add(split[1]);
            }
            //����begin��������ǰѭ��
            if(string.contains("begin")) {
                lineNumber = i;
                break;
            }
        }
        //��������
        variableList.addAll(0, var.getInparam());
        //��¼���ļ���ȡ����λ��
        for(int i = lineNumber + 1; i < fileContext.length; i++) {
            String string = fileContext[i];
            //����begin��������ǰѭ��
            if(string.contains("if")) {
                lineNumber = i;
                break;
            }
        }
        //������ʱ����
        System.out.println("declare");
        //������Ĳ���Ҳ�����ʱ����   �����������
        for(String param1 : var.assignmentParamList) {
            System.out.println(param1);
        }
        for(int i = 1; i < lineNumber; i++) {
            String string = fileContext[i];
            if(!StringUtils.isEmpty(string)) {
                System.out.println(string);
            }
            if(string.contains("begin")) {
                //���ɸ�ֵ���
                for(String param1 : var.declareParamList) {
                    System.out.println(param1);
                }
            }
        }
        //���ɲ�����������
        for(String param1 : variableList) {
            //
            System.out.println("  dbms_output.put_line('" + param1 + "��ֵΪ:'||" + param1 + ");");
        }
        System.out.println("end;");
    }

    public static void main(String[] args) {
        File file = new File("D:\\1.txt");
        getVariableInfo(file);
    }

}
