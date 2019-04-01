/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.helper;

import java.sql.Connection;

import javax.annotation.Resource;

import main.helper.database.DBHelper;
import main.helper.database.DBHelperProxy;
import main.utils.common.StringUtils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/** 
 * @description 暂时只用于注入一些常用的工具类
 * @author 陈吕奖
 * @date 2018-06-27 
 */
@Component("baseUtils")
public class BaseHelper {

    //账务3和账务6相比,只有url地址不一样,其他都一样
    public DBHelper db;//账务6
//    public DBHelper db3;//账务3
    public DBHelperProxy db2;//和db相比,就是在执行时,会输出SQL语句
    //conn的注入是通过db的方法
    public Connection conn;

    public StringUtils str = new StringUtils();
    
    /*
     * 如果Logger配置文件在src下,就可以直接这样使用
     * 注意:在多线的情况下肯能回报错(web项目中定义在baseAction中,action是多例模式的)
     */
    public final Logger log = Logger.getLogger(this.getClass());
    
    public Connection getConn() {
        return conn;
    }
    
    @Autowired
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public DBHelperProxy getDb2() {
        return db2;
    }
    
    @Autowired
    public void setDb2(DBHelperProxy db2) {
        this.db2 = db2;
    }
    
    public DBHelper getDb() {
        return db;
    }
    
    @Resource(name = "DBHelper")
    public void setDb(DBHelper db) {
        this.db = db;
    }
}
