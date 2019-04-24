/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.utils.file;

import java.io.File;
import java.io.FilenameFilter;

/** 
 * @description 删除svn的文件,svn文件太多删除时会很卡  创建线程每100个文件删除一下
 * @author 陈吕奖
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
     * @description 获取文件列表
     * @author 陈吕奖 2018-06-01
     * @param file
     */ 
    public static void getFile(File file){
        //先删除目录下的.svn目录
        File[] files = file.listFiles(filter);
        for(File file2 : files) {
            deleteFile(file2);
        }
        //遍历所有的目录
        File[] files2 = file.listFiles();
        for(File file3 : files2) {
            if(file3.isDirectory()){
                getFile(file3);
            }
        }
    }
    
    /** 
     * @description 删除文件
     * @author 陈吕奖 2018-06-01
     * @param file
     */ 
    public static void deleteFile(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File file2 : files) {
                if(file.isDirectory()){
                    deleteFile(file2);
                }else{
                    System.out.println(file2.delete());//删除文件
                }
            }
            System.out.println(file.delete());//删除目录
        }else{
            System.out.println(file.delete());//删除文件
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
