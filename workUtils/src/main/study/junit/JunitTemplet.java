/*
 * Copyright (c) 2019  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.study.junit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/** 
 * 单元测试的模板
 * @author 陈吕奖
 * @date 2019-03-21 
 */
public class JunitTemplet {

    @BeforeClass
    public static void setUpBeforeClass(){
        //该方法在在整个类初始化之前调用
    }

    @AfterClass
    public static void tearDownAfterClass(){
        //该方法在在整个类初始化之后调用
    }

    @Before
    public void setUp(){
        //方法在测试方法前调用,一般用来做测试准备工作
    }

    @After
    public void tearDown(){
        //方法在测试方法之后抵用,一般做清理工作
    }
	
}
