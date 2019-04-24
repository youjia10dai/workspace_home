package main.source.zip;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
/**
 * Created by wzj on 2016/9/9.
 */
public class ��ѹ�ļ�
{
  /**
   * ��ѹ��ָ��Ŀ¼
   */
  public static void unZipFiles(String zipPath,String descDir)throws IOException
  {
    unZipFiles(new File(zipPath), descDir);
  }
  /**
   * ��ѹ�ļ���ָ��Ŀ¼
   */
  @SuppressWarnings("rawtypes")
  public static void unZipFiles(File zipFile,String descDir)throws IOException
  {
    File pathFile = new File(descDir);
    if(!pathFile.exists())
    {
      pathFile.mkdirs();
    }
    //���zip�ļ���������Ŀ¼���������ļ�
    ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));
    for(Enumeration entries = zip.entries(); entries.hasMoreElements();)
    {
      ZipEntry entry = (ZipEntry)entries.nextElement();
      String zipEntryName = entry.getName();
      InputStream in = zip.getInputStream(entry);
      System.out.println(descDir+zipEntryName);
      String outPath = (descDir+zipEntryName).replaceAll("\\*", "/");;
      //�ж�·���Ƿ����,�������򴴽��ļ�·��
      File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
      if(!file.exists())
      {
        file.mkdirs();
      }
      //�ж��ļ�ȫ·���Ƿ�Ϊ�ļ���,����������Ѿ��ϴ�,����Ҫ��ѹ
      if(new File(outPath).isDirectory())
      {
        continue;
      }
      //����ļ�·����Ϣ
      System.out.println(outPath);
      OutputStream out = new FileOutputStream(outPath);
      byte[] buf1 = new byte[1024];
      int len;
      while((len=in.read(buf1))>0)
      {
        out.write(buf1,0,len);
      }
      in.close();
      out.close();
    }
    System.out.println("******************��ѹ���********************");
  }
  public static void main(String[] args) throws IOException {
    /**
     * ��ѹ�ļ�
     */
    System.out.println(File.separator);
    File zipFile = new File("d:\\njcrm1.zip");
    String path = "d:\\���\\";
    unZipFiles(zipFile, path);
  }
}