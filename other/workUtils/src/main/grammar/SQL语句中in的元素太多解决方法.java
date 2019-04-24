/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */
package main.grammar;

import java.util.ArrayList;
import java.util.List;

/**
 * @description select in (...),in的数量过多处理
 * @author 陈吕奖
 * @date 2018-07-30
 */
public class SQL语句中in的元素太多解决方法 {
    
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("aaa");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("ddd");        
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("ddd");        
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("ddd");        
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("sss");
        list.add("ddd");
        System.out.println(list.size());
        System.out.println(list.size());
        getString("1",list);
    }
    
    private static String getString(String id, List<String> list) {
        StringBuffer sb = new StringBuffer();
        String returnString = "";
        if(list.size() == 0 || null == list) {
            returnString = sb.append(id).append("=''").toString();
        }
        for(int i = 0; i < list.size(); i++) {
            if(i == 0) {
                sb.append(id);
                sb.append(" in (");
            }
            sb.append("'");
            sb.append(list.get(i).toString());
            sb.append("'");
            if(i >= 20 && i < list.size() - 1) {
                if(i % 20 == 0) {
                    sb.append(") or ");
                    sb.append(id);
                    sb.append(" in (");
                } else {
                    sb.append(",");
                }
            } else {
                if(i < list.size() - 1) {
                    sb.append(",");
                }
            }
            if(i == list.size() - 1) {
                sb.append(")");
            }
        }
        returnString = sb.toString();
        System.out.println(returnString);
        return returnString;
    }
}
