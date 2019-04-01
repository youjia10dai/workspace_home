/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
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
 * @description ���ݿ����ӵİ�����(�Ա���Ŀ�е���,������û���ǹ���) ���ܽ��� 1. ʹ�� doInTransaction
 *              ���ж���SQL���ĸ��²���() 2. ʹ�� update ���е��������²���
 * @author ������
 * @date 2018-06-11
 */
//�����ͨ�������ļ�,���õ�Spring�е�
public class DBHelper extends JdbcDaoSupport {

    private TransactionTemplate transactionTemplate;

    public synchronized static DBHelper getInstance() {
        DBHelper db = null;
        try {
            db = (DBHelper) SpringHelper.getBean("DBHelper");
        }
        catch (Exception e) {
            System.out.println("�������ݲ�������ʧ�ܣ�" + e.toString());
            e.printStackTrace();
        }
        return db;
    }

    /**
     * @description ����һ�м�¼(ʹ��Map��װ)�޲�
     * @author ������ 2018-07-13
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
     * @description ����һ�м�¼(ʹ��Map��װ)
     * @author ������ 2018-10-16
     * @param sql
     * @param objects ������Ϣ
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
     * @description ��Map���ݸ�ֵ��obj����
     * @author ������ 2018-11-09
     * @param map
     * @param obj
     * @throws SecurityException 
     * @throws Exception 
     */ 
    public void mapConvertToObject(Map<String, Object> map, Object obj) throws Exception{
        //��ȡkey�ļ���
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
     * @description ����SQL��ѯ,���ض�����¼(List<Map>��װ)
     * @author ������ 2018-06-19
     * @param sql
     * @param objects ������Ϣ
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
     * @description ����SQL��ѯ,����list����(û�в���)
     * @author ������ 2018-06-19
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
     * @description ����һ��ִ�д洢���̵Ķ���
     * @author ������ 2018-06-19
     * @param sql �洢���̵�����
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
     * @description �ر����ݿ�����
     * @author ������ 2018-06-19
     */
    public void closeDblinks() {
        try {
            /*
             * �������ݿ�,һ����close_dblink,һ��û��,�����ж� �о͹ر�,û��
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
     * @description ����DML������(���ºͲ���),�������
     * @author ������ 2018-07-13
     * @param sql
     * @param param
     * @return �������0,��ʾ����û�гɹ�
     */
    public int update(String sql, Object... param) {
        BatchSql batchSql = new BatchSql();
        batchSql.addBatch(sql, param);
        return doInTransaction(batchSql);
    }

    /**
     * @description ��ѯһ����ֵ���
     * @author ������ 2018-07-13
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
     * û�з���ֵ������ 1.Spring Jdbc��Ĭ�ϵ�������Զ��ύ�����
     * 2.����ʹ�õ��Ǳ��ʽ�������������м�û�б�����Զ����ύ����������ִ��󽫻ع�����
     * @param batchSqls
     * @return �������зŻ�1   �����쳣����0
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
     * @description �������л�ȡ���е�ֵ
     * @author ������ 2018-07-13
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
     * @description �Ͻű��ر�������,ȥ�����
     * @author ������ 2018-09-03
     */
    @PreDestroy
    public void colse() {
        //ʹ�����ע�Ⲣû����
        System.out.println("�ر�������");
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
