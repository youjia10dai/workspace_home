package source;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import sun.security.jca.GetInstance;

public class MultiThreadClient {

    private String reciveMsg;
    private String sendMsg;
    private static MultiThreadClient singletonClient = new MultiThreadClient();
    
    public String getReciveMsg() {
        return reciveMsg;
    }

    public void setReciveMsg(String reciveMsg) {
        this.reciveMsg = reciveMsg;
    }

    public String getSendMsg() {
        return sendMsg;
    }

    public void setSendMsg(String sendMsg) {
        this.sendMsg = sendMsg;
    }


    /*
    客户端读取服务器发来信息线程
     */
   private  class ReadFromServer implements  Runnable
    {
        private Socket client;
        public ReadFromServer(Socket client ) {
            this.client = client;
        }
     
        public void run() {
            try {
                Scanner in=new Scanner(client.getInputStream());
                while(true)
                {
                    if(in.hasNextLine())
                    {
                        reciveMsg = in.nextLine();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
     
   /*
   向服务器发送信息
    */
   private class WriteToServer implements  Runnable
   {
       private Socket client;
       public WriteToServer(Socket client) {
           this.client = client;
       }
    
       public void run() {
    
           try {
               PrintStream out=new PrintStream(client.getOutputStream());
               while(true)
               {
                   if(sendMsg != null && !sendMsg.equals(""))
                   {
                       out.println(sendMsg);
                       sendMsg = null;//避免无限次想服务端发送消息
                   }
    
                   //设置退出标致
                   if(sendMsg != null && !sendMsg.equals("") && sendMsg.contains("beybey"))
                   {
                       out.close();
                       client.close();
                       break;
                   }
               }
               client.shutdownOutput();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }
   
   private MultiThreadClient()
   {

   }
   
   public static MultiThreadClient getInstance(Socket socket)
   {
       Thread threadRead = new Thread(singletonClient.new ReadFromServer(socket));
       Thread threadWrite = new Thread(singletonClient.new WriteToServer(socket));
       threadRead.start();
       threadWrite.start();
       return singletonClient;
   }
}


