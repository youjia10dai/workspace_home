/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package socket.client.imp;

import gui.client.ClientWindowController;
import socket.message.Message;

/** 
 * @description 客户端
 * @author 陈吕奖
 * @date 2018-12-07 
 */
public class ClientImp extends BaseClient{
    
    private ClientWindowController contorller;

    public static int count =0;
    
    public ClientImp(ClientWindowController windowControl) throws Exception{
        super();
        try {
            log.debug(++count + "客户端的数量");
            init(windowControl);
        }
        catch (Exception e) {
            log.debug("客户端失败初始化");
            e.printStackTrace();
        }
    }
    
    private void init(ClientWindowController controller)throws Exception{
        this.contorller = controller;
        sendInitMessage();
    }
    
    //发送信息用于服务器端的初始化
    private void sendInitMessage()throws Exception{
    	sendMessage(createMessageForInit());
    }
    
    private Message createMessageForInit(){
        return Message.getInitMessage(user);
    }
    
    public void sendTextMessageForAll(String context) throws Exception{
        log.debug("发送信息");
        sendMessage(createMessageForAllMessage(context));
    }
    
    private Message createMessageForAllMessage(String context){
        return Message.getTextMessageForAll(user, context);
    }
    
    public void sendTextMessageForOne(String acceptor, String context) throws Exception{
        log.debug("发送信息");
        sendMessage(createMessageForOneMessage(acceptor, context));
    }
    
    private Message createMessageForOneMessage(String acceptor, String context){
        return Message.getTextMessageForOne(user, acceptor, context);
    }
    
    public void dealMessage(Message message) throws Exception {
        log.info(message.context);
        contorller.showMessage(message);
    }

}