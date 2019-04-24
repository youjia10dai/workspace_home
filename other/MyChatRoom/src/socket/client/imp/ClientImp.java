/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package socket.client.imp;

import gui.client.ClientWindowController;
import socket.message.Message;

/** 
 * @description �ͻ���
 * @author ������
 * @date 2018-12-07 
 */
public class ClientImp extends BaseClient{
    
    private ClientWindowController contorller;

    public static int count =0;
    
    public ClientImp(ClientWindowController windowControl) throws Exception{
        super();
        try {
            log.debug(++count + "�ͻ��˵�����");
            init(windowControl);
        }
        catch (Exception e) {
            log.debug("�ͻ���ʧ�ܳ�ʼ��");
            e.printStackTrace();
        }
    }
    
    private void init(ClientWindowController controller)throws Exception{
        this.contorller = controller;
        sendInitMessage();
    }
    
    //������Ϣ���ڷ������˵ĳ�ʼ��
    private void sendInitMessage()throws Exception{
    	sendMessage(createMessageForInit());
    }
    
    private Message createMessageForInit(){
        return Message.getInitMessage(user);
    }
    
    public void sendTextMessageForAll(String context) throws Exception{
        log.debug("������Ϣ");
        sendMessage(createMessageForAllMessage(context));
    }
    
    private Message createMessageForAllMessage(String context){
        return Message.getTextMessageForAll(user, context);
    }
    
    public void sendTextMessageForOne(String acceptor, String context) throws Exception{
        log.debug("������Ϣ");
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