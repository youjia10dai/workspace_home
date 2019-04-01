/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.helper.file;

import java.io.File;

/** 
 * @description ����һ��File
 * �ļ����ͺ�׺��
 * _��ʾ��һ���ֲ��ı���
 * @author ������
 * @date 2018-10-30 
 */
public class FileNameAndPathHelper {

    private File file;
    
    /** 
     * @fields FileName �ļ���
     */ 
    private String fileName;
    
    /** 
     * @fields suffixName ��׺��
     */ 
    private String suffixName;
    
    /** 
     * @fields path ·��
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
     * @description ��ȡ�ļ���(���к�׺��)
     * @author ������ 2018-10-31
     * @return
     */ 
    public String getFileName(){
        return fileName + (null==suffixName?"":suffixName);
    }
    
    /** 
     * @description ��ȡ�ļ���(�������к�׺��)
     * @author ������ 2018-10-31
     * @return
     */ 
    public String getFileNameWithoutSuffixName(){
        return fileName;
    }
    
    /** 
     * @description ������
     * @author ������ 2018-10-31
     * @param newName
     */ 
    public void rename(String newName){
        fileName = newName;
    }
    
    /** 
     * @description ��ȡ�ϲ��·��
     * @author ������ 2018-10-31
     * @return
     */ 
    public String getPath(){
        return path;
    }
    
    /** 
     * @description ��ȡ��ɵ��ļ���(�ļ�·�� + �ļ��� + ��׺��)
     * @author ������ 2018-10-31
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
