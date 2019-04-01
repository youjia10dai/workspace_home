/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.grammar;

/** 
 * @description 测试除以2的值
 * @author 陈吕奖
 * @date 2018-08-29 
 */
public class 测试除以2的值 {

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
