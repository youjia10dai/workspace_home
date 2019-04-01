/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */
package main.study.reflection;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * ʹ��1.7��JDK
 * @description �����һ�������Եĸ��� ����д��Ŀʱ,����ͨ���Ƚ����Եĸ������ж��Ƿ���©��ʲô����
 * @author ������
 * @date 2018-04-14
 */
public class ReflectUtils {

    public static void main(String[] args) throws Exception {
        demo1();
//    	demo2();
    }
    
    /**
     * @description ��һ�ֵ��÷�ʽ
     * @author ������ 2018-04-14
     * @throws Exception
     */ 
    public static void demo1() throws Exception{
        Class<?> target = Class.forName("web.model.User", true, new MyClassLoader("User",1));
        Class<?> source = Class.forName("web.model.User", true, new MyClassLoader("User",0));
     //   Class<?> class1 = new MyClassLoader().findClass("web.model.User");  //ֱ�ӵ���
        compareClass(source,target);
    }
    
    /** 
     * @description 
     * @author ������ 2018-04-14
     * @param source ԭ��
     * @param target �İ�
     */ 
    public static void compareClass(Class<?> source , Class<?> target){
        //��ȡclass�����еķ���,������
        //�����Ժͷ��������洢��һ������
        //���м��ϵıȽ�,����ҵ���Ӧ�ľ�ɾ��,���û���ҵ��ͱ���
        //��󽫼��ϵ�Ԫ��ȫ�����
        String[] sourceValue = getTypeValues(source);
        String[] targetValue = getTypeValues(target);
        
        
        for(int i = 0, length = targetValue.length; i < length; i++) {
            String str1 = targetValue[i];
            
            for(int j = 0, l= sourceValue.length; j < l; j++) {
                String str2 = sourceValue[j];
                if(str1.equals(str2)){
                    targetValue[i] ="";
                    sourceValue[j]="";
                    break ;
                }
            }
            
        }
        
        System.out.println(Arrays.asList(targetValue));
        System.out.println(Arrays.asList(sourceValue));
        
    }
    
    /** 
     * @description ��ȡһ��Class��������з���������
     * @author ������ 2018-04-14
     * @param clazz
     * @return  ����һ��String[]
     */ 
    public static String[] getTypeValues(Class<?> clazz){
        Method[] methods = clazz.getDeclaredMethods();
        Field[] fields = clazz.getDeclaredFields();
        String[] typeValues =new String[methods.length+fields.length];
        for(int i = 0, length = methods.length; i < length; i++) {
            System.out.println(methods[i].toString());
            typeValues[i] = methods[i].toString();
        }
        for(int i = 0, length = fields.length; i < length; i++) {
            typeValues[i+methods.length] = fields[i].getName();
        }
        return typeValues;
    }
    
    public static void demo2(){
        //û�гɹ�
      ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

      // This URL for a directory will be searched *recursively*  
      URL classes;
      try {
          classes = new URL("file:///D:/MyScript/");

          ClassLoader custom = new URLClassLoader(new URL[] {classes},systemClassLoader);

          // this class should be loaded from your directory  
          Class<?> clazz = custom.loadClass("web.model.User");
//          Class<?> clazz = Class.forName("web.model.User", true, custom);
          System.out.println(clazz);
          Method[] methods = clazz.getDeclaredMethods();
          for(Method method : methods) {
              System.out.println(method.getName());
          }
      }
      catch (Exception e) {
         
          e.printStackTrace();
      }
    }
}

/**
 * @description �Զ���һ���������
 * @author ������
 * @date 2018-04-14
 */
class MyClassLoader extends ClassLoader {
    
    public String paths = "";
    
    //class�ļ�������
    private String className;
    
    // 0 �� source    1   target
    private int type;
    

    
    /**
     * @description 
     * @param className
     * @param type  0 :ԭ��   1 �İ�
     */
    public MyClassLoader(String className, int type){
        this.className = className;
        this.type = type;
        System.out.println (MyClassLoader.class.getResource("").getFile());
        URL url = MyClassLoader.class.getResource ("");
        if(type == 0){
            paths = url.getFile().substring(1)+"source/";
        }else {
            paths = url.getFile().substring(1)+"target/";
        }
    }
    
    @Override
    protected Class<?> findClass(String name) {
        System.out.println("1");
        byte[] classBytes = null;//����ֽ���Ķ�����
        Path path = null;//��Դ��λ��
        try {
            //Ӧ �ã�Ҫʹ��FileЭ�飬�����ĸ�ʽ���£�file:///�ļ�·��������Ҫ��F��flash�ļ����е�1.swf�ļ���
            //��ô��������Դ��������IE��ַ���м��룺file:///f:/flash/1.swf���س���
            path = Paths.get(new URI("file:///"+paths+name.substring(name.lastIndexOf(".")+1)+".class"));
            File file = path.toFile();
            classBytes = Files.readAllBytes(path);  
            Class<?> class1 = defineClass(name, classBytes, 0, classBytes.length);
            return class1;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
