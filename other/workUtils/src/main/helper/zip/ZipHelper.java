/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */
package main.helper.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import main.helper.file.FileNameAndPathHelper;
import org.apache.log4j.Logger;

/**
 * @description 压缩和解压缩的工具类
 * @author 陈吕奖
 * @date 2018-11-12
 */
public class ZipHelper {

    public final static Logger log = Logger.getLogger(ZipHelper.class);
    /**
     * @description 将文件解压要某个指定的目录
     * @author 陈吕奖 2018-11-12
     * @param zipFile 压缩文件
     * @param descFile 解压到的位置
     * @throws Exception
     */
    public void unZipFils(String zipFile, String descDir) throws Exception {
        unZipFils(new File(zipFile), new File(descDir));
    }

    /**
     * @description 将文件解压要某个指定的目录
     * @author 陈吕奖 2018-11-12
     * @param zipFile 压缩文件 默认位置为压缩文件的同级目录
     * @throws Exception
     */
    public void unZipFils(String zipFile) throws Exception {
        unZipFils(new File(zipFile));
    }

    /**
     * @description 将文件解压要某个指定的目录
     * @author 陈吕奖 2018-11-12
     * @param zipFile 压缩文件 默认位置为压缩文件的同级目录
     * @throws Exception
     */
    public void unZipFils(File zipFile) throws Exception {
        unZipFils(zipFile, zipFile.getParentFile());
    }
    
    /**
     * @description 将文件解压要某个指定的目录
     * @author 陈吕奖 2018-11-12
     * @param zipFile 压缩文件
     * @param descFile 解压到的位置
     * @throws Exception
     */
    public void unZipFils(File zipFile, File descDir) throws Exception {
        FileNameAndPathHelper zipFileInfo = new FileNameAndPathHelper(zipFile);
        //是文件并且是zip结尾的
        if(zipFile.isDirectory() || !zipFileInfo.getFileName().toLowerCase().endsWith(".zip")) {
            log.debug("并不是压缩文件");
        }
        //如果目标目录不存在就创建
        if(!descDir.exists()) {
            descDir.mkdirs();
        }
        ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));
        for(Enumeration entries = zip.entries(); entries.hasMoreElements();) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String zipEntryName = entry.getName();
            //拼接出完成的字符串路径  压缩的路径使用的是/
            String outPath = (descDir + File.separator + zipEntryName).replaceAll("\\*", "/");//d:\你好\   njcrm1/abc_pzjtby/bacthdc/expframe.jsp
            log.info(descDir);
            log.info(zipEntryName);
            System.out.println(descDir + zipEntryName);
            File _descDir = new File(outPath);
            if(_descDir.isDirectory() && !_descDir.exists()) {
                _descDir.mkdirs();
                continue;
            } else {
                if(!_descDir.getParentFile().exists())
                    _descDir.getParentFile().mkdirs();
                decompression(zip, entry, _descDir);
            }
        }
        System.out.println("******************解压完毕********************");
    }

    /**
     * @description 将压缩文件解压成文件 解压缩
     * @author 陈吕奖 2018-11-12
     * @param zip
     * @param entry
     * @param descFile
     * @throws Exception
     */
    public void decompression(ZipFile zip, ZipEntry entry, File descFile) throws Exception {
        InputStream in = zip.getInputStream(entry);
        //输出文件路径信息
        OutputStream out = new FileOutputStream(descFile);
        byte[] buf1 = new byte[1024];
        int len;
        while((len = in.read(buf1)) > 0) {
            out.write(buf1, 0, len);
        }
        in.close();
        out.close();
    }

    /** 
     * @description 压缩文件(有默认的压缩文件)
     * @author 陈吕奖 2018-11-12
     * @param srcDir 源文件
     * @throws Exception
     */ 
    public void zipFiles(String srcDir) throws Exception{
        zipFiles(new File(srcDir));
    }
    
    /** 
     * @description 压缩文件(有默认的压缩文件)
     * @author 陈吕奖 2018-11-12
     * @param srcDir 源文件
     * @throws Exception
     */ 
    public void zipFiles(File srcDir) throws Exception{
        zipFiles(srcDir, new File(srcDir.getAbsolutePath()+".zip"), true);
    }
    
    /** 
     * @description 压缩文件
     * @author 陈吕奖 2018-11-12
     * @param srcDir 源文件
     * @param descZipFile  目标文件
     * @throws Exception
     */ 
    public void zipFiles(String srcDir, String descZipFile) throws Exception {
        zipFiles(new File(srcDir), new File(descZipFile), true);
    }
    
    /** 
     * @description 压缩文件
     * @author 陈吕奖 2018-11-12
     * @param srcDir  源文件
     * @param descZipFile  目标文件
     * @param KeepDirStructure  是否保持目录结构
     * @throws Exception
     */ 
    public void zipFiles(File srcDir, File descZipFile, boolean KeepDirStructure) throws Exception {
        zipFiles(srcDir, new FileOutputStream(descZipFile), true);
    }
    
    /**
     * @description 压缩文件
     * @author 陈吕奖 2018-11-12
     * @param srcDir 压缩文件夹路径
     * @param out 压缩文件输出流
     * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     *        false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public void zipFiles(File sourceFile, OutputStream out, boolean KeepDirStructure) throws RuntimeException {
        //记录开始时间
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            //提供输出流
            zos = new ZipOutputStream(out);
            //源文件
            if(!sourceFile.exists()) {
                log.info("待压缩文件不存在");
                return;
            }
            compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure);
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) + " ms");
        }
        catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        }
        finally {
            if(zos != null) {
                try {
                    zos.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** 
     * @description 递归压缩方法
     * @author 陈吕奖 2018-11-12
     * @param sourceFile 源文件
     * @param zos zip输出流
     * @param name 压缩后的名称
     * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     *        false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     */ 
    private void compress(File sourceFile, ZipOutputStream zos, String name, boolean KeepDirStructure) throws Exception {
        byte[] buf = new byte[1024 * 2];
        //是一个文件
        if(sourceFile.isFile()) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            //是文件夹
            File[] listFiles = sourceFile.listFiles();
            if(listFiles == null || listFiles.length == 0) {
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if(KeepDirStructure) {
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            } else {
                for(File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if(KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        System.out.println("输出的name为: " + name);//这里的那么是累加的
                        compress(file, zos, name + File.separator + file.getName(), KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(), KeepDirStructure);
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ZipHelper zipHelper = new ZipHelper();
        //zipHelper.UnZipFils("d:\\njcrm1.zip");
        //zipHelper.UnZipFils("d:\\njcrm1.zip", "d:\\testtt");
        zipHelper.zipFiles("D:\\njcrm1", "D:\\njcrm1.zip");
    }

}
