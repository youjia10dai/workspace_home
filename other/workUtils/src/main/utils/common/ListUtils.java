/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.utils.common;

import java.util.List;
import main.app.njry.procedure.source.ForEach;

/** 
 * @description List
 * @author 陈吕奖
 * @date 2018-10-18 
 */
public class ListUtils {

    
    /** 
     * @description 回调函数forEach
     * @author 陈吕奖 2018-10-19
     * @param <T>
     * @param list
     * @param callback
     */ 
    public static <T> void forEach(List<T> list, ForEach<T> callback){
        for(int i = 0; i < list.size(); i++)
        {
            callback.forEach(list, i, list.get(i));
        }
    }
    
    /** 
     * @description 将一个List<String>集合转换成一个String字符串
     * @author 陈吕奖 2018-10-19
     * @param list
     * @return
     */ 
    public static String getString(List<String> list){
        StringBuffer sb = new StringBuffer();
        for(String string : list) {
            sb.append(string);
        }
        return sb.toString();
    }
    
}
