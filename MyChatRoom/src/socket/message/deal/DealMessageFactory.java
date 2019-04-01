/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package socket.message.deal;

import socket.client.imp.BaseClient;
import socket.message.Message;
import socket.message.MessageDeal;
import socket.message.MessageGenerator;
import socket.server.Server;
import socket.server.imp.ServerImp;

/** 
 * 这里用一句话描述这个类的作用
 * @author 陈吕奖
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
