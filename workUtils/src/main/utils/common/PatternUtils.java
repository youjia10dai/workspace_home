/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.utils.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import main.app.njry.procedure.validate.entity.VaildateSql;
import main.helper.json.JsonObjectHelper;
import org.apache.log4j.Logger;

/** 
 * @description ������ʽ������
 * @author ������
 * @date 2018-08-27 
 */
public class PatternUtils {

    //�Զ�������֤����ʱʹ��
    public static String reg = "\\{name\\}|\\{tipRange\\}|\\{columnName\\}|\\{sqlRange\\}";
    public static Logger log = Logger.getLogger(PatternUtils.class);
    /**
     * @description ����������ʽ�滻�ַ���������,���ҵ����ַ�����������
     * �滻�ɲ�ͬ������
     * @author ������ 2018-08-27
     * @param context
     * @param regExpStr
     * @param map
     * @return
     */
    public static String replaceAll(String context, String regExpStr, JsonObjectHelper map){
        //���ԭ��������
//        System.out.println(context);
        //�����������
        Pattern regExp = Pattern.compile(regExpStr);
        Matcher match = regExp.matcher(context);
        StringBuffer sb = new StringBuffer();
        while (match.find()) {
            //���滻������ݴ���sb��
            match.appendReplacement(sb,map.get(getReplaceString(context,match)));
        }
        //ƴ�Ϻ�벿��
        match.appendTail(sb);
        return sb.toString();
    }
    
    private static String getReplaceString(String context, Matcher match){
        return context.substring(match.start()+1, match.end()-1);
    }
    
    /**
     * @description ��ȡ�ַ�����ƥ������� ��ȡ������Ϊ ()�м������
     * @author ������ 2018-09-30
     * @param regex    ������ʽ
     * @param context  ����ƥ����ַ���
     * @return
     */
    public static String getMatcherStr(String regex, String context){
        String[] matcherStr = getMatcherStr(regex, Arrays.asList(new String[]{context}), 1, 1);
        if(matcherStr.length != 0)
            return matcherStr[0];
        else 
            return "";
    }
    
    /** 
     * @description ����������ʽ,����ƥ������� ���ַ�����  ��(groupIndex)
     * @author ������ 2018-09-25
     * @param regx       ������ʽ
     * @param context    ����ƥ����ַ�����
     * @param groupIndex ��id   ����״̬ 1. 0��ʱ�򷵻� ������ϵ��ַ��� 
     *                                 2. -1��ʱ�򷵻�ȫ��������(�������ϵ��ַ���) 
     *                                 3. >0ʱ���ص�����
     * @param type ��ʾ�Ƿ�ȫ��ƥ��,  0 ��ʾȫ��ƥ��    1��ʾ����ƥ��(�ҵ�һ���ͷ���)
     * @return
     */ 
    public static String[] getMatcherStr(String regex, List<String> context, int groupIndex, int type){
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);//�����������,���Դ�Сд
        Matcher matcher = p.matcher("");//����������󴴽�ƥ����
        List<String> list = new ArrayList<String>();
        String string = "";
        for(int j = 0; j < context.size(); j++){
            string = context.get(j);
            matcher.reset(string);//�滻����ƥ����ַ���
            while(matcher.find()){//���в���
                if(groupIndex < 0){
                    for(int i = 0; i<=matcher.groupCount(); i++ ){
                        //��ӷ����ַ����е�������
                        list.add(matcher.group(i));
                    }
                }else if(groupIndex == 0){
                    //����������ϵ��ַ���
                    list.add(matcher.group(0));
                }else {
                    list.add(matcher.group(groupIndex));
                }
                if(type == 1)
                    //���Ӧ���ڻ�ȡ������ʱ,ֻҪһ������ͷ���
                    return list.toArray(new String[]{});
            }
        }
        return list.toArray(new String[]{});
    }
    
    public static void main(String[] args) {
        replaceAll(VaildateSql.requireRange,reg,null);
    }
}
