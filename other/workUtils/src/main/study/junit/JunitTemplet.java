/*
 * Copyright (c) 2019  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.study.junit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/** 
 * ��Ԫ���Ե�ģ��
 * @author ������
 * @date 2019-03-21 
 */
public class JunitTemplet {

    @BeforeClass
    public static void setUpBeforeClass(){
        //�÷��������������ʼ��֮ǰ����
    }

    @AfterClass
    public static void tearDownAfterClass(){
        //�÷��������������ʼ��֮�����
    }

    @Before
    public void setUp(){
        //�����ڲ��Է���ǰ����,һ������������׼������
    }

    @After
    public void tearDown(){
        //�����ڲ��Է���֮�����,һ����������
    }
	
}
