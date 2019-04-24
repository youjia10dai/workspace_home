/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.grammar;

/** 
 * @description 手机正则表达式
 * @author 陈吕奖
 * @date 2018-06-04 
 */
public class 手机正则表达式 {

    /** 
     * @description 这里用一句话描述这个方法的作用
     * @author 陈吕奖 2018-06-04
     * @param args
     */
    public static void main(String[] args) {
        String phone = "13123456789";
        String phoneNumber = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
        System.out.println("正则phone中4*：" + phoneNumber);
    }

}
