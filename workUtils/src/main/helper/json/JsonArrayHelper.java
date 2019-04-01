/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.helper.json;

import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
 * @description JSONArray��ͨ��list��ʵ�ֵ�
 * ���Ի�ȡ��ָ���±�json�ַ���,��Json����
 * ���ǿ��Է�װ�������list����һ������JSONArray
 * @author ������
 * @date 2018-08-17 
 */
public class JsonArrayHelper {

    public JSONArray jsonArr;
    
    public JsonArrayHelper(JSONArray jsonArr){
        this.jsonArr = jsonArr;
    }
    
    /** 
     * @description ����ָ���±��Json��������(Ĭ�������get��ȡ����)
     * @author ������ 2018-08-21
     * @param index
     * @return
     */ 
    public JsonObjectHelper get(int index) {
        //���ڵ�����Ԫ�ر�����һ��������ʽ��JSON��������������õ�����Ԫ��
        JSONObject obj = jsonArr.getJSONObject(index);//���JSONObject����
        return new JsonObjectHelper(obj);
    }
    
    /** 
     * @description ��ȡjson���Եĸ���
     * @author ������ 2018-08-21
     * @return
     */ 
    public int size(){
        return jsonArr.size();
    }
    
    /** 
     * @description ����ָ���±��Json�ַ���
     * @author ������ 2018-08-21
     * @param index
     * @return
     */ 
    public String getStr(int index){
        //����JSONArray�ײ������飬��������ʹ���±�ķ�ʽ���������е�Ԫ��
        String jsonStr = jsonArr.getString(index);
        return jsonStr;
    }
    
    @Override
    public String toString(){
        if(jsonArr != null){
            return jsonArr.toString();
        }else{
            return "";
        }
    }
}
