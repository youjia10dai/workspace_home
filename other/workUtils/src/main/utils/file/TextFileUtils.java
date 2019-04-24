/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** 
 * @description ���ַ���������ļ���
 * @author ������
 * @date 2018-06-07 
 */
public class TextFileUtils {

    /** 
     * @description ��list�����е�����������ļ���
     * @author ������ 2018-06-07
     * @param list
     */ 
    public static void outPutFile(String filePathAndName, List list)
    {
        FileWriter resultFile = null;
        PrintWriter myFile = null;
        try {
            File file = createFile(filePathAndName);
            resultFile = new FileWriter(file,true);
            myFile = new PrintWriter(resultFile);
            for(Object object : list) {
                if(object instanceof Map){
                    myFile.println(((Map)object).get("sql"));
                }else{
                    myFile.println(object.toString());
                }
            }
            myFile.close();
            resultFile.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(resultFile != null){
                try {
                    resultFile.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(myFile != null){
                myFile.close();
            }
        }
    }
    
    /** 
     * @description ����һ���ļ�
     * @author ������ 2018-06-07
     * @return
     */ 
    public static File createFile(String filePathAndName){
        File myFilePath = null;
        try {
            
            myFilePath = new File(filePathAndName);
            if (!myFilePath.exists()) {
                myFilePath.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myFilePath;
    }
    
    /** 
     * @description ��ȡһ���ı��ļ����ı�����,������һ��List����
     * @author ������ 2018-06-14
     * @param file
     * @return
     */ 
    public static String[] getFileContext(File file,String encoding){
        List<String> list = new ArrayList<String>();
        BufferedReader br = null;
        String lineStr = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file),encoding));
            while((lineStr=br.readLine())!=null){
                list.add(lineStr);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(br != null){
                try {
                    br.close();
                    br = null;
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String[] fileContextStrings = list.toArray(new String[]{});
        return fileContextStrings;
    }
    
}
