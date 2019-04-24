/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.utils.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/** 
 * @description ��ȡʱ��Ĺ�����
 * @author ������
 * @date 2018-06-15 
 */
public class DateUtils {

    
    /** 
     * @description ��ȡ��ǰ��ʱ��
     * @author ������ 2018-06-15
     * @param pattern ʱ���������ʽ
     * @return
     */ 
    public static String getToday(String pattern)
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(cal.getTime());
    }
    
    /** 
     * @description ��ȡ�����·�֮����·�(����ͷ,����β)
     * @author ������ 2018-07-30
     * @param minDate
     * @param maxDate
     * @return
     * @throws ParseException
     */ 
    public List<String> getMonthBetween(String minDate, String maxDate) {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");//��ʽ��Ϊ����
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        try {
            min.setTime(sdf.parse(minDate));
            //�����������Ҫ��Ϊ�������·ݲ���ʡ��(��Ҫ������date��ֵ)
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
            max.setTime(sdf.parse(maxDate));
          //�����������Ҫ��Ϊ�������·ݲ���ʡ��(��Ҫ������date��ֵ)
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar curr = min;
        //��ǰʱ���ǲ�����ĳ��ʱ��֮ǰ
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
