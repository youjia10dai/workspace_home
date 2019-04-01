/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.helper.json;

import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
 * @description JSONArray是通过list来实现的
 * 可以获取到指定下标json字符串,或Json对象
 * 我们可以封装成像操作list集合一样操作JSONArray
 * @author 陈吕奖
 * @date 2018-08-17 
 */
public class JsonArrayHelper {

    public JSONArray jsonArr;
    
    public JsonArrayHelper(JSONArray jsonArr){
        this.jsonArr = jsonArr;
    }
    
    /** 
     * @description 返回指定下标的Json帮助对象(默认情况下get获取对象)
     * @author 陈吕奖 2018-08-21
     * @param index
     * @return
     */ 
    public JsonObjectHelper get(int index) {
        //由于第三个元素本身是一个对象形式的JSON串，可以这样获得第三个元素
        JSONObject obj = jsonArr.getJSONObject(index);//获得JSONObject对象
        return new JsonObjectHelper(obj);
    }
    
    /** 
     * @description 获取json属性的个数
     * @author 陈吕奖 2018-08-21
     * @return
     */ 
    public int size(){
        return jsonArr.size();
    }
    
    /** 
     * @description 返回指定下标的Json字符串
     * @author 陈吕奖 2018-08-21
     * @param index
     * @return
     */ 
    public String getStr(int index){
        //由于JSONArray底层是数组，所以这里使用下标的方式来访问其中的元素
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
