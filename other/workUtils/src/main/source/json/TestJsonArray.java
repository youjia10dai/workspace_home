/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */
package main.source.json;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @description ԭ��: JsonArray��ͨ��Listʵ�ֵ�,  List�п��Է�Map
 * ����ʵ�ֲ��ֽ���(jsonArrayת��Ϊ���JSONObject)
 * @author ������
 * @date 2018-08-06
 */
public class TestJsonArray {
    
    public static void testJSONArray() {
        String array = "[\"123\",\"this is my test\",{\"address\":\"�����г�����\",\"age\":\"23\",\"name\":\"JSON\"}]";
        JSONArray jsonArray = JSONArray.fromObject(array);
        //����JSONArray�ײ������飬��������ʹ���±�ķ�ʽ���������е�Ԫ��
        String str1 = jsonArray.getString(0);
        String str2 = jsonArray.getString(1);
        String str3 = jsonArray.getString(2);
        System.out.println("str1:" + str1 + ",str2:" + str2 + ",str3:" + str3);
        //���ڵ�����Ԫ�ر�����һ��������ʽ��JSON��������������õ�����Ԫ��
        JSONObject o3 = jsonArray.getJSONObject(2);//���JSONObject����
        System.out.println("address:" + o3.getString("address") + ",age:" + o3.getString("age"));
    }

    public static void main(String[] args) {
        testJSONArray();
    }
}
