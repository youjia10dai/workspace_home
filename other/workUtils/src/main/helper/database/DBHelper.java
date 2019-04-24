/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */
package main.helper.database;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PreDestroy;

import main.helper.database.sql.BatchSql;
import main.helper.spring.SpringHelper;
import main.utils.common.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @description 数据库连接的帮助类(对比项目中的类,批处理没有那过来) 功能介绍 1. 使用 doInTransaction
 *              进行多条SQL语句的更新操作() 2. 使用 update 进行单条语句更新操作
 * @author 陈吕奖
 * @date 2018-06-11
 */
//这个是通过配置文件,配置到Spring中的
public class DBHelper extends JdbcDaoSupport {

    private TransactionTemplate transactionTemplate;

    public synchronized static DBHelper getInstance() {
        DBHelper db = null;
        try {
            db = (DBHelper) SpringHelper.getBean("DBHelper");
        }
        catch (Exception e) {
            System.out.println("创建数据操作对象失败！" + e.toString());
            e.printStackTrace();
        }
        return db;
    }

    /**
     * @description 返回一行记录(使用Map封装)无参
     * @author 陈吕奖 2018-07-13
     * @param sql
     * @return
     */
    public Map<String,Object> queryForMap(String sql) {
        Map<String,Object> map = null;
        try {
            map = this.getJdbcTemplate().queryForMap(sql, null);
        }
        catch (Exception e) {
            logger.error(e);
            logger.error(sql);
        }
        if(map == null)
            map = new HashMap<String,Object>();
        return map;
    }

    /** 
     * @description 返回一行记录(使用Map封装)
     * @author 陈吕奖 2018-10-16
     * @param sql
     * @param objects 参数信息
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map queryForMap(String sql, Object... objects) {
        Map map = null;
        try {
            map = this.getJdbcTemplate().queryForMap(sql, objects);
        }
        catch (Exception e) {
//            logger.error(sql);
            logger.info(StringUtils.getSql(sql, objects));
            logger.error(e);
        }

        if(map == null)
            map = new HashMap();
        return map;
    }

    /** 
     * @description 将Map数据赋值给obj对象
     * @author 陈吕奖 2018-11-09
     * @param map
     * @param obj
     * @throws SecurityException 
     * @throws Exception 
     */ 
    public void mapConvertToObject(Map<String, Object> map, Object obj) throws Exception{
        //获取key的集合
        Set<String> set = map.keySet();
        Class<?> class1 = obj.getClass();
        for(String fieldStr : set) {
            logger.debug(fieldStr);
            Field field = class1.getDeclaredField(fieldStr);
            field.setAccessible(true);
            logger.debug(field.getType());
            if("int".equals(field.getType().toString())){
                field.setInt(obj, Integer.parseInt(map.get(fieldStr).toString()));
            }else{
                field.set(obj, map.get(fieldStr).toString());
            }
        }
    }
    
    /**
     * @description 根据SQL查询,返回多条记录(List<Map>封装)
     * @author 陈吕奖 2018-06-19
     * @param sql
     * @param objects 参数信息
     * @return
     */
    public List<Map<String, Object>> queryForList(String sql, Object ... objects) {
        List<Map<String, Object>> list = null;
        try {
            list = this.getJdbcTemplate().queryForList(sql, objects);
        }
        catch (Exception e) {
            logger.error(sql);
            logger.error(e);
        }
        if(list == null)
            list = new ArrayList<Map<String, Object>>();
        return list;
    }
    
    /**
     * @description 根据SQL查询,返回list集合(没有参数)
     * @author 陈吕奖 2018-06-19
     * @param sql
     * @return
     */
    public List<Map<String, Object>> queryForList(String sql) {
        List<Map<String, Object>> list = null;
        try {
            list = this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            logger.error(sql);
            logger.error(e);
        }
        return list;
    }

    /**
     * @description 创建一个执行存储过程的对象
     * @author 陈吕奖 2018-06-19
     * @param sql 存储过程的名称
     * @return
     */
    public ProcHelper getProcHelper(String sql) {
        ProcHelper proc = null;
        try {
            proc = new ProcHelper(this.getDataSource(), sql);
            proc.setSql(sql);
        }
        catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        }

        return proc;

    }

    /**
     * @description 关闭数据库连接
     * @author 陈吕奖 2018-06-19
     */
    public void closeDblinks() {
        try {
            /*
             * 两个数据库,一个有close_dblink,一个没有,进行判断 有就关闭,没有
             */
            System.out.println(this.queryForInt("select count(1) from all_objects where object_name = ?",
                    "CLOSE_DBLINKS2") == 1);
            if(this.queryForInt("select count(1) from all_objects where object_name = ?", "CLOSE_DBLINKS2") == 1) {
                this.getProcHelper("close_dblinks2").execute();
            }
        }
        catch (Exception e) {
            logger.error("close_dblinks error !" + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * @description 进行DML语句操作(更新和插入),单条语句
     * @author 陈吕奖 2018-07-13
     * @param sql
     * @param param
     * @return 如果返回0,表示操作没有成功
     */
    public int update(String sql, Object... param) {
        BatchSql batchSql = new BatchSql();
        batchSql.addBatch(sql, param);
        return doInTransaction(batchSql);
    }

    /**
     * @description 查询一个数值结果
     * @author 陈吕奖 2018-07-13
     * @param sql
     * @param objects
     * @return
     */
    public int queryForInt(String sql, Object... objects) {
        int exc = -1;
        try {
            exc = this.getJdbcTemplate().queryForObject(sql, objects, Integer.class);
            //            exc=this.getJdbcTemplate().queryForObject(sql, Integer.class);
        }
        catch (Exception e) {
            exc = -1;
            logger.error(sql + " " + e.toString());
            logger.error(e);
            e.printStackTrace();
        }

        return exc;
    }

    /**
     * 没有返回值事务处理 1.Spring Jdbc在默认的情况是自动提交事务的
     * 2.这里使用的是编程式事务管理如果运行间没有报错就自动的提交事务如果出现错误将回滚事务
     * @param batchSqls
     * @return 正常运行放回1   出现异常返回0
     */
    public int doInTransaction(final BatchSql batchSql) {
        int exc = 1;
        if(batchSql == null) {
            exc = 0;
        }
        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    List sqlList = batchSql.getSqlList();
                    for(int i = 0; i < sqlList.size(); i++) {
                        Map sqlMap = (Map) sqlList.get(i);
                        String sql = (String) sqlMap.get("sql");
                        Object[] objects = (Object[]) sqlMap.get("objects");
                        getJdbcTemplate().update(sql, objects);
                    }
                    batchSql.init();
                }
            });
        }
        catch (Exception e) {
            exc = 0;
            logger.error(e);
            e.printStackTrace();
        }
        return exc;
    }

    /**
     * @description 根据序列获取序列的值
     * @author 陈吕奖 2018-07-13
     * @param sequenceName
     * @return
     */
    public String getNextSequenceValue(String sequenceName) {
        Map map = null;
        String nextVal = "";
        try {
            map = this.queryForMap("select " + sequenceName + ".NEXTVAL SEQ from dual");
            nextVal = map.get("SEQ").toString();
        }
        catch (Exception e) {
            logger.error(e);
        }
        return nextVal;
    }

    /**
     * @description 老脚本关闭数据链,去除这个
     * @author 陈吕奖 2018-09-03
     */
    @PreDestroy
    public void colse() {
        //使用这个注解并没有用
        System.out.println("关闭数据链");
        closeDblinks();
    }

    @Bean
    public Connection getConnection1() throws Exception {
        return this.getDataSource().getConnection();
    }

    /* get set */
    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
}
