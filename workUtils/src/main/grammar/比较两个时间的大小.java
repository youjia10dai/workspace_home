/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.grammar;

import java.util.Date;


/** 
 * @description ������һ�仰��������������
 * @author ������
 * @date 2018-11-01 
 */
public class �Ƚ�����ʱ��Ĵ�С {

    
    public static void main(String[] args) throws Exception {
        Date first = new Date();
        Thread.sleep(1000);
        Date second = new Date();
        //�ȽϺ���ֵ,��ȷ����С
        System.out.println(first.getTime() > second.getTime());
    }
    
    
    
}
