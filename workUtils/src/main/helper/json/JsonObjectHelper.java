/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.helper.json;


import java.util.Set;
import net.sf.json.JSONObject;

/** 
 * @description JSONObject�Ľṹ��Map����(ʹ��Mapʵ��  Properties��ֻ�����ַ���)
 * ���Ի�ȡĳ��key��ֵ,Ҳ�������һ��keyֵ
 * @author ������
 * @date 2018-08-17 
 */
public class JsonObjectHelper {

    public JSONObject jsonObj;
    
    public JsonObjectHelper(JSONObject jsonObj){
        this.jsonObj = jsonObj;
    }

    /** 
     * @description ��Json�ַ������������
     * @author ������ 2018-08-21
     * @param str1
     * @param str2
     */ 
    public void put(String str1, String str2){
        jsonObj.element(str1, str2);
    }
    
    /** 
     * @description ��ȡkey��Ӧ��valueֵ
     * @author ������ 2018-08-21
     * @param key
     * @return
     */ 
    public String get(String key){
        String value = jsonObj.getString(key);
        return value;
    }
    
    /** 
     * @description ���ַ����������ʽ�������е�key
     * @author ������ 2018-08-21
     * @return
     */ 
    public String[] keySet(){
        String[] keys = new String[jsonObj.size()];
        int i = 0;
        Set<?> keySet = jsonObj.keySet();
        for(Object object : keySet) {
            keys[i++] = object.toString();
        }
        return keys;
    }
    
    /** 
     * @description ����class�ļ�,��������
     * @author ������ 2018-08-27
     * @param clz
     * @return
     */ 
    @SuppressWarnings("unchecked")
    public <T> T toBean(Class<T> clz){
        return (T)JSONObject.toBean(jsonObj, clz);
    }
    
    @Override
    public String toString(){
        if(jsonObj != null){
            return jsonObj.toString();
        }else{
            return "";
        }
    }
}
