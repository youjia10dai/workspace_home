/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.source.json;

import java.util.Set;

import net.sf.json.JSONObject;

/** 
 * @description ԭ��:JSONObject��ͨ��Mapʵ�ֵ�
 * @author ������
 * @date 2018-08-06 
 */
public class TestJsonObject {
    /*
     * JSONObject��ʹ��Mapʵ�ֵ�
     * 
     */
    /* ����JSONObject(��ʹ��Mapʵ�ֵ�)
     * ������ɺ�,�������ֶ����������
     * 
     */
    
    //��map�����������
    public static void TestJSONObject(){
        //����һ��JSONObject����
        JSONObject jsonObject = new JSONObject();
        jsonObject.element("my", "this is my first");
        String str = jsonObject.toString();
        String keyValue = jsonObject.getString("my");
        System.out.println("str:"+str);
        System.out.println("keyValue:"+keyValue);
    }
    
    //��ȡMap�е�����
    public static void getValue(){
        String str="{\"my\":\"this is my first\",\"second\":\"this is second!\"}";
        JSONObject jsonObject=JSONObject.fromObject(str);
        Set<Object> entrySet = jsonObject.keySet();
        for(Object string : entrySet) {
            System.out.println(jsonObject.get(string));
        }
        String my=jsonObject.getString("my");
        String second=jsonObject.getString("second");
        System.out.println("my:"+my);
        System.out.println("secode:"+second);
    }
    
    public static void main(String[] args) {
        getValue();
    }
}
