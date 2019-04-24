/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.grammar;

/** 
 * @description 这里用一句话描述这个类的作用
 * @author 陈吕奖
 * @date 2018-11-08 
 */
public class 初始化测试 {

    public 初始化测试(int i){
        System.out.println("初始化测试.初始化测试()");
    }
    {
        System.out.println("1");
    }
    
    public static void main(String[] args) {
        new 初始化测试(1);
        new 初始化测试(1);
    }
}
