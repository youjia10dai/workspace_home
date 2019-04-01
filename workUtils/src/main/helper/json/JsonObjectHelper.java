/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.helper.json;


import java.util.Set;
import net.sf.json.JSONObject;

/** 
 * @description JSONObject的结构跟Map类似(使用Map实现  Properties都只操作字符串)
 * 可以获取某个key的值,也可以添加一个key值
 * @author 陈吕奖
 * @date 2018-08-17 
 */
public class JsonObjectHelper {

    public JSONObject jsonObj;
    
    public JsonObjectHelper(JSONObject jsonObj){
        this.jsonObj = jsonObj;
    }

    /** 
     * @description 往Json字符串中添加数据
     * @author 陈吕奖 2018-08-21
     * @param str1
     * @param str2
     */ 
    public void put(String str1, String str2){
        jsonObj.element(str1, str2);
    }
    
    /** 
     * @description 获取key对应的value值
     * @author 陈吕奖 2018-08-21
     * @param key
     * @return
     */ 
    public String get(String key){
        String value = jsonObj.getString(key);
        return value;
    }
    
    /** 
     * @description 以字符串数组的形式返回所有的key
     * @author 陈吕奖 2018-08-21
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
     * @description 根据class文件,创建对象
     * @author 陈吕奖 2018-08-27
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
