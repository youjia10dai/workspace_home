/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package socket.message;

import socket.server.Server;
import util.BaseUtils;

/** 
 * ������һ�仰��������������
 * @author ������
 * @date 2018-12-10 
 */
public abstract class MessageDeal extends BaseUtils{
	
	protected Message message;
	protected Server server;
	
	public MessageDeal(Message message, Server server){
		this.message = message;
		this.server = server;
	}
	
	public abstract void dealMessage()throws Exception;
}
