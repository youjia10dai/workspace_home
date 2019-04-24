/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
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
 * @description 正则表达式工具类
 * @author 陈吕奖
 * @date 2018-08-27 
 */
public class PatternUtils {

    //自动生成验证过程时使用
    public static String reg = "\\{name\\}|\\{tipRange\\}|\\{columnName\\}|\\{sqlRange\\}";
    public static Logger log = Logger.getLogger(PatternUtils.class);
    /**
     * @description 根据正则表达式替换字符串的内容,将找到的字符串根据内容
     * 替换成不同的数据
     * @author 陈吕奖 2018-08-27
     * @param context
     * @param regExpStr
     * @param map
     * @return
     */
    public static String replaceAll(String context, String regExpStr, JsonObjectHelper map){
        //输出原来的内容
//        System.out.println(context);
        //创建正则对象
        Pattern regExp = Pattern.compile(regExpStr);
        Matcher match = regExp.matcher(context);
        StringBuffer sb = new StringBuffer();
        while (match.find()) {
            //将替换后的内容存入sb中
            match.appendReplacement(sb,map.get(getReplaceString(context,match)));
        }
        //拼上后半部分
        match.appendTail(sb);
        return sb.toString();
    }
    
    private static String getReplaceString(String context, Matcher match){
        return context.substring(match.start()+1, match.end()-1);
    }
    
    /**
     * @description 截取字符串中匹配的内容 截取的内容为 ()中间的内容
     * @author 陈吕奖 2018-09-30
     * @param regex    正则表达式
     * @param context  用于匹配的字符串
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
     * @description 根据正则表达式,返回匹配的内容 的字符数组  改(groupIndex)
     * @author 陈吕奖 2018-09-25
     * @param regx       正则表达式
     * @param context    用于匹配的字符数组
     * @param groupIndex 组id   三种状态 1. 0的时候返回 整体符合的字符串 
     *                                 2. -1的时候返回全部组数组(包含符合的字符串) 
     *                                 3. >0时返回单个组
     * @param type 表示是否全量匹配,  0 表示全量匹配    1表示部分匹配(找到一个就返回)
     * @return
     */ 
    public static String[] getMatcherStr(String regex, List<String> context, int groupIndex, int type){
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);//创建正则对象,忽略大小写
        Matcher matcher = p.matcher("");//根据正则对象创建匹配器
        List<String> list = new ArrayList<String>();
        String string = "";
        for(int j = 0; j < context.size(); j++){
            string = context.get(j);
            matcher.reset(string);//替换用于匹配的字符串
            while(matcher.find()){//进行查找
                if(groupIndex < 0){
                    for(int i = 0; i<=matcher.groupCount(); i++ ){
                        //添加符合字符串中的所有组
                        list.add(matcher.group(i));
                    }
                }else if(groupIndex == 0){
                    //添加整个符合的字符串
                    list.add(matcher.group(0));
                }else {
                    list.add(matcher.group(groupIndex));
                }
                if(type == 1)
                    //这个应用于获取过程项时,只要一个满足就返回
                    return list.toArray(new String[]{});
            }
        }
        return list.toArray(new String[]{});
    }
    
    public static void main(String[] args) {
        replaceAll(VaildateSql.requireRange,reg,null);
    }
}
