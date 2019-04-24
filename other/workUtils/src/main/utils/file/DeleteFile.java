/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.utils.file;

import java.io.File;
import java.io.FilenameFilter;

/** 
 * @description ɾ��svn���ļ�,svn�ļ�̫��ɾ��ʱ��ܿ�  �����߳�ÿ100���ļ�ɾ��һ��
 * @author ������
 * @date 2018-06-01 
 */
public class DeleteFile {

    static SvnFilter filter =new SvnFilter();
    public static void main(String[] args) 
    {
        File file = new File("C:\\Users\\Administrator\\Desktop\\njcrm");
        getFile(file);
    }
    
    /** 
     * @description ��ȡ�ļ��б�
     * @author ������ 2018-06-01
     * @param file
     */ 
    public static void getFile(File file){
        //��ɾ��Ŀ¼�µ�.svnĿ¼
        File[] files = file.listFiles(filter);
        for(File file2 : files) {
            deleteFile(file2);
        }
        //�������е�Ŀ¼
        File[] files2 = file.listFiles();
        for(File file3 : files2) {
            if(file3.isDirectory()){
                getFile(file3);
            }
        }
    }
    
    /** 
     * @description ɾ���ļ�
     * @author ������ 2018-06-01
     * @param file
     */ 
    public static void deleteFile(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File file2 : files) {
                if(file.isDirectory()){
                    deleteFile(file2);
                }else{
                    System.out.println(file2.delete());//ɾ���ļ�
                }
            }
            System.out.println(file.delete());//ɾ��Ŀ¼
        }else{
            System.out.println(file.delete());//ɾ���ļ�
        }
        
    }
}

class SvnFilter implements FilenameFilter{


    public boolean accept(File dir, String name) {
//        System.out.println(name);
        if(dir.isDirectory() && name.equals(".svn")){
            return true;
        }
        return false;
    }
    
}
