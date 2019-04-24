/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */
package main.utils.common;

import java.util.Scanner;

/**
 * @description 获取键盘录入信息工具类
 * @author 陈吕奖
 * @date 2018-11-12
 */
public class ScannerHelper {

    public String outPutStr;
    
    public ScannerHelper(String outPutStr){
        this.outPutStr = outPutStr;
    }
    Scanner sc = new Scanner(System.in);
    public void getInputString(GetUserInputInfo input) throws Exception {
        System.out.println(outPutStr);
        String next = sc.next();
        input.input(next);
    }

}