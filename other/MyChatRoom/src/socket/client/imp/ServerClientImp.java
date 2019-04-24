/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package socket.client.imp;

import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import socket.message.Message;
import socket.threadControl.ServerClientThreadControl;

/** 
 * @description 服务器端使用的客户端
 * @author 陈吕奖
 * @date 2018-12-10 
 */
public class ServerClientImp extends BaseClient{
	
    public final BlockingQueue<Message> acceptMessageList = new ArrayBlockingQueue<Message>(100);
    
    public static int count;
    
    public ServerClientImp(Socket socket) throws Exception{
        super(socket);
    }
    
    //根据Message判断是否发送消息
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
     * 服务器端的接收线程,有线程池创建
     */ 
    protected void startAccept() {
        log.debug(++count + "调用次数");
        ServerClientThreadControl.startAcceptorThread(new Acceptor());
    }

}