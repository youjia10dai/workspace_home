/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.helper.database.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.utils.file.TextFileUtils;

/** 
 * Batch   ������
 * @description SQL��伯��,�洢SQL���ͳһִ��
 * @author ������    
 * @date 2018-06-05 
 */
public class BatchSql {

    //�������ǰ���
    private String insertPlaceholderStr;//ռλ����
    private String insertString;//
    
    //sql  ? ? ?    ��   object�в�����Ӧ
    private List<Map<String, Object>> beforeExecution = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> afterExecution = new ArrayList<Map<String, Object>>();
    /*
     * ����list�û�����ĳЩ���ֵ�SQL���
     * ��Щ������ִ��,��Щ�����ִ��
     * list1��ִ��
     * list2��ִ��
     */
    
    public void addBatch(String sql)
    {  
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sql", sql);
        //û�в���,����һ���յ�����
        map.put("objects", new Object[]{});
        beforeExecution.add(map);
    }
    
    public void addBatch(String sql, Object[] objects)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sql", sql);
        map.put("objects", objects);
        beforeExecution.add(map);
    }
    
    public void addBatchAfter(String sql)
    {  
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sql", sql);
        //û�в���,����һ���յ�����
        map.put("objects", new Object[]{});
        afterExecution.add(map);
    }
    
    public void addBatchAfter(String sql, Object[] objects)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sql", sql);
        map.put("objects", objects);
        afterExecution.add(map);
    }
    
    /** 
     * @description ��ʾ�����е�SQL���,������С��ʱ��ʹ��
     * @author ������ 2018-06-06
     */ 
    public void showSql(){
        ////������ݺܶ� ����̨���޷�ȫ������ʾ, �����ݴ洢��text��
        for(Map map : beforeExecution) {
            System.out.println(map.get("sql"));
        }
        for(Map map : afterExecution) {
            System.out.println(map.get("sql"));
        }

    }

    public void outPutToFile(String pathnameFile){
        //C:\\Users\\Administrator\\Desktop\\����excel�ļ�\\��ʱ������\\1.txt
        TextFileUtils.outPutFile(pathnameFile, beforeExecution);
        TextFileUtils.outPutFile(pathnameFile, afterExecution);
    }
    
    /** 
     * @description ������һ�仰�����������������
     * @author ������ 2018-06-27
     * @return
     */ 
    public List<Map<String, Object>> getSqlList() {
        beforeExecution.addAll(afterExecution);
        return beforeExecution;
    }
    
    /**
     * get  set 
     */
    public String getInsertPlaceholderStr() {
        return insertPlaceholderStr;
    }

    public void setInsertPlaceholderStr(String insertPlaceholderStr) {
        this.insertPlaceholderStr = insertPlaceholderStr;
    }

    public String getInsertString() {
        return insertString;
    }

    public void setInsertString(String insertString) {
        this.insertString = insertString;
    }

    /** 
     * @description ��ʼ�����϶���(SQLִ�й���ɾ��)
     * @author ������ 2018-07-12
     * @return
     */ 
	public void init() {
		if(beforeExecution.size() != 0){
			beforeExecution.clear();
		}
		if(afterExecution.size() != 0){
			afterExecution.clear(); 
		}
	}

}
