/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
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
 * @description ѹ���ͽ�ѹ���Ĺ�����
 * @author ������
 * @date 2018-11-12
 */
public class ZipHelper {

    public final static Logger log = Logger.getLogger(ZipHelper.class);
    /**
     * @description ���ļ���ѹҪĳ��ָ����Ŀ¼
     * @author ������ 2018-11-12
     * @param zipFile ѹ���ļ�
     * @param descFile ��ѹ����λ��
     * @throws Exception
     */
    public void unZipFils(String zipFile, String descDir) throws Exception {
        unZipFils(new File(zipFile), new File(descDir));
    }

    /**
     * @description ���ļ���ѹҪĳ��ָ����Ŀ¼
     * @author ������ 2018-11-12
     * @param zipFile ѹ���ļ� Ĭ��λ��Ϊѹ���ļ���ͬ��Ŀ¼
     * @throws Exception
     */
    public void unZipFils(String zipFile) throws Exception {
        unZipFils(new File(zipFile));
    }

    /**
     * @description ���ļ���ѹҪĳ��ָ����Ŀ¼
     * @author ������ 2018-11-12
     * @param zipFile ѹ���ļ� Ĭ��λ��Ϊѹ���ļ���ͬ��Ŀ¼
     * @throws Exception
     */
    public void unZipFils(File zipFile) throws Exception {
        unZipFils(zipFile, zipFile.getParentFile());
    }
    
    /**
     * @description ���ļ���ѹҪĳ��ָ����Ŀ¼
     * @author ������ 2018-11-12
     * @param zipFile ѹ���ļ�
     * @param descFile ��ѹ����λ��
     * @throws Exception
     */
    public void unZipFils(File zipFile, File descDir) throws Exception {
        FileNameAndPathHelper zipFileInfo = new FileNameAndPathHelper(zipFile);
        //���ļ�������zip��β��
        if(zipFile.isDirectory() || !zipFileInfo.getFileName().toLowerCase().endsWith(".zip")) {
            log.debug("������ѹ���ļ�");
        }
        //���Ŀ��Ŀ¼�����ھʹ���
        if(!descDir.exists()) {
            descDir.mkdirs();
        }
        ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));
        for(Enumeration entries = zip.entries(); entries.hasMoreElements();) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String zipEntryName = entry.getName();
            //ƴ�ӳ���ɵ��ַ���·��  ѹ����·��ʹ�õ���/
            String outPath = (descDir + File.separator + zipEntryName).replaceAll("\\*", "/");//d:\���\   njcrm1/abc_pzjtby/bacthdc/expframe.jsp
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
        System.out.println("******************��ѹ���********************");
    }

    /**
     * @description ��ѹ���ļ���ѹ���ļ� ��ѹ��
     * @author ������ 2018-11-12
     * @param zip
     * @param entry
     * @param descFile
     * @throws Exception
     */
    public void decompression(ZipFile zip, ZipEntry entry, File descFile) throws Exception {
        InputStream in = zip.getInputStream(entry);
        //����ļ�·����Ϣ
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
     * @description ѹ���ļ�(��Ĭ�ϵ�ѹ���ļ�)
     * @author ������ 2018-11-12
     * @param srcDir Դ�ļ�
     * @throws Exception
     */ 
    public void zipFiles(String srcDir) throws Exception{
        zipFiles(new File(srcDir));
    }
    
    /** 
     * @description ѹ���ļ�(��Ĭ�ϵ�ѹ���ļ�)
     * @author ������ 2018-11-12
     * @param srcDir Դ�ļ�
     * @throws Exception
     */ 
    public void zipFiles(File srcDir) throws Exception{
        zipFiles(srcDir, new File(srcDir.getAbsolutePath()+".zip"), true);
    }
    
    /** 
     * @description ѹ���ļ�
     * @author ������ 2018-11-12
     * @param srcDir Դ�ļ�
     * @param descZipFile  Ŀ���ļ�
     * @throws Exception
     */ 
    public void zipFiles(String srcDir, String descZipFile) throws Exception {
        zipFiles(new File(srcDir), new File(descZipFile), true);
    }
    
    /** 
     * @description ѹ���ļ�
     * @author ������ 2018-11-12
     * @param srcDir  Դ�ļ�
     * @param descZipFile  Ŀ���ļ�
     * @param KeepDirStructure  �Ƿ񱣳�Ŀ¼�ṹ
     * @throws Exception
     */ 
    public void zipFiles(File srcDir, File descZipFile, boolean KeepDirStructure) throws Exception {
        zipFiles(srcDir, new FileOutputStream(descZipFile), true);
    }
    
    /**
     * @description ѹ���ļ�
     * @author ������ 2018-11-12
     * @param srcDir ѹ���ļ���·��
     * @param out ѹ���ļ������
     * @param KeepDirStructure �Ƿ���ԭ����Ŀ¼�ṹ,true:����Ŀ¼�ṹ;
     *        false:�����ļ��ܵ�ѹ������Ŀ¼��(ע�⣺������Ŀ¼�ṹ���ܻ����ͬ���ļ�,��ѹ��ʧ��)
     * @throws RuntimeException ѹ��ʧ�ܻ��׳�����ʱ�쳣
     */
    public void zipFiles(File sourceFile, OutputStream out, boolean KeepDirStructure) throws RuntimeException {
        //��¼��ʼʱ��
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            //�ṩ�����
            zos = new ZipOutputStream(out);
            //Դ�ļ�
            if(!sourceFile.exists()) {
                log.info("��ѹ���ļ�������");
                return;
            }
            compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure);
            long end = System.currentTimeMillis();
            System.out.println("ѹ����ɣ���ʱ��" + (end - start) + " ms");
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
     * @description �ݹ�ѹ������
     * @author ������ 2018-11-12
     * @param sourceFile Դ�ļ�
     * @param zos zip�����
     * @param name ѹ���������
     * @param KeepDirStructure �Ƿ���ԭ����Ŀ¼�ṹ,true:����Ŀ¼�ṹ;
     *        false:�����ļ��ܵ�ѹ������Ŀ¼��(ע�⣺������Ŀ¼�ṹ���ܻ����ͬ���ļ�,��ѹ��ʧ��)
     * @throws Exception
     */ 
    private void compress(File sourceFile, ZipOutputStream zos, String name, boolean KeepDirStructure) throws Exception {
        byte[] buf = new byte[1024 * 2];
        //��һ���ļ�
        if(sourceFile.isFile()) {
            // ��zip����������һ��zipʵ�壬��������nameΪzipʵ����ļ�������
            zos.putNextEntry(new ZipEntry(name));
            // copy�ļ���zip�������
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            //���ļ���
            File[] listFiles = sourceFile.listFiles();
            if(listFiles == null || listFiles.length == 0) {
                // ��Ҫ����ԭ�����ļ��ṹʱ,��Ҫ�Կ��ļ��н��д���
                if(KeepDirStructure) {
                    // ���ļ��еĴ���
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // û���ļ�������Ҫ�ļ���copy
                    zos.closeEntry();
                }
            } else {
                for(File file : listFiles) {
                    // �ж��Ƿ���Ҫ����ԭ�����ļ��ṹ
                    if(KeepDirStructure) {
                        // ע�⣺file.getName()ǰ����Ҫ���ϸ��ļ��е����ּ�һб��,
                        // ��Ȼ���ѹ�����оͲ��ܱ���ԭ�����ļ��ṹ,���������ļ����ܵ�ѹ������Ŀ¼����
                        System.out.println("�����nameΪ: " + name);//�������ô���ۼӵ�
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
