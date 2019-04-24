/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.app.njry.procedure.validate;

import java.util.List;

import main.helper.json.JsonArrayHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/** 
 * @description Json�Ĺ�����,��������Json�ַ���,������Json����
 * @author ������
 * @date 2018-08-15 
 */
public class JsonUtils {

    /**
     * VaildateProUtil�������
     * @description ��List<String[]>����ת��Ϊһ��Json�����ַ���,
     * @author ������ 2018-08-15
     * @return
     */ 
    public static String getJsonStrByList(List<String[]> list){
        //��ȡһ���е�����,��װ��αjson����
        //ƴ�ӳ�json�ַ���
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
        //ȥ������,
        String str = sb.substring(0,sb.length() - 2)+ "]";
        //ȥ��ÿ���β�Ķദ��,
        str = str.replaceAll(",}", "}");
        return str;
    }
    
    /** 
     * @description ������json�ַ�����ʽ��String[]���鼯��ת��ΪJsonArray
     * ({"aa":"aa","aa":"dd"},{"dd":"d","dd":"dd"})
     * @author ������ 2018-08-21
     * @param list
     * @return
     */ 
    public static JsonArrayHelper getJsonArray(List<String[]> list){
        //jsonArray  �൱��List�з���Map
        JSONArray jsonArray = JSONArray.fromObject(getJsonStrByList(list));
        return new JsonArrayHelper(jsonArray);
    }
    
    /** 
     * @description ����Object����һ��JSONObject
     * @author ������ 2018-08-27
     * @param obj
     * @return
     */ 
    public static JSONObject getJSONObject(Object obj){
        JSONObject json = JSONObject.fromObject(obj);
        return json;
    }
    
}
