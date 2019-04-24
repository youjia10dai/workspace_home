/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */
package source;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description ��������
 * @author ������
 * @date 2018-12-07
 */
public class Server {
    public static void main(String[] args) throws Exception {
        /**
         * ����TCPЭ���Socketͨ�ţ�ʵ���û���¼�������
         */
        //1������һ����������Socket����ServerSocket��ָ���󶨵Ķ˿ڣ��������˶˿�
        ServerSocket serverSocket = new ServerSocket(10086);//1024-65535��ĳ���˿�
        //2������accept()������ʼ�������ȴ��ͻ��˵�����
        Socket socket = serverSocket.accept();
        //3����ȡ������������ȡ�ͻ�����Ϣ
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String info = null;
        while((info = br.readLine()) != null) {
            System.out.println("���Ƿ��������ͻ���˵��" + info);
        }
        socket.shutdownInput();//�ر�������
        //4����ȡ���������Ӧ�ͻ��˵�����
        OutputStream os = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(os);
        pw.write("��ӭ����");
        pw.flush();

        //5���ر���Դ
        pw.close();
        os.close();
        br.close();
        isr.close();
        is.close();
        socket.close();
        serverSocket.close();
    }
}
