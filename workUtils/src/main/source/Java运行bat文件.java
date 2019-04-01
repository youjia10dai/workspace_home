package main.source;
import java.io.IOException;
import java.io.InputStream;

public class Java运行bat文件 {
    public void runbat(String batName) {
        String cmd = "cmd /k start D:\\new\\news.bat";// pass
        try {
            Process ps = Runtime.getRuntime().exec(cmd);
            InputStream in = ps.getInputStream();
            int c;
            while ((c = in.read()) != -1) {
                System.out.print(c);// 如果你不需要看输出，这行可以注销掉
            }
            in.close();
            ps.waitFor();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("child thread donn");
    }

    public static void main(String[] args) {
        Java运行bat文件 test1 = new Java运行bat文件();
        test1.runbat("news");
        System.out.println("main thread");
    }
}