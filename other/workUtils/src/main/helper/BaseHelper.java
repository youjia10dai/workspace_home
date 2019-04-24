/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
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
 * @description ��ʱֻ����ע��һЩ���õĹ�����
 * @author ������
 * @date 2018-06-27 
 */
@Component("baseUtils")
public class BaseHelper {

    //����3������6���,ֻ��url��ַ��һ��,������һ��
    public DBHelper db;//����6
//    public DBHelper db3;//����3
    public DBHelperProxy db2;//��db���,������ִ��ʱ,�����SQL���
    //conn��ע����ͨ��db�ķ���
    public Connection conn;

    public StringUtils str = new StringUtils();
    
    /*
     * ���Logger�����ļ���src��,�Ϳ���ֱ������ʹ��
     * ע��:�ڶ��ߵ�����¿��ܻر���(web��Ŀ�ж�����baseAction��,action�Ƕ���ģʽ��)
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
