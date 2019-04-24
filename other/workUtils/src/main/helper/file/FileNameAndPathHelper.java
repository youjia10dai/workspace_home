/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.helper.file;

import java.io.File;

/** 
 * @description 描述一个File
 * 文件名和后缀名
 * _表示是一个局部的变量
 * @author 陈吕奖
 * @date 2018-10-30 
 */
public class FileNameAndPathHelper {

    private File file;
    
    /** 
     * @fields FileName 文件名
     */ 
    private String fileName;
    
    /** 
     * @fields suffixName 后缀名
     */ 
    private String suffixName;
    
    /** 
     * @fields path 路径
     */ 
    private String path;
    
    public FileNameAndPathHelper(File file){
        this.file = file;
        path = file.getParent();
        String _fileName = file.getName();
        if(_fileName.contains(".")){
            suffixName = _fileName.substring(_fileName.lastIndexOf("."));
            fileName = _fileName.substring(0, _fileName.lastIndexOf("."));
        }else{
            fileName = _fileName;
        }
//        System.out.println(path);
//        System.out.println(suffixName);
//        System.out.println(fileName);
    }
    
    public FileNameAndPathHelper(String fileNameAndPath){
        this(new File(fileNameAndPath));
    }
    
    /** 
     * @description 获取文件名(含有后缀名)
     * @author 陈吕奖 2018-10-31
     * @return
     */ 
    public String getFileName(){
        return fileName + (null==suffixName?"":suffixName);
    }
    
    /** 
     * @description 获取文件名(不包含有后缀名)
     * @author 陈吕奖 2018-10-31
     * @return
     */ 
    public String getFileNameWithoutSuffixName(){
        return fileName;
    }
    
    /** 
     * @description 重命名
     * @author 陈吕奖 2018-10-31
     * @param newName
     */ 
    public void rename(String newName){
        fileName = newName;
    }
    
    /** 
     * @description 获取上层的路径
     * @author 陈吕奖 2018-10-31
     * @return
     */ 
    public String getPath(){
        return path;
    }
    
    /** 
     * @description 获取完成的文件名(文件路径 + 文件名 + 后缀名)
     * @author 陈吕奖 2018-10-31
     * @return
     */ 
    public String getCompleteFileName(){
        return path + File.separator + getFileName();
    }
    
    public boolean isFile(){
        return file.isFile();
    }
    
    public static void main(String[] args) {
        FileNameAndPathHelper info = new FileNameAndPathHelper("D:\\test\\njcrm\\report\\pzjtmdrl\\reportFrame.jsp");
        info.rename(info.getFileNameWithoutSuffixName()+"20181111");
        System.out.println(info.getCompleteFileName());
    }
}
