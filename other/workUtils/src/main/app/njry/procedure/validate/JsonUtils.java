/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.app.njry.procedure.validate;

import java.util.List;

import main.helper.json.JsonArrayHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/** 
 * @description Json的工具类,用于生成Json字符串,或生成Json对象
 * @author 陈吕奖
 * @date 2018-08-15 
 */
public class JsonUtils {

    /**
     * VaildateProUtil对象调用
     * @description 将List<String[]>集合转换为一个Json数组字符串,
     * @author 陈吕奖 2018-08-15
     * @return
     */ 
    public static String getJsonStrByList(List<String[]> list){
        //获取一列列的数据,封装成伪json对象
        //拼接成json字符串
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for(int i = 0; i< list.size(); i++){
            String[] context = list.get(i);
            sb.append("{");
            for(String string : context) {
                sb.append(string+",");
            }
            sb.append("}, ");
        }
        //去除最后的,
        String str = sb.substring(0,sb.length() - 2)+ "]";
        //去除每项结尾的多处的,
        str = str.replaceAll(",}", "}");
        return str;
    }
    
    /** 
     * @description 将符合json字符串格式的String[]数组集合转换为JsonArray
     * ({"aa":"aa","aa":"dd"},{"dd":"d","dd":"dd"})
     * @author 陈吕奖 2018-08-21
     * @param list
     * @return
     */ 
    public static JsonArrayHelper getJsonArray(List<String[]> list){
        //jsonArray  相当于List中放了Map
        JSONArray jsonArray = JSONArray.fromObject(getJsonStrByList(list));
        return new JsonArrayHelper(jsonArray);
    }
    
    /** 
     * @description 根据Object返回一个JSONObject
     * @author 陈吕奖 2018-08-27
     * @param obj
     * @return
     */ 
    public static JSONObject getJSONObject(Object obj){
        JSONObject json = JSONObject.fromObject(obj);
        return json;
    }
    
}
