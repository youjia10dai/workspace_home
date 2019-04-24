/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.utils.common;

import java.util.List;
import main.app.njry.procedure.source.ForEach;

/** 
 * @description List
 * @author ������
 * @date 2018-10-18 
 */
public class ListUtils {

    
    /** 
     * @description �ص�����forEach
     * @author ������ 2018-10-19
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
     * @description ��һ��List<String>����ת����һ��String�ַ���
     * @author ������ 2018-10-19
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
