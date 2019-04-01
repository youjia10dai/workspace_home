/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */
package main.source.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @description 这里用一句话描述这个类的作用
 * @author 陈吕奖
 * @date 2018-10-29
 */
public class 目录文件复制 {
    public static void copy(String source, String dest) throws IOException {
        File sourceFile = new File(source);
        System.out.println(sourceFile);
        //文件名称列表
        File[] files = sourceFile.listFiles();

        if(!(new File(dest)).exists()) {
            (new File(dest)).mkdir();
        }
        if(files != null && files.length > 0){
            for(int i = 0; i < files.length; i++) {
                if(files[i].isDirectory()) {
                    System.out.println(files[i].getAbsolutePath()+ "   " + dest + File.separator + files[i].getName());
                    copy(files[i].toString(), dest + File.separator + files[i].getName());
                }
                if(files[i].isFile()) {
                    copyFile(files[i], dest + File.separator + files[i].getName());
                }
            }
        }
    }

    public static void copyFile(File source, String dest) throws IOException {
        System.out.println("文件复制");
        FileInputStream in = new FileInputStream(source);
        FileOutputStream out = new FileOutputStream(dest);
        byte[] buffer = new byte[2097152];
        while((in.read(buffer)) != -1) {
            out.write(buffer);
        }
        in.close();
        out.close();
    }
    
    public static void main(String[] args) throws Exception {
        copy("E:\\test1","E:\\test2");
    }
}
