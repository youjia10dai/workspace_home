/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.grammar;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import main.helper.database.DBHelper;
import org.springframework.stereotype.Component;


/** 
 * @description 测试
 * 先执行自身初始化方法,在执行Spring初始化方法
 * @author 陈吕奖
 * @date 2018-12-03 
 */
@Component("testInit")
public class 测试Spring的初始化注解 {

    public DBHelper db;//账务6
    
    public 测试Spring的初始化注解(){
        System.out.println("自身初始化方法");
        System.out.println("成员是否注入" + db == null);
    }
    
    @PostConstruct
    public void init(){
        System.out.println("Spring初始化方法");
        System.out.println("成员是否注入" + db == null);
    }
    
    @Resource(name = "DBHelper")
    public void setDb(DBHelper db) {
        this.db = db;
    }
}
