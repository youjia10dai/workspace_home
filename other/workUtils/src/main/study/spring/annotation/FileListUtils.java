/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.study.spring.annotation;

import java.io.File;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/** 
 * @description ����File����(���е�File����ȫ������������)
 * @author ������
 * @date 2018-08-15 
 */
@Component("fileListUtils")
public class FileListUtils {
    
    //�Զ�������֤����������ļ�
    public static File file = new File("D:\\�ҵļ����\\ͬ���ļ�\\workspace\\excelInfo\\��֤����\\���ɻ�����֤ģ�� 7�����͵���֤.xls");

    //�Զ����ɵ���֤�ļ�
    public static File file1 = new File("D:\\�ҵļ����\\ͬ���ļ�\\workspace\\excelInfo\\��֤����\\������֤ģ��.xls");
    
    //����Excel�ļ��������ñ�
    public static File file2 = new File("D:\\�ҵļ����\\ͬ���ļ�\\workspace\\excelInfo\\��Эָ��ר��\\��Эת�����ñ�.xls");
    
    
    public static File file3 = new File("D:\\�ҵļ����\\ͬ���ļ�\\workspace\\excelInfo\\С���û���ǩ\\���V2.xls");
    
    /** 
     * @description ��file�����ڷ�����,ͨ�������������ע��
     * ȱ��:ֻ��ע�����
     * 
     * public VaildateProUtils(@Qualifier(value="excelHelper") ExcelHelper excel, @Qualifier(value="file")File file){
     * @author ������ 2018-08-28
     * @return
     */ 
    @Bean(name="file")
    public File getFile(){
        return file;
    }
    
}