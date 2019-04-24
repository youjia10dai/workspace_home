/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */
package main.grammar;

import java.util.ArrayList;
import java.util.List;

/**
 * @description Java块级作用域
 * @author 陈吕奖
 * @date 2018-10-18
 */
public class Java块级作用域 {
    
    public static void main(String[] args) {
        //Java是有块级作用域的     Js中默认是函数作用域,使用let权健字之后,出现块级作用域
        List<String> sources = new ArrayList<String>();
        for(int i = 0; i < sources.size(); i++) {
            Object o  = sources.get(i);
        }
        for(int i = 0; i < sources.size(); i++) {
            Object o  = sources.get(i);
        }
    }
}
