/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */
package main.utils.common;

import java.util.Scanner;

/**
 * @description ��ȡ����¼����Ϣ������
 * @author ������
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