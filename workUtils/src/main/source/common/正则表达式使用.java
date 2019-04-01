/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.source.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/** 
 * @description Pattern 和 Matcher 两个对象的使用
 * @author 陈吕奖
 * @date 2018-09-25 
 */
public class 正则表达式使用 {

    public static void main(String[] args) {
        //Pattern 对象  **************************** 
        //类用于创建一个正则表达式，也可以说是创建一个匹配模式 
        //两种创建方式 compile(String regex)和compile(String regex,int flags)
        //regex是正则表达式，flags为可选模式(如：Pattern.CASE_INSENSITIVE 忽略大小写)
        Pattern regx = Pattern.compile("Java", Pattern.CASE_INSENSITIVE);
        
        //输出正则表达式
        System.out.println(regx.pattern());
        System.out.println(regx.toString());
        
        String test="123Java456Java789Java";
        
        //进行分割字符串
        //当limit值大于所能返回的字符串的最多个数或者为负数，返回的字符串个数将不受限制，
        //但结尾可能包含空串，而当limit=0时与split(CharSequence input)等价，但结尾的空串会被丢弃。
        //regx.split(test)结尾的""默认被删除
        String[] split = regx.split(test, -1);
        System.out.println(split.length);
        for(String string : split) {
            System.out.println(string);
        }
        
        //Pattern 类的Matches方法
        //Pattern类也自带一个静态匹配方法matches(String regex, CharSequence input)，
        //但只能进行 全字符串匹配 并且只能返回是否匹配上的boolean值
        System.out.println(Pattern.matches(".{3}Java.+", test));
        
        //Matcher  对象***********************
        //Pattern类中的matcher(CharSequence input)会返回一个Matcher对象
        //Matcher类提供了对正则表达式的分组支持,以及对正则表达式的多次匹配支持，要想得到更丰富的正则匹配操作,那就需要将Pattern与Matcher联合使用。
        //Matcher类提供了三个返回boolean值得匹配方法：matches()，lookingAt()，find()，find(int start)，其中matches()用于全字符串匹配，
        //lookingAt从字符串最开头开始匹配满足的子串，find可以对任意位置字符串匹配,其中start为起始查找索引值。
        //matches()字符串完全匹配      lookingAt(从0开始是否有满足的子串)     find(子串)
        Pattern pattern = Pattern.compile("Java");
        String test1 = "Java";
        String test2 = "Java1234";
        String test3 = "1234Java";
        Matcher matcher = pattern.matcher(test1);
        System.out.println("**********");
        System.out.println(matcher.matches());//返回true
        matcher = pattern.matcher(test2);
        System.out.println(matcher.matches());//返回false
        matcher = pattern.matcher(test2);
        System.out.println(matcher.lookingAt());//返回true
        matcher = pattern.matcher(test3);
        System.out.println(matcher.lookingAt());//返回true
        matcher = pattern.matcher(test1);
        System.out.println(matcher.find());//返回true
        matcher = pattern.matcher(test2);
        System.out.println(matcher.find());//返回true
        matcher = pattern.matcher(test3);
        System.out.println(matcher.find(2));//返回true
        matcher = pattern.matcher(test3);
        System.out.println(matcher.find(5));//返回false
        
        //正则表达式中组的概念 
        //组是用括号划分的正则表达式，可以根据组的编号来引用这个组。
        //组号为0表示整个表达式，组号为1表示被第一对括号括起的组，
        //依次类推，例如A(B(C))D，组0是ABCD，组1是BC，组2是C。 
        //Matcher类提供了start()，end()，group()分别用于返回
        //字符串的起始索引，结束索引，以及匹配到到的字符串。
        Pattern pattern1 = Pattern.compile("Java");
        String test11 = "123Java456";
        Matcher matcher1 = pattern1.matcher(test11);
        matcher1.find();
        System.out.println("+++++++++++++++");
        System.out.println(matcher1.start());//返回3
        System.out.println(matcher1.end());//返回7
        System.out.println(matcher1.group());//返回Java
        
        
        //分组操作---
        //Matcher类提供了start(int gropu)，end(int group)，group(int i)，groupCount()用于分组操作
        Pattern pattern2 = Pattern.compile("(Java)(Python)");
        String test22 = "123JavaPython456";
        Matcher matcher2 = pattern2.matcher(test22);
        matcher2.find();
        System.out.println(matcher2.groupCount());//返回2
        System.out.println(matcher2.group(1));//返回第一组匹配到的字符串"Java"，注意起始索引是1
        System.out.println(matcher2.start(1));//返回3，第一组起始索引
        System.out.println(matcher2.end(1));//返回7 第一组结束索引
        System.out.println(matcher2.group(2));//返回第二组匹配到的字符串"Python"
        System.out.println(matcher2.start(2));//返回7，第二组起始索引
        System.out.println(matcher2.end(2));//返回13 第二组结束索引
        Pattern p=Pattern.compile("([a-z]+)(\\d+)"); 
        Matcher m=p.matcher("aaa2223bb"); 
        m.find();   //匹配aaa2223 
        m.groupCount();   //返回2,因为有2组 
        m.start(1);   //返回0 返回第一组匹配到的子字符串在字符串中的索引号
        m.start(2);   //返回3             包含头不包含尾
        System.out.println(m.end(2));   //返回3 返回第一组匹配到的子字符串的最后一个字符在字符串中的索引位置. m.end(2);   //返回7 
        m.group(1);   //返回aaa,返回第一组匹配到的子字符串 
        m.group(2);   //返回2223,返回第二组匹配到的子字符串
        
        
        //Matcher类还提供region(int start, int end)(不包括end)方法用于设定查找范围，并提供regionStrat()和regionEnd()用于返回起始和结束查找的索引
        Pattern pattern3 = Pattern.compile("Java");
        String test33 = "123JavaJava";
        Matcher matcher3 = pattern3.matcher(test33);
        matcher3.region(7, 11);
        System.out.println(matcher3.regionStart());//返回7
        System.out.println(matcher3.regionEnd());//返回11
        matcher3.find();
        System.out.println(matcher3.group());//返回Java
        
        //Matcher类提供了两种用于重置当前匹配器的方法:reset()和reset(CharSequence input) 替换匹配的字符串
        Pattern pattern4 = Pattern.compile("Java");
        String test4 = "Java";
        Matcher matcher4 = pattern4.matcher(test4);
        matcher4.find();
        System.out.println(matcher4.group());//返回Java
        matcher4.reset();//从起始位置重新匹配
        matcher4.find();
        System.out.println(matcher4.group());//返回Java
        matcher4.reset("Python");
        System.out.println(matcher4.find());//返回false
        
        //Matcher类的匹配方法：replaceAll(String replacement) 和 replaceFirst(String replacement)，
        //其中replaceAll是替换全部匹配到的字符串，而replaceFirst仅仅是替换第一个匹配到的字符串。

        Pattern pattern5 = Pattern.compile("Java");
        String test5 = "JavaJava";
        Matcher matcher5 = pattern5.matcher(test5);
        System.out.println(matcher5.replaceAll("Python"));//返回PythonPython
        System.out.println(matcher5.replaceFirst("python"));//返回PythonJava
        
        //两个方法appendReplacement(StringBuffer sb, String replacement) 和 
        //appendTail(StringBuffer sb)也很重要，appendReplacement允许直接将匹配的字符串保存在另一个
        //StringBuffer中并且是渐进式匹配，并不是只匹配依次或匹配全部,
        //而appendTail则是将未匹配到的余下的字符串添加到StringBuffer中。
        Pattern pattern6 = Pattern.compile("Java");
        Matcher matcher6 = pattern6.matcher("JavaJavaJava1234");
        StringBuffer sb = new StringBuffer();
        System.out.println();//返回true
        while(matcher6.find()){
            System.out.println(matcher6.group());//获取匹配
            matcher6.appendReplacement(sb, "Python");//将匹配到的字符串,依次替换
            System.out.println(sb);//输出Python
        }
        matcher6.appendTail(sb);
        System.out.println(sb);//输出Python1234
    }
}
