/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.utils.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/** 
 * @description �ַ�����صĹ�����
 * @author ������
 * @date 2018-06-06 
 */
public class StringUtils {

    /** 
     * @description ��϶��String[],������","����
     * @author ������ 2018-06-06
     * @param strings  String[]����
     * @return
     */ 
    public static String[] combString(String[] ... strings ){
        String[] combStr = new String[strings[0].length];
        for(int i = 0; i < strings.length; i++) 
        {
            for(int j = 0; j < combStr.length; j++)
            {
                if(combStr[j] == null){
                    combStr[j] = strings[i][j];
                }else{
                    combStr[j] += ","+strings[i][j];
                }
            }
        }
        return combStr;
    }
    
    /** 
     * @description ������л�ȡ����
     * @author ������ 2018-06-14
     * @param v_deleteStatement
     * @return
     */ 
    public static String getTableName(String deleteStatement){
        String v_deleteStatement = deleteStatement.toUpperCase();
        String tableNameString = null;
        if(v_deleteStatement.contains("DROPTABLE")||v_deleteStatement.contains("PROCEDURE")){
            tableNameString = deleteStatement.substring(deleteStatement.indexOf("(")+1, v_deleteStatement.lastIndexOf(")"));
        }else if(v_deleteStatement.contains("RENAME")){
            tableNameString = "'"+deleteStatement.substring(deleteStatement.indexOf("TO")+2, v_deleteStatement.lastIndexOf(";")).trim();
        }
        return tableNameString;
    }
    
    /** 
     * @description ���ڷָ��ַ���,���طָ���ķǿ�����
     * @author ������ 2018-06-15
     * @param str
     * @return
     */ 
    public static String[] split(String str, String regex){
        List<String> list = new ArrayList<String>();
        String[] split = str.split(regex);
        for(String string : split) {
            if(!"".equals(string)){
                list.add(string);
            }
        }
        return list.toArray(new String[]{});
    }
    
    /** 
     * @description �ж��ַ����Ƿ�Ϊ��
     * @author ������ 2018-06-15
     * @param str
     * @return
     */ 
    public static boolean isEmpty(String str){
        if(str == null || "".equals(str.trim())) {
            return true;
        }
        return false;
    }

    /** 
     * @description ��һ���ַ�������,�ָ������
     * @author ������ 2018-07-24
     * @param old
     * @param new1
     * @param new2
     */ 
    public static void splitString(String[] old, String[] new1, String[] new2){
        for(int i = 0; i < old.length; i++) {
            new1[i] = old[i].split(":")[0];
            new2[i] = old[i].split(":")[1];
        }
    }
    
    
    /** 
     * @description ����һ�������toString�ķ���ֵ,�������Ϊnull,�򷵻ؿյ��ַ���
     * @author ������ 2018-07-16
     * @param value
     * @return
     */ 
    public static String notEmpty(Object value){
        if(value == null){
            value = "";
        }
        String result="";
        try {
			result=String.valueOf(value);
		} catch (RuntimeException e) {
			result=(String)value;
		}
        return result;
    }
    
    /** 
     * @description  ���ִ����� 
     * ��ռλ��?�滻�ɾ���ֵ,���
     * @author ������ 2018-07-16
     * @param sql
     * @param params
     * @return
     */ 
    public static String getSql(String sql,Object ... params)
    {
        int idx=-1;
        int i=0;
        String tmp=sql;
        while((idx=tmp.indexOf("?"))>-1)
        {
            tmp=tmp.replaceFirst("\\?", "'"+toSql(params[i++]+"")+"'");    
        }
        return tmp;
        
    }
    
    private static String toSql(String str) {
        String sql = new String(str);
        return Replace(sql, "'", "''");
    }
    
    /** 
     * @description �Զ�����ַ����滻����
     * @author ������ 2018-07-16
     * @param source
     * @param oldString
     * @param newString
     * @return
     */ 
    private static String Replace(String source, String oldString, String newString) {
        StringBuffer output = new StringBuffer();

        int lengthOfSource = source.length();   // 
        int lengthOfOld = oldString.length();   // 

        int posStart = 0;   // 
        int pos;            // 

        while ((pos = source.indexOf(oldString, posStart)) >= 0) {
            output.append(source.substring(posStart, pos));

            output.append(newString);
            posStart = pos + lengthOfOld;
        }
        if (posStart < lengthOfSource) {
            output.append(source.substring(posStart));
        }
        return output.toString();
    }
    
    /** 
     * @description ���ַ���ת����int,���ת��ʧ�ܽ�����-1
     * @author ������ 2018-07-26
     * @param str
     * @return
     */ 
    public static int parseInt(String str){
        try {
            return Integer.parseInt(str);
        }
        catch (NumberFormatException e) {
        }
        return -1;
    }
    
    /** 
     * @description ����map�����ж�Ӧ��ֵ,����String��ʽ,�������Ϊ��,�����ؿ��ַ���
     * @author ������ 2018-07-17
     * @param map
     * @param keyName
     * @return
     */ 
    public static String get(Map map, String keyName) {
        return notEmpty(map.get(keyName));
    }
}
