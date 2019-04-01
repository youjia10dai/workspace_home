/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
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
 * @description �����ļ��Ĺ�����
 * Ӧ��ʵ������API,ʹ��File��String������
 * @author ������
 * @date 2018-10-29
 */
public class FileUtils {

    /**
     * @description ��ȡһ���ļ�������,���ַ�������ʽ�Ż�
     * @author ������ 2018-10-29
     * @param filePathAndName ������·��������
     * @param encoding �ı��ı����ʽ
     * @return �����ļ����ı�����
     * @throws IOException
     */
    public static String readTxt(String filePathAndName, String encoding) throws IOException {
        return readTxt(new File(filePathAndName), encoding);
    }

    public final static Logger log = Logger.getLogger(FileUtils.class);
    
    /**
     * @description ��ȡһ���ļ�������,���ַ�������ʽ�Ż�
     * @author ������ 2018-10-29
     * @param readFile ������·��������
     * @param encoding �ı��ı����ʽ
     * @return �����ļ����ı�����
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
     * @description ����һ��Ŀ¼
     * @author ������ 2018-10-29
     * @param folderPath ������Ŀ¼
     * @return �´�����Ŀ¼������·��
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
     * @description ������㼶��Ŀ¼ ��Ҫ��ѧϰStringTokenizer�����ʹ��
     *              ����:Ϊʲô��ʹ��File.mkdirs������������㼶Ŀ¼
     * @author ������ 2018-10-29
     * @param folderPath
     * @param paths ʹ��|���ָ�Ŀ¼�㼶
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
     * @description ɾ��һ���ļ�
     * @author ������ 2018-10-29
     * @param filePathAndName ����·�����ļ���
     * @return ɾ���ɹ�����true, ����ļ������ڻ�ɾ��ʧ�ܷ���false
     */
    public static boolean deleteFile(String filePathAndName) {
        return deleteFile(new File(filePathAndName));
    }

    /**
     * @description ɾ��һ���ļ�
     * @author ������ 2018-10-29
     * @param deletedFile ����·�����ļ���
     * @return ɾ���ɹ�����true, ����ļ������ڻ�ɾ��ʧ�ܷ���false
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
     * @description ɾ��һ��Ŀ¼(�������Ŀ¼�µ������ļ�) Ŀǰ��������Ĺ����������ڵ����� ʹ�õݹ�ɾ����,����Ŀ¼�Ĳ㼶����̫��
     *              ����JavaScript�Ѿ���ֹ�ݹ��ʹ��
     * @author ������ 2018-06-01
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
                    System.out.println(file2.delete());//ɾ���ļ�
                }
            }
            System.out.println(file.delete());//ɾ��Ŀ¼
        } else {
            System.out.println(file.delete());//ɾ���ļ�
        }
    }

    /**
     * @description ɾ��һ��Ŀ¼(�������Ŀ¼�µ������ļ�) Ŀǰ��������Ĺ����������ڵ����� ʹ�õݹ�ɾ����,����Ŀ¼�Ĳ㼶����̫��
     *              ����JavaScript�Ѿ���ֹ�ݹ��ʹ��
     * @author ������ 2018-06-01
     * @param excelFile
     */
    public static void deleteDirFile(String filePathAndName) {
        File file = new File(filePathAndName);
        deleteDirFile(file);
    }

    /**
     * @description �����ļ��еĸ���(���������Ŀ������������,������Ҫ�Լ���װ,����ʹ���������ַ���)
     * 1.ʹ��Commons IO����      -- ���Jar��������Ŀ���ᵼ��
     * 2.ʹ��Java7��Files�ิ��    --Java7 ����ֱ��ʹ��
     * �����ַ�ʽ����ʹ��Ч�ʸߵķ�ʽ
     * @author ������ 2018-10-29
     * @param source
     * @param dest
     * @throws IOException
     */
    public static void copy(String source, String dest) throws IOException {
        copy(new File(source), new File(dest));
    }
    
    /**
     * @description �����ļ��еĸ���(���������Ŀ������������,������Ҫ�Լ���װ,����ʹ���������ַ���)
     * 1.ʹ��Commons IO����      -- ���Jar��������Ŀ���ᵼ��
     * 2.ʹ��Java7��Files�ิ��    --Java7 ����ֱ��ʹ��
     * �����ַ�ʽ����ʹ��Ч�ʸߵķ�ʽ
     * @author ������ 2018-10-29
     * @param source
     * @param dest
     * @throws IOException
     */ 
    public static void copy(File source, File dest) throws IOException {
        //����������Ƶ��ļ��ĸ�Ŀ¼����,�ʹ���
        if(!dest.getParentFile().exists()){
            System.out.println("FileUtils.copy()");
            dest.getParentFile().mkdirs();
        }
        //�ļ������б�
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
     * @description �����ļ�,ʹ�õ����µ�API(Ч�ʸ��Ӹ�)
     * @author ������ 2018-10-29
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
     * @description �ļ���������
     * @author ������ 2018-10-29
     * @param sourceFile
     * @param newName
     * @return
     */ 
    public static boolean rename(File sourceFile, String newName){
        return sourceFile.renameTo(new File(newName));
    }
    
    /**
     * @description �ļ���������
     * @author ������ 2018-10-29
     * @param excelFile
     * @param newName
     * @return
     */
    public static boolean rename(String sourceName, String newName){
        return rename(new File(sourceName), newName);
    }
    
    
    
    //����
    public static void main(String[] args) throws Exception {
        //
        //        String text = readTxt("D:\\�ҵļ����\\ͬ���ļ�\\workspace\\workUtils\\src\\com\\njry\\clj\\util\\workUtils\\automaticdeploy\\info.txt",
//                "GBK");
//        System.out.println(File.separator);
//        deleteDirFile("D:\\�ҵļ����\\ͬ���ļ�\\workspace\\workUtils\\src\\com\\njry\\clj\\util\\workUtils\\automaticdeploy\\info");
//        copy("E:\\test1","E:\\test2");
    	copy("E:\\myeclipse\\anhui", "E:\\myeclipse\\anhui2");
//        System.out.println(new File("E:\\test1\\���� ����\\�����������ź�ۣ�.xls").renameTo(new File("E:\\test1\\���� ����\\2.xls")));
    }

}
