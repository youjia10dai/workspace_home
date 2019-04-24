/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package socket.message.deal;

import socket.message.Message;
import socket.message.MessageDeal;
import socket.server.Server;

/** 
 * ������һ�仰��������������
 * @author ������
 * @date 2018-12-10 
 */
public class DealTextMessage extends MessageDeal{


	public DealTextMessage(Message message, Server server) {
		super(message, server);
	}

	public void dealMessage() throws Exception {
	    log.debug(message.acceptor);
		if("all".equals(message.acceptor)){
			//Ⱥ��
		    log.debug("����Ⱥ��");
			server.groupSendMessages(message);
		}else{
			//ָ�������˷�
		    server.sendMessage(message);
		}
	}

}
