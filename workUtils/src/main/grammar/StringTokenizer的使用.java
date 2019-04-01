/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */
package main.grammar;

import java.util.StringTokenizer;

/**
 * @description 这里用一句话描述这个类的作用
 * @author 陈吕奖
 * @date 2018-10-29
 */
public class StringTokenizer的使用 {
    public static void main(String[] args) {
        StringTokenizer st = new StringTokenizer("a|bddfdfdd|c", "|d");
        while(st.hasMoreElements()) {
            System.out.println("Token:" + st.nextToken());
        }
    }

}
