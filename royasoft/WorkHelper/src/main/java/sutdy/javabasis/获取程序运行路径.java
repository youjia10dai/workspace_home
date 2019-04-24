package sutdy.javabasis;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

public class ��ȡ��������·�� {
	public static class RtuMain {

	    public static String getProjectPath() throws UnsupportedEncodingException {
	        URL url = RtuMain.class.getProtectionDomain().getCodeSource().getLocation();
	        //����ļ�Ŀ¼�� E:\workspace\workspace_home\���ٶ�\bin
	        //��ôurl.getPath()��ֵΪ :/E:/workspace/workspace_home/%e5%a4%9a%e5%b0%91%e5%ba%a6/bin/
	        //��Ҫ���н���
	        String filePath = URLDecoder.decode(url.getPath(), "utf-8");
	        if (filePath.endsWith(".jar")) {
	            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
	        }
	        File file = new File(filePath);
	        filePath = file.getAbsolutePath();
	        return filePath;
	    }

	    public static String getRealPath() {
	        String realPath = RtuMain.class.getClassLoader().getResource("").getFile();
	        File file = new File(realPath);
	        realPath = file.getAbsolutePath();
	        try {
	            realPath = URLDecoder.decode(realPath, "utf-8");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return realPath;
	    }
	}
	
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(RtuMain.getProjectPath());
        System.out.println(RtuMain.getRealPath());
    }
	
}
