/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package socket.message.deal;

import socket.client.imp.BaseClient;
import socket.message.Message;
import socket.message.MessageDeal;
import socket.message.MessageGenerator;
import socket.server.Server;
import socket.server.imp.ServerImp;

/** 
 * ������һ�仰��������������
 * @author ������
 * @date 2018-12-10 
 */
public class DealMessageFactory {
    //Message message,, Server server
	public static MessageDeal getMessageDeal(Message message, Server server){
		switch (message.type) {
		case MessageGenerator.TEXT:
			return new DealTextMessage(message, server);
		case 2:
			
			break;
		case 3:
			
			break;
		case MessageGenerator.INIT:
			return new DealInitMessage(message, server);
		}
		return null;
	}
	
}
