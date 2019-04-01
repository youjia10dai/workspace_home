/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.helper.database;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import main.helper.database.sql.BatchSql;
import main.utils.common.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/** 
 * ����ʹ��Spring��aop���и���
 * @description DBHelper����Ĵ������
 * ��ִ��ÿһ��SQL����ʱ��,���sql������
 * @author ������
 * @date 2018-07-16 
 */
@Component("dbhelperproxy")
public class DBHelperProxy {

    
    DBHelper db ;
    public final Logger log = Logger.getLogger(this.getClass());
    public StringUtils str = new StringUtils();
    
    public int doInTransaction(final BatchSql batchSql)
    {
        List<Map<String,Object>> sqlList = batchSql.getSqlList();
        for(Map<String, Object> map : sqlList) {
            log.debug(str.getSql(map.get("sql").toString(), (Object[])map.get("objects")));
        }
        return db.doInTransaction(batchSql);
    }
    
    public int update(String sql, Object ... param){
        log.debug(str.getSql(sql, (Object[])param));
        return db.update(sql,param);
    }
    
    public int queryForInt(String sql, Object ... objects)
    {
        log.debug(str.getSql(sql, (Object[])objects));
        return db.queryForInt(sql,objects);
    }
    
    public Map queryForMap(String sql) {
        log.debug(sql);
        return db.queryForMap(sql);
    }
    
    public Map queryForMap(String sql, Object ... objects)
    {
        //(Object[])��ʾ�Ҿ���һ������,���ò�
        log.debug(str.getSql(sql, (Object[])objects));
        return db.queryForMap(sql, objects);
    }

    public List<Map<String, Object>> queryForList(String sql, Object[] objects)
    {
        log.debug(str.getSql(sql, (Object[])objects));
        return db.queryForList(sql, objects);
    }
    
    public List<Map<String, Object>> queryForList(String sql)
    {
        log.debug(sql);
        return db.queryForList(sql);
    }
    
    public DBHelper getDb() {
        return db;
    }
    
    @Resource(name = "DBHelper")
    public void setDb(DBHelper db) {
        this.db = db;
    }
    
}
