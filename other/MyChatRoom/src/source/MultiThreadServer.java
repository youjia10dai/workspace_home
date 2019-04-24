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

	 //����ConcurrentHashMap�洢�������ӵ��������ͻ�����Ϣ�������̰߳�ȫ���⣬Ч�ʸ�
    private static Map<String,Socket> clientMap=new ConcurrentHashMap();
    //�����ڲ�����崦��ÿ���ͻ������󣬲����ڲ�����Է����ⲿ��˽������
    private static class ExeccuteRealClient implements  Runnable
    {
        /*
		        �������˹��ܣ�
		            ע���û���userName��
		            Ⱥ�ģ�G��
		            ˽�ģ�P���û�-
		            �û��˳���byebye
         */
        private Socket client;
        private ExeccuteRealClient(Socket client) {
            this.client = client;
        }
        public void run() {
            try {
                //��ȡ�ͻ�������������ȡ�û���������Ϣ
                //��ȡ�ͻ�������������ȡ�û���������Ϣ
                Scanner in=new Scanner(client.getInputStream());
                String str="";
                while(true)
                {
                    if(in.hasNextLine())
                    {    
                        str= in.nextLine();
                        //ʶ��Windows�µĻ��з�����/r���ɿ��ַ���
                        Pattern pattern=Pattern.compile("\r");
                        Matcher matcher=pattern.matcher(str);
                        matcher.replaceAll("");
 
                        //�û�ע��
                        if(str.startsWith("userName"))
                        {
                            //userName:test
                            String userName=str.split("\\:")[1];
                            userRegister(userName,client);
                            continue;
                        }
 
                        //Ⱥ��
                        if(str.startsWith("G"))
                        {
                            //G:123
                            String msg=str.split("\\:")[1];
                            System.err.println(msg);
                            GroupChat(msg);
                            continue;
                        }
 
                        //˽��
                        if(str.startsWith("P"))
                        {
                            //P:test-123
                            String userName=str.split("\\:")[1].split("\\-")[0];
                            String msg=str.split("\\:")[1].split("\\-")[1];
                            privateChat(userName,msg);
                            continue;
                        }
 
                        //�˳�
                        if(str.contains("byebye"))
                        {
                            //����Map,��ȡuserName,����V��K
                            String userName="";
                            for (String key:clientMap.keySet()) {
                                if(clientMap.get(key).equals(client)){
                                    userName=key;
                                }
                            }
                            System.out.println("�û�"+userName+"������");
                            clientMap.remove(userName);
                            System.out.println("��ǰ������һ������"+clientMap.size()+"��");
                            continue;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 
        //�û�ע�᷽��
        private static void userRegister(String userName,Socket socket)
        {
            System.out.println("�û�"+userName+"������");
            //���û�������map��
            clientMap.put(userName,socket);
            System.out.println("��ǰ������һ����"+clientMap.size()+"��");
            try {
                //��֪�û�ע��ɹ�
                PrintStream out=new PrintStream(socket.getOutputStream());
                out.println("�û�ע��ɹ�");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 
        //Ⱥ��
        private static void GroupChat(String msg)
        {
            Set<Map.Entry<String,Socket>> clientSet=clientMap.entrySet();
            for (Map.Entry<String,Socket> entry:clientSet) {
                //����ȡ��ÿ��Socket
                Socket socket=entry.getValue();
                String userName = entry.getKey();
                try {
                    //��ȡÿ��Socket�������
                    System.out.println("Ⱥ����Ϣ���û�'"+userName+"'˵��"+msg);
                    PrintStream out=new PrintStream(socket.getOutputStream());
                    out.println("Ⱥ����Ϣ���û�'"+userName+"'˵��"+msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
 
        //˽��
        private static void privateChat(String userName,String msg)
        {
            //�����û�����ȡָ��Socket
            Socket socket=clientMap.get(userName);
            try {
                //��ȡָ��socketde �����
                PrintStream out=new PrintStream(socket.getOutputStream(),true,"UTF-8");
                out.println("˽����Ϣ��"+msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws Exception {
        //������СΪ20���̣߳�ͨ���̳߳�
        ExecutorService executorService= Executors.newFixedThreadPool(20);
        //������վ
        ServerSocket serverSocket=new ServerSocket(6666);
        for (int i = 0; i <20 ; i++) {
            System.out.println("�ȴ��ͻ�������");
            Socket client=serverSocket.accept();
            System.out.println("���µĿͻ������ӣ��˿ں�Ϊ��"+client.getPort());
            System.out.println("���µĿͻ������ӣ�IP��"+client.getInetAddress().getHostAddress());
            //ÿ�����û����ӣ��½��̴߳���
            executorService.submit(new ExeccuteRealClient(client));
        }
        executorService.shutdown();
        serverSocket.close();
    }
 
}
