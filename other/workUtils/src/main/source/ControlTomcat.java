package main.source;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ControlTomcat {

    public static String startUp = "cmd.exe /c D:\\tomcat6.0.45_x86\\bin\\startup.bat";
    public static String shutDown = "cmd.exe /c D:\\tomcat6.0.45_x86\\bin\\shutdown.bat";
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        ControlTomcat tom = new ControlTomcat();
        tom.runBar(ControlTomcat.startUp);
        Thread.sleep(10000);
        while(true){
            System.out.println("ControlTomcat.main()");
        }
//        tom.runBar(ControlTomcat.shutDown);
//        System.out.println("主线程结束");
    }
    
    public void runBar(String commond){
        //创建后台线程,启动完tomcat后直接退出程序
        Thread thread = new Thread(new RunBat(commond));
        thread.start();
    }
    
}

class RunBat implements Runnable{

    public String commond;
    
    public RunBat(String commond){
        this.commond = commond;
    }
    
    @Override
    public void run() {
        try {
            Process process = Runtime.getRuntime().exec(commond); // 调用外部程序
            final InputStream in = process.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder buf = new StringBuilder();
            String line = null;
            while((line = br.readLine()) != null)
                buf.append(line);
            System.out.println("输出结果为：" + buf);
        }
        catch (Exception e) {
            System.out.println("执行bat文件失败");
            e.printStackTrace();
        }
    }
}
//关键是exe参数命令cmd.exe /c D:\apache-tomcat-6.0.18\bin\startup.bat 
//如果要停止