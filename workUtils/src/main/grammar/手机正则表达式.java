/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.grammar;

/** 
 * @description �ֻ�������ʽ
 * @author ������
 * @date 2018-06-04 
 */
public class �ֻ�������ʽ {

    /** 
     * @description ������һ�仰�����������������
     * @author ������ 2018-06-04
     * @param args
     */
    public static void main(String[] args) {
        String phone = "13123456789";
        String phoneNumber = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
        System.out.println("����phone��4*��" + phoneNumber);
    }

}
