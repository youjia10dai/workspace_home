/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */
package main.grammar;

import java.util.StringTokenizer;

/**
 * @description ������һ�仰��������������
 * @author ������
 * @date 2018-10-29
 */
public class StringTokenizer��ʹ�� {
    public static void main(String[] args) {
        StringTokenizer st = new StringTokenizer("a|bddfdfdd|c", "|d");
        while(st.hasMoreElements()) {
            System.out.println("Token:" + st.nextToken());
        }
    }

}
