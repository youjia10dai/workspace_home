/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */
package main.grammar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @description 获取两个月份之间的月份
 * 
 * @author 陈吕奖
 * @date 2018-07-30
 */
public class 获取两个月份之间的月份 {
    private static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        //这里的设置主要是为了最大的月份不被省略
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        //当前时间是不是在某个时间之前
        while(curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    public static void main(String[] args) throws ParseException {
        List<String> list = getMonthBetween("201701", "201802");
        for(String string : list) {
            System.out.println(string);
        }
    }
}
