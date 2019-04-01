/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */
package main.grammar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @description ��ȡ�����·�֮����·�
 * 
 * @author ������
 * @date 2018-07-30
 */
public class ��ȡ�����·�֮����·� {
    private static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");//��ʽ��Ϊ����

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        //�����������Ҫ��Ϊ�������·ݲ���ʡ��
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        //��ǰʱ���ǲ�����ĳ��ʱ��֮ǰ
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
