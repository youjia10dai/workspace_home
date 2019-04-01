/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package socket.server.imp;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import socket.client.Client;
import socket.client.imp.ServerClientImp;
import socket.message.Message;
import socket.message.deal.DealMessageFactory;
import socket.server.Server;
import util.BaseUtils;

/** 
 * @description 服务器
 * @author 陈吕奖
 * @date 2018-12-10 
 */
public class ServerImp extends BaseUtils implements Server{

    private ServerSocket serverSocket;
    private BlockingQueue<ServerClientImp> clientList = new ArrayBlockingQueue<ServerClientImp>(100);
    private BlockingQueue<Message> acceptMessageList = new ArrayBlockingQueue<Message>(100);
    
    public ServerImp(){
        try {
            createServer();
            startServer();
        }
        catch (Exception e) {
            logError(e);
        }
    }
    
    /** 
     * @description 创建服务器
     * @author 陈吕奖 2018-12-10
     * @throws IOException 
     * @throws Exception 
     */ 
    public void createServer() throws IOException{
        serverSocket = new ServerSocket(Server.PORT);
    }

    /** 
     * @description 运行服务器 
     * @author 陈吕奖 2018-12-10
     * @throws IOException 
     */ 
    public void startServer() throws Exception {
        startMonitorClientConnection();
        getAllClientMessage();
        dealAllClientMessage();
    }

    /** 
     * @description 开启监听器,创建线程处理
     * @author 陈吕奖 2018-12-10
     * @throws Exception 
     */ 
    private void startMonitorClientConnection() throws Exception {
        log.debug("开启监听");
        Thread monitorThread = new Thread(new StartMonitor());
        monitorThread.start();
    }
    
    /** 
     * @description 获取客户端所有发送信息
     * @author 陈吕奖 2018-12-10
     */ 
    private void getAllClientMessage() {
        Thread clientMessage = new Thread(new GetClientMessage());
        clientMessage.start();
    }

    /** 
     * @description 处理所有的消息
     * @author 陈吕奖 2018-12-10
     */ 
    private void dealAllClientMessage() {
        log.debug("开始处理消息");
        Thread dealAllMessage = new Thread(new DealAllMessage());
        dealAllMessage.start();
    }
    
    public Map<String, ServerClientImp> getAllClients(){
        Map<String, ServerClientImp> allClient = new HashMap<String, ServerClientImp>();
        Iterator<ServerClientImp> iterator = clientList.iterator();
        log.debug(clientList.size());
        while(iterator.hasNext()){
            ServerClientImp client = iterator.next();
            allClient.put(client.getIpAddressName(), client);
        }
        return allClient;
    }

    public void groupSendMessages(Message message) throws Exception {
        log.debug(message);
        Map<String, ServerClientImp> allClients = getAllClients();
        Iterator<ServerClientImp> iterator = allClients.values().iterator();
        log.debug(clientList.size());
        while(iterator.hasNext()){
            ServerClientImp clinet = iterator.next();
            if(clinet.isValid && clinet.isSendMessage(message)){
                clinet.sendMessage(message);
            }else if(!clinet.isValid){
            	iterator.remove();
            }
        }
    }

    public void sendMessage(Message message) {
        log.debug(message.acceptor);
        ServerClientImp serverClient = findClientByIpAddressName(message.acceptor);
        log.debug(serverClient.isValid);
        log.debug(serverClient.isSendMessage(message));
        if(serverClient.isValid && serverClient.isSendMessage(message)){
            log.debug("发送消息");
            serverClient.sendMessage(message);
        }
    }

    public ServerClientImp findClientByIpAddressName(String ipAddressName) {
        return getAllClients().get(ipAddressName);
    }
    
    //监听生成客户端
    class StartMonitor implements Runnable{
        public void run(){
            while(true){
                try {
                    clientList.add(new ServerClientImp(serverSocket.accept()));
                    log.debug("创建一个连接");
                }
                catch (Exception e) {
                    logError(e);
                }
            }
        }
    }
    
    //获取所有的Message
    class GetClientMessage implements Runnable{
        public void run(){
            while(true){
                try {
                    getMessage();
                }
                catch (Exception e) {
                    logError(e);
                }
            }
        }
        
        public void getMessage() throws Exception{
            for(ServerClientImp client : clientList) {
                while(client.acceptMessageList.size() > 0){
                    Message message = client.acceptMessageList.remove();
                    log.debug("获取一条信息" + message);
                    acceptMessageList.put(message);
                }
            }
        }
    }
    
    //处理所有的Message
    class DealAllMessage implements Runnable{
        public void run() {
            try {
                dealMessage();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        public void dealMessage() throws Exception{
            while(true){
                log.debug("消息队列的数量" + acceptMessageList.size());
                Message message = acceptMessageList.take();
                DealMessageFactory.getMessageDeal(message, ServerImp.this).dealMessage();
                log.debug("处理完一条消息");
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        Server serverImp =new ServerImp();
//        serverImp.groupSend(MessageGenerator.getMessageJson(Message.getTextMessageForAll("fff", "fdfdf")));
    }
}
