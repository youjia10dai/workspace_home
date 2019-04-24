/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.grammar;

import java.util.Date;


/** 
 * @description 这里用一句话描述这个类的作用
 * @author 陈吕奖
 * @date 2018-11-01 
 */
public class 比较两个时间的大小 {

    
    public static void main(String[] args) throws Exception {
        Date first = new Date();
        Thread.sleep(1000);
        Date second = new Date();
        //比较毫秒值,来确定大小
        System.out.println(first.getTime() > second.getTime());
    }
    
    
    
}
