/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.grammar;

/** 
 * @description ���Գ���2��ֵ
 * @author ������
 * @date 2018-08-29 
 */
public class ���Գ���2��ֵ {

    public static void main(String[] args) {
//        for(int i = 0; i < 100; i++) {
//            System.out.println(i/2);
//        }
//        
//        String str = new String("\n\t");
//        System.out.println(str.length());
        
        String string = "zcfx.t_jtvnetmem_'|| v_mon ||'@topnj";
        string = string.replaceAll("'(.)+'", "");
        System.out.println(string);
    }
    
}
