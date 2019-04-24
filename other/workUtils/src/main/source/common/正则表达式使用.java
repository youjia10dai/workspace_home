/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.source.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/** 
 * @description Pattern �� Matcher ���������ʹ��
 * @author ������
 * @date 2018-09-25 
 */
public class ������ʽʹ�� {

    public static void main(String[] args) {
        //Pattern ����  **************************** 
        //�����ڴ���һ��������ʽ��Ҳ����˵�Ǵ���һ��ƥ��ģʽ 
        //���ִ�����ʽ compile(String regex)��compile(String regex,int flags)
        //regex��������ʽ��flagsΪ��ѡģʽ(�磺Pattern.CASE_INSENSITIVE ���Դ�Сд)
        Pattern regx = Pattern.compile("Java", Pattern.CASE_INSENSITIVE);
        
        //���������ʽ
        System.out.println(regx.pattern());
        System.out.println(regx.toString());
        
        String test="123Java456Java789Java";
        
        //���зָ��ַ���
        //��limitֵ�������ܷ��ص��ַ���������������Ϊ���������ص��ַ����������������ƣ�
        //����β���ܰ����մ�������limit=0ʱ��split(CharSequence input)�ȼۣ�����β�Ŀմ��ᱻ������
        //regx.split(test)��β��""Ĭ�ϱ�ɾ��
        String[] split = regx.split(test, -1);
        System.out.println(split.length);
        for(String string : split) {
            System.out.println(string);
        }
        
        //Pattern ���Matches����
        //Pattern��Ҳ�Դ�һ����̬ƥ�䷽��matches(String regex, CharSequence input)��
        //��ֻ�ܽ��� ȫ�ַ���ƥ�� ����ֻ�ܷ����Ƿ�ƥ���ϵ�booleanֵ
        System.out.println(Pattern.matches(".{3}Java.+", test));
        
        //Matcher  ����***********************
        //Pattern���е�matcher(CharSequence input)�᷵��һ��Matcher����
        //Matcher���ṩ�˶�������ʽ�ķ���֧��,�Լ���������ʽ�Ķ��ƥ��֧�֣�Ҫ��õ����ḻ������ƥ�����,�Ǿ���Ҫ��Pattern��Matcher����ʹ�á�
        //Matcher���ṩ����������booleanֵ��ƥ�䷽����matches()��lookingAt()��find()��find(int start)������matches()����ȫ�ַ���ƥ�䣬
        //lookingAt���ַ����ͷ��ʼƥ��������Ӵ���find���Զ�����λ���ַ���ƥ��,����startΪ��ʼ��������ֵ��
        //matches()�ַ�����ȫƥ��      lookingAt(��0��ʼ�Ƿ���������Ӵ�)     find(�Ӵ�)
        Pattern pattern = Pattern.compile("Java");
        String test1 = "Java";
        String test2 = "Java1234";
        String test3 = "1234Java";
        Matcher matcher = pattern.matcher(test1);
        System.out.println("**********");
        System.out.println(matcher.matches());//����true
        matcher = pattern.matcher(test2);
        System.out.println(matcher.matches());//����false
        matcher = pattern.matcher(test2);
        System.out.println(matcher.lookingAt());//����true
        matcher = pattern.matcher(test3);
        System.out.println(matcher.lookingAt());//����true
        matcher = pattern.matcher(test1);
        System.out.println(matcher.find());//����true
        matcher = pattern.matcher(test2);
        System.out.println(matcher.find());//����true
        matcher = pattern.matcher(test3);
        System.out.println(matcher.find(2));//����true
        matcher = pattern.matcher(test3);
        System.out.println(matcher.find(5));//����false
        
        //������ʽ����ĸ��� 
        //���������Ż��ֵ�������ʽ�����Ը�����ı������������顣
        //���Ϊ0��ʾ�������ʽ�����Ϊ1��ʾ����һ������������飬
        //�������ƣ�����A(B(C))D����0��ABCD����1��BC����2��C�� 
        //Matcher���ṩ��start()��end()��group()�ֱ����ڷ���
        //�ַ�������ʼ�����������������Լ�ƥ�䵽�����ַ�����
        Pattern pattern1 = Pattern.compile("Java");
        String test11 = "123Java456";
        Matcher matcher1 = pattern1.matcher(test11);
        matcher1.find();
        System.out.println("+++++++++++++++");
        System.out.println(matcher1.start());//����3
        System.out.println(matcher1.end());//����7
        System.out.println(matcher1.group());//����Java
        
        
        //�������---
        //Matcher���ṩ��start(int gropu)��end(int group)��group(int i)��groupCount()���ڷ������
        Pattern pattern2 = Pattern.compile("(Java)(Python)");
        String test22 = "123JavaPython456";
        Matcher matcher2 = pattern2.matcher(test22);
        matcher2.find();
        System.out.println(matcher2.groupCount());//����2
        System.out.println(matcher2.group(1));//���ص�һ��ƥ�䵽���ַ���"Java"��ע����ʼ������1
        System.out.println(matcher2.start(1));//����3����һ����ʼ����
        System.out.println(matcher2.end(1));//����7 ��һ���������
        System.out.println(matcher2.group(2));//���صڶ���ƥ�䵽���ַ���"Python"
        System.out.println(matcher2.start(2));//����7���ڶ�����ʼ����
        System.out.println(matcher2.end(2));//����13 �ڶ����������
        Pattern p=Pattern.compile("([a-z]+)(\\d+)"); 
        Matcher m=p.matcher("aaa2223bb"); 
        m.find();   //ƥ��aaa2223 
        m.groupCount();   //����2,��Ϊ��2�� 
        m.start(1);   //����0 ���ص�һ��ƥ�䵽�����ַ������ַ����е�������
        m.start(2);   //����3             ����ͷ������β
        System.out.println(m.end(2));   //����3 ���ص�һ��ƥ�䵽�����ַ��������һ���ַ����ַ����е�����λ��. m.end(2);   //����7 
        m.group(1);   //����aaa,���ص�һ��ƥ�䵽�����ַ��� 
        m.group(2);   //����2223,���صڶ���ƥ�䵽�����ַ���
        
        
        //Matcher�໹�ṩregion(int start, int end)(������end)���������趨���ҷ�Χ�����ṩregionStrat()��regionEnd()���ڷ�����ʼ�ͽ������ҵ�����
        Pattern pattern3 = Pattern.compile("Java");
        String test33 = "123JavaJava";
        Matcher matcher3 = pattern3.matcher(test33);
        matcher3.region(7, 11);
        System.out.println(matcher3.regionStart());//����7
        System.out.println(matcher3.regionEnd());//����11
        matcher3.find();
        System.out.println(matcher3.group());//����Java
        
        //Matcher���ṩ�������������õ�ǰƥ�����ķ���:reset()��reset(CharSequence input) �滻ƥ����ַ���
        Pattern pattern4 = Pattern.compile("Java");
        String test4 = "Java";
        Matcher matcher4 = pattern4.matcher(test4);
        matcher4.find();
        System.out.println(matcher4.group());//����Java
        matcher4.reset();//����ʼλ������ƥ��
        matcher4.find();
        System.out.println(matcher4.group());//����Java
        matcher4.reset("Python");
        System.out.println(matcher4.find());//����false
        
        //Matcher���ƥ�䷽����replaceAll(String replacement) �� replaceFirst(String replacement)��
        //����replaceAll���滻ȫ��ƥ�䵽���ַ�������replaceFirst�������滻��һ��ƥ�䵽���ַ�����

        Pattern pattern5 = Pattern.compile("Java");
        String test5 = "JavaJava";
        Matcher matcher5 = pattern5.matcher(test5);
        System.out.println(matcher5.replaceAll("Python"));//����PythonPython
        System.out.println(matcher5.replaceFirst("python"));//����PythonJava
        
        //��������appendReplacement(StringBuffer sb, String replacement) �� 
        //appendTail(StringBuffer sb)Ҳ����Ҫ��appendReplacement����ֱ�ӽ�ƥ����ַ�����������һ��
        //StringBuffer�в����ǽ���ʽƥ�䣬������ֻƥ�����λ�ƥ��ȫ��,
        //��appendTail���ǽ�δƥ�䵽�����µ��ַ�����ӵ�StringBuffer�С�
        Pattern pattern6 = Pattern.compile("Java");
        Matcher matcher6 = pattern6.matcher("JavaJavaJava1234");
        StringBuffer sb = new StringBuffer();
        System.out.println();//����true
        while(matcher6.find()){
            System.out.println(matcher6.group());//��ȡƥ��
            matcher6.appendReplacement(sb, "Python");//��ƥ�䵽���ַ���,�����滻
            System.out.println(sb);//���Python
        }
        matcher6.appendTail(sb);
        System.out.println(sb);//���Python1234
    }
}
