/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package socket.client.imp;

import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import socket.message.Message;
import socket.threadControl.ServerClientThreadControl;

/** 
 * @description ��������ʹ�õĿͻ���
 * @author ������
 * @date 2018-12-10 
 */
public class ServerClientImp extends BaseClient{
	
    public final BlockingQueue<Message> acceptMessageList = new ArrayBlockingQueue<Message>(100);
    
    public static int count;
    
    public ServerClientImp(Socket socket) throws Exception{
        super(socket);
    }
    
    //����Message�ж��Ƿ�����Ϣ
    public boolean isSendMessage(Message message){
        log.debug(message.sender);
        log.debug(getIpAddressName());
        if(message.sender.equals(getIpAddressName())){
            return false;
        }
        return true;
    }
    
    public void dealMessage(Message message) throws Exception {
        message.client = this;
        acceptMessageList.add(message);
    }

    /**
     * �������˵Ľ����߳�,���̳߳ش���
     */ 
    protected void startAccept() {
        log.debug(++count + "���ô���");
        ServerClientThreadControl.startAcceptorThread(new Acceptor());
    }

}