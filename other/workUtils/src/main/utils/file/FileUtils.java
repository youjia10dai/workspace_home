/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */
package main.utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;


/**
 * @description 操作文件的工具类
 * 应该实现两套API,使用File或String都可以
 * @author 陈吕奖
 * @date 2018-10-29
 */
public class FileUtils {

    /**
     * @description 读取一个文件的内容,以字符串的形式放回
     * @author 陈吕奖 2018-10-29
     * @param filePathAndName 完整的路径加名字
     * @param encoding 文本的编码格式
     * @return 返回文件的文本内容
     * @throws IOException
     */
    public static String readTxt(String filePathAndName, String encoding) throws IOException {
        return readTxt(new File(filePathAndName), encoding);
    }

    public final static Logger log = Logger.getLogger(FileUtils.class);
    
    /**
     * @description 读取一个文件的内容,以字符串的形式放回
     * @author 陈吕奖 2018-10-29
     * @param readFile 完整的路径加名字
     * @param encoding 文本的编码格式
     * @return 返回文件的文本内容
     * @throws IOException
     */
    public static String readTxt(File readFile, String encoding) throws IOException {
        encoding = encoding.trim();
        StringBuffer str = new StringBuffer("");
        String st = "";
        try {
            FileInputStream fs = new FileInputStream(readFile);
            InputStreamReader isr;
            if(encoding.equals("")) {
                isr = new InputStreamReader(fs);
            } else {
                isr = new InputStreamReader(fs, encoding);
            }
            BufferedReader br = new BufferedReader(isr);
            try {
                String data = "";
                while((data = br.readLine()) != null) {
                    str.append(data + "\r\n");
                }
            }
            catch (Exception e) {
                str.append(e.toString());
            }
            st = str.toString();
        }
        catch (IOException es) {
            st = "";
        }
        return st;
    }
    
    /**
     * @description 创建一个目录
     * @author 陈吕奖 2018-10-29
     * @param folderPath 完整的目录
     * @return 新创建的目录的完整路径
     */
    public static String createDir(String folderPath) {
        String txt = folderPath;
        try {
            java.io.File myFilePath = new java.io.File(txt);
            txt = folderPath;
            if(!myFilePath.exists()) {
                myFilePath.mkdirs();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return txt;
    }

    /**
     * @description 创建多层级的目录 主要是学习StringTokenizer对象的使用
     *              疑问:为什么不使用File.mkdirs方法来创建多层级目录
     * @author 陈吕奖 2018-10-29
     * @param folderPath
     * @param paths 使用|来分割目录层级
     * @return
     */
    public static String createDirs(String folderPath, String paths) {
        String txts = folderPath;
        try {
            String txt;
            txts = folderPath;
            StringTokenizer st = new StringTokenizer(paths, "|");
            for(int i = 0; st.hasMoreTokens(); i++) {
                txt = st.nextToken().trim();
                if(txts.endsWith(File.separator)) {
                    txts = createDir(txts + txt);
                } else {
                    txts = createDir(txts + File.separator + txt);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return txts;
    }

    /**
     * @description 删除一个文件
     * @author 陈吕奖 2018-10-29
     * @param filePathAndName 完整路径加文件名
     * @return 删除成功返回true, 如果文件不存在或删除失败返回false
     */
    public static boolean deleteFile(String filePathAndName) {
        return deleteFile(new File(filePathAndName));
    }

    /**
     * @description 删除一个文件
     * @author 陈吕奖 2018-10-29
     * @param deletedFile 完整路径加文件名
     * @return 删除成功返回true, 如果文件不存在或删除失败返回false
     */
    public static boolean deleteFile(File deletedFile) {
        boolean isSuccess = false;
        try {
            if(deletedFile.exists()) {
                deletedFile.delete();
                isSuccess = true;
            } else {
                isSuccess = false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
    
    /**
     * @description 删除一个目录(包含这个目录下的所有文件) 目前这个方法的功能满足现在的需求 使用递归删除的,所有目录的层级不能太深
     *              并别JavaScript已经禁止递归的使用
     * @author 陈吕奖 2018-06-01
     * @param file
     */
    public static void deleteDirFile(File file) {
        if(file == null || !file.exists())
            return;
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            for(File file2 : files) {
                if(file.isDirectory()) {
                    deleteDirFile(file2);
                } else {
                    System.out.println(file2.delete());//删除文件
                }
            }
            System.out.println(file.delete());//删除目录
        } else {
            System.out.println(file.delete());//删除文件
        }
    }

    /**
     * @description 删除一个目录(包含这个目录下的所有文件) 目前这个方法的功能满足现在的需求 使用递归删除的,所有目录的层级不能太深
     *              并别JavaScript已经禁止递归的使用
     * @author 陈吕奖 2018-06-01
     * @param excelFile
     */
    public static void deleteDirFile(String filePathAndName) {
        File file = new File(filePathAndName);
        deleteDirFile(file);
    }

    /**
     * @description 整个文件夹的复制(如果正常项目中有这种需求,尽量不要自己封装,可以使用下面两种方法)
     * 1.使用Commons IO复制      -- 这个Jar包正常项目都会导入
     * 2.使用Java7的Files类复制    --Java7 可以直接使用
     * 这两种方式都是使用效率高的方式
     * @author 陈吕奖 2018-10-29
     * @param source
     * @param dest
     * @throws IOException
     */
    public static void copy(String source, String dest) throws IOException {
        copy(new File(source), new File(dest));
    }
    
    /**
     * @description 整个文件夹的复制(如果正常项目中有这种需求,尽量不要自己封装,可以使用下面两种方法)
     * 1.使用Commons IO复制      -- 这个Jar包正常项目都会导入
     * 2.使用Java7的Files类复制    --Java7 可以直接使用
     * 这两种方式都是使用效率高的方式
     * @author 陈吕奖 2018-10-29
     * @param source
     * @param dest
     * @throws IOException
     */ 
    public static void copy(File source, File dest) throws IOException {
        //如果即将复制的文件的父目录不存,就创建
        if(!dest.getParentFile().exists()){
            System.out.println("FileUtils.copy()");
            dest.getParentFile().mkdirs();
        }
        //文件名称列表
        if(source.isFile()){
            copyFileUsingFileChannels(source, dest);
            return;
        }
        File[] files = source.listFiles();
        if(files != null && files.length > 0){
            for(int i = 0; i < files.length; i++) {
                if(files[i].isDirectory()) {
                    copy(files[i].toString(), dest.getAbsolutePath() + File.separator + files[i].getName());
                }
                if(files[i].isFile()) {
                    copyFileUsingFileChannels(files[i], new File(dest.getAbsolutePath() + File.separator + files[i].getName()));
                }
            }
        }
    }
    
    /** 
     * @description 复制文件,使用到了新的API(效率更加高)
     * @author 陈吕奖 2018-10-29
     * @param source
     * @param dest
     * @throws IOException
     */ 
    public static void copyFileUsingFileChannels(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        }catch (Exception e) {
            System.out.println(e + "1");
        }
        finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

    /**
     * @description 文件的重命名
     * @author 陈吕奖 2018-10-29
     * @param sourceFile
     * @param newName
     * @return
     */ 
    public static boolean rename(File sourceFile, String newName){
        return sourceFile.renameTo(new File(newName));
    }
    
    /**
     * @description 文件的重命名
     * @author 陈吕奖 2018-10-29
     * @param excelFile
     * @param newName
     * @return
     */
    public static boolean rename(String sourceName, String newName){
        return rename(new File(sourceName), newName);
    }
    
    
    
    //测试
    public static void main(String[] args) throws Exception {
        //
        //        String text = readTxt("D:\\我的坚果云\\同步文件\\workspace\\workUtils\\src\\com\\njry\\clj\\util\\workUtils\\automaticdeploy\\info.txt",
//                "GBK");
//        System.out.println(File.separator);
//        deleteDirFile("D:\\我的坚果云\\同步文件\\workspace\\workUtils\\src\\com\\njry\\clj\\util\\workUtils\\automaticdeploy\\info");
//        copy("E:\\test1","E:\\test2");
    	copy("E:\\myeclipse\\anhui", "E:\\myeclipse\\anhui2");
//        System.out.println(new File("E:\\test1\\复件 人力\\民主测评表（张红慧）.xls").renameTo(new File("E:\\test1\\复件 人力\\2.xls")));
    }

}
