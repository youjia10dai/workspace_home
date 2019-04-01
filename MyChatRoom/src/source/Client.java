/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */
package source;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @description �ͻ���
 * @author ������
 * @date 2018-12-07
 */
public class Client {
    //�ͻ���
    //1�������ͻ���Socket��ָ����������ַ�Ͷ˿�
    public static void main(String[] args)throws Exception{
        Socket socket = new Socket("localhost", 10086);
        //2����ȡ���������������˷�����Ϣ
        OutputStream os = socket.getOutputStream();//�ֽ������
        PrintWriter pw = new PrintWriter(os);//���������װ�ɴ�ӡ��
        pw.write("�û���:admin;����:123");
        pw.flush();
        socket.shutdownOutput();
        //3����ȡ������������ȡ�������˵���Ӧ��Ϣ
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String info = null;
        while((info = br.readLine()) != null) {
            System.out.println("���ǿͻ���,������˵:" + info);
        }

        //4���ر���Դ
        br.close();
        is.close();
        pw.close();
        os.close();
        socket.close();
    }
    
}
