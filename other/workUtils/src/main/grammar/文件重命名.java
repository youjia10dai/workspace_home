/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.grammar;

import java.io.File;

/** 
 * @description 这里用一句话描述这个类的作用
 * @author 陈吕奖
 * @date 2018-10-29 
 */
public class 文件重命名 {
    public static void main(String[] args) {
        //把f:/a/a.xlsx原文件重命名为f:/a/b.xlsx，其中路径是必要的。注意
        System.out.println(new File("E:\\test3\\复件 人力\\2013年南京移动职业技能鉴定全年办证数据-剔除离职人员1.xlsx").renameTo(new File("E:\\test3\\复件 人力\\1.xlsx")));  
    }
}

