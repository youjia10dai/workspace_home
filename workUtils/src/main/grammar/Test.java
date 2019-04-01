/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.grammar;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * @description 这里用一句话描述这个类的作用
 * @author 陈吕奖
 * @date 2018-11-02 
 */
public class Test {
    public static void main(String[] args) {   
        try {   
            Process p = Runtime.getRuntime().exec("cmd /C dir D:\\tomcat6.0.45_x86\\webapps\\njcrm\\report\\ywtjkbb\\zqkdghcc /tc");   
            InputStream is = p.getInputStream();   
            BufferedReader br = new BufferedReader(new InputStreamReader(is));   
            String str;   
            int i = 0;   
            while ((str = br.readLine()) != null) {   
                i++;   
                if (i == 6) {   
                    System.out.println(str.substring(0, 17));
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");       
                    System.out.println(formatter.format(new Date(new File("D:\\tomcat6.0.45_x86\\webapps\\njcrm\\report\\ywtjkbb\\zqkdghcc").lastModified())));
                    
                }   
            }   
        } catch (java.io.IOException exc) {   
            exc.printStackTrace();   
        }   
    }
}
