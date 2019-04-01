/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.utils.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/** 
 * @description 获取时间的工具类
 * @author 陈吕奖
 * @date 2018-06-15 
 */
public class DateUtils {

    
    /** 
     * @description 获取当前的时间
     * @author 陈吕奖 2018-06-15
     * @param pattern 时间的正则表达式
     * @return
     */ 
    public static String getToday(String pattern)
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(cal.getTime());
    }
    
    /** 
     * @description 获取两个月份之间的月份(包含头,包含尾)
     * @author 陈吕奖 2018-07-30
     * @param minDate
     * @param maxDate
     * @return
     * @throws ParseException
     */ 
    public List<String> getMonthBetween(String minDate, String maxDate) {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");//格式化为年月
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        try {
            min.setTime(sdf.parse(minDate));
            //这里的设置主要是为了最大的月份不被省略(主要是设置date的值)
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
            max.setTime(sdf.parse(maxDate));
          //这里的设置主要是为了最大的月份不被省略(主要是设置date的值)
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar curr = min;
        //当前时间是不是在某个时间之前
        while(curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }
        return result;
    }
    
    public static void main(String[] args) {
        System.out.println((new DateUtils()).getToday("yyyMMdd"));
    }
}
