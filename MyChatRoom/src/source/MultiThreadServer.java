package source;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MultiThreadServer {

	 //采用ConcurrentHashMap存储所有连接到服务器客户端信息，避免线程安全问题，效率高
    private static Map<String,Socket> clientMap=new ConcurrentHashMap();
    //采用内部类具体处理每个客户端请求，并且内部类可以访问外部类私有属性
    private static class ExeccuteRealClient implements  Runnable
    {
        /*
		        服务器端功能：
		            注册用户：userName：
		            群聊：G：
		            私聊：P：用户-
		            用户退出：byebye
         */
        private Socket client;
        private ExeccuteRealClient(Socket client) {
            this.client = client;
        }
        public void run() {
            try {
                //获取客户端输入流，读取用户发来的信息
                //获取客户端输入流，读取用户发来的信息
                Scanner in=new Scanner(client.getInputStream());
                String str="";
                while(true)
                {
                    if(in.hasNextLine())
                    {    
                        str= in.nextLine();
                        //识别Windows下的换行符，将/r换成空字符串
                        Pattern pattern=Pattern.compile("\r");
                        Matcher matcher=pattern.matcher(str);
                        matcher.replaceAll("");
 
                        //用户注册
                        if(str.startsWith("userName"))
                        {
                            //userName:test
                            String userName=str.split("\\:")[1];
                            userRegister(userName,client);
                            continue;
                        }
 
                        //群聊
                        if(str.startsWith("G"))
                        {
                            //G:123
                            String msg=str.split("\\:")[1];
                            System.err.println(msg);
                            GroupChat(msg);
                            continue;
                        }
 
                        //私聊
                        if(str.startsWith("P"))
                        {
                            //P:test-123
                            String userName=str.split("\\:")[1].split("\\-")[0];
                            String msg=str.split("\\:")[1].split("\\-")[1];
                            privateChat(userName,msg);
                            continue;
                        }
 
                        //退出
                        if(str.contains("byebye"))
                        {
                            //遍历Map,获取userName,根据V找K
                            String userName="";
                            for (String key:clientMap.keySet()) {
                                if(clientMap.get(key).equals(client)){
                                    userName=key;
                                }
                            }
                            System.out.println("用户"+userName+"下线了");
                            clientMap.remove(userName);
                            System.out.println("当前聊天室一共还有"+clientMap.size()+"人");
                            continue;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 
        //用户注册方法
        private static void userRegister(String userName,Socket socket)
        {
            System.out.println("用户"+userName+"上线了");
            //将用户保存在map中
            clientMap.put(userName,socket);
            System.out.println("当前聊天室一共有"+clientMap.size()+"人");
            try {
                //告知用户注册成功
                PrintStream out=new PrintStream(socket.getOutputStream());
                out.println("用户注册成功");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 
        //群聊
        private static void GroupChat(String msg)
        {
            Set<Map.Entry<String,Socket>> clientSet=clientMap.entrySet();
            for (Map.Entry<String,Socket> entry:clientSet) {
                //遍历取出每个Socket
                Socket socket=entry.getValue();
                String userName = entry.getKey();
                try {
                    //获取每个Socket的输出流
                    System.out.println("群聊消息：用户'"+userName+"'说："+msg);
                    PrintStream out=new PrintStream(socket.getOutputStream());
                    out.println("群聊消息：用户'"+userName+"'说："+msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
 
        //私聊
        private static void privateChat(String userName,String msg)
        {
            //根据用户名获取指定Socket
            Socket socket=clientMap.get(userName);
            try {
                //获取指定socketde 输出流
                PrintStream out=new PrintStream(socket.getOutputStream(),true,"UTF-8");
                out.println("私聊消息："+msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws Exception {
        //建立大小为20的线程：通过线程池
        ExecutorService executorService= Executors.newFixedThreadPool(20);
        //建立基站
        ServerSocket serverSocket=new ServerSocket(6666);
        for (int i = 0; i <20 ; i++) {
            System.out.println("等待客户端连接");
            Socket client=serverSocket.accept();
            System.out.println("有新的客户端连接，端口号为："+client.getPort());
            System.out.println("有新的客户端连接，IP："+client.getInetAddress().getHostAddress());
            //每当有用户连接，新建线程处理
            executorService.submit(new ExeccuteRealClient(client));
        }
        executorService.shutdown();
        serverSocket.close();
    }
 
}
