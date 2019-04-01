/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.helper.database.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.utils.file.TextFileUtils;

/** 
 * Batch   批处理
 * @description SQL语句集合,存储SQL语句统一执行
 * @author 陈吕奖    
 * @date 2018-06-05 
 */
public class BatchSql {

    //插入语句前半段
    private String insertPlaceholderStr;//占位符班
    private String insertString;//
    
    //sql  ? ? ?    与   object中参数对应
    private List<Map<String, Object>> beforeExecution = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> afterExecution = new ArrayList<Map<String, Object>>();
    /*
     * 两个list用户区分某些部分的SQL语句
     * 有些必须先执行,有些必须后执行
     * list1先执行
     * list2后执行
     */
    
    public void addBatch(String sql)
    {  
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sql", sql);
        //没有参数,创建一个空的数组
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
        //没有参数,创建一个空的数组
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
     * @description 显示集合中的SQL语句,数据量小的时候使用
     * @author 陈吕奖 2018-06-06
     */ 
    public void showSql(){
        ////如果数据很多 控制台将无法全部的显示, 将数据存储到text中
        for(Map map : beforeExecution) {
            System.out.println(map.get("sql"));
        }
        for(Map map : afterExecution) {
            System.out.println(map.get("sql"));
        }

    }

    public void outPutToFile(String pathnameFile){
        //C:\\Users\\Administrator\\Desktop\\报表excel文件\\临时跑数据\\1.txt
        TextFileUtils.outPutFile(pathnameFile, beforeExecution);
        TextFileUtils.outPutFile(pathnameFile, afterExecution);
    }
    
    /** 
     * @description 这里用一句话描述这个方法的作用
     * @author 陈吕奖 2018-06-27
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
     * @description 初始化集合对象(SQL执行过后删除)
     * @author 陈吕奖 2018-07-12
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
