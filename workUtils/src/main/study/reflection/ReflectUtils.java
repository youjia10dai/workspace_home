/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
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
 * 使用1.7的JDK
 * @description 计算出一个类属性的个数 仿照写项目时,可以通过比较属性的个数来判断是否有漏掉什么方法
 * @author 陈吕奖
 * @date 2018-04-14
 */
public class ReflectUtils {

    public static void main(String[] args) throws Exception {
        demo1();
//    	demo2();
    }
    
    /**
     * @description 第一种调用方式
     * @author 陈吕奖 2018-04-14
     * @throws Exception
     */ 
    public static void demo1() throws Exception{
        Class<?> target = Class.forName("web.model.User", true, new MyClassLoader("User",1));
        Class<?> source = Class.forName("web.model.User", true, new MyClassLoader("User",0));
     //   Class<?> class1 = new MyClassLoader().findClass("web.model.User");  //直接调用
        compareClass(source,target);
    }
    
    /** 
     * @description 
     * @author 陈吕奖 2018-04-14
     * @param source 原版
     * @param target 改版
     */ 
    public static void compareClass(Class<?> source , Class<?> target){
        //获取class中所有的方法,和属性
        //将属性和方法名都存储到一个集合
        //进行集合的比较,如果找到对应的就删除,如果没有找到就保留
        //最后将集合的元素全部输出
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
     * @description 获取一个Class对象的所有方法和属性
     * @author 陈吕奖 2018-04-14
     * @param clazz
     * @return  返回一个String[]
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
        //没有成功
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
 * @description 自定义一个类加载器
 * @author 陈吕奖
 * @date 2018-04-14
 */
class MyClassLoader extends ClassLoader {
    
    public String paths = "";
    
    //class文件的名字
    private String className;
    
    // 0 ： source    1   target
    private int type;
    

    
    /**
     * @description 
     * @param className
     * @param type  0 :原版   1 改版
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
        byte[] classBytes = null;//存放字节码的二进制
        Path path = null;//资源定位类
        try {
            //应 用：要使用File协议，基本的格式如下：file:///文件路径，比如要打开F盘flash文件夹中的1.swf文件，
            //那么可以在资源管理器或IE地址栏中键入：file:///f:/flash/1.swf并回车。
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
