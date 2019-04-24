/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.source.json;

import java.util.Set;

import net.sf.json.JSONObject;

/** 
 * @description 原理:JSONObject是通过Map实现的
 * @author 陈吕奖
 * @date 2018-08-06 
 */
public class TestJsonObject {
    /*
     * JSONObject是使用Map实现的
     * 
     */
    /* 测试JSONObject(是使用Map实现的)
     * 解析完成后,还可以手动的添加数据
     * 
     */
    
    //往map里面添加数据
    public static void TestJSONObject(){
        //创建一个JSONObject对象
        JSONObject jsonObject = new JSONObject();
        jsonObject.element("my", "this is my first");
        String str = jsonObject.toString();
        String keyValue = jsonObject.getString("my");
        System.out.println("str:"+str);
        System.out.println("keyValue:"+keyValue);
    }
    
    //获取Map中的数据
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
