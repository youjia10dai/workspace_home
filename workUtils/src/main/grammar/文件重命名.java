/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.grammar;

import java.io.File;

/** 
 * @description ������һ�仰��������������
 * @author ������
 * @date 2018-10-29 
 */
public class �ļ������� {
    public static void main(String[] args) {
        //��f:/a/a.xlsxԭ�ļ�������Ϊf:/a/b.xlsx������·���Ǳ�Ҫ�ġ�ע��
        System.out.println(new File("E:\\test3\\���� ����\\2013���Ͼ��ƶ�ְҵ���ܼ���ȫ���֤����-�޳���ְ��Ա1.xlsx").renameTo(new File("E:\\test3\\���� ����\\1.xlsx")));  
    }
}

