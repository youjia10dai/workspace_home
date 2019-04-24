/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */
package main.source.json;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @description 原理: JsonArray是通过List实现的,  List中可以放Map
 * 可以实现部分解析(jsonArray转换为多个JSONObject)
 * @author 陈吕奖
 * @date 2018-08-06
 */
public class TestJsonArray {
    
    public static void testJSONArray() {
        String array = "[\"123\",\"this is my test\",{\"address\":\"北京市朝阳区\",\"age\":\"23\",\"name\":\"JSON\"}]";
        JSONArray jsonArray = JSONArray.fromObject(array);
        //由于JSONArray底层是数组，所以这里使用下标的方式来访问其中的元素
        String str1 = jsonArray.getString(0);
        String str2 = jsonArray.getString(1);
        String str3 = jsonArray.getString(2);
        System.out.println("str1:" + str1 + ",str2:" + str2 + ",str3:" + str3);
        //由于第三个元素本身是一个对象形式的JSON串，可以这样获得第三个元素
        JSONObject o3 = jsonArray.getJSONObject(2);//获得JSONObject对象
        System.out.println("address:" + o3.getString("address") + ",age:" + o3.getString("age"));
    }

    public static void main(String[] args) {
        testJSONArray();
    }
}
