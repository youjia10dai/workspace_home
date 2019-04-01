/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package socket.message.deal;

import socket.message.Message;
import socket.message.MessageDeal;
import socket.server.Server;

/** 
 * 这里用一句话描述这个类的作用
 * @author 陈吕奖
 * @date 2018-12-10 
 */
public class DealTextMessage extends MessageDeal{


	public DealTextMessage(Message message, Server server) {
		super(message, server);
	}

	public void dealMessage() throws Exception {
	    log.debug(message.acceptor);
		if("all".equals(message.acceptor)){
			//群发
		    log.debug("进行群发");
			server.groupSendMessages(message);
		}else{
			//指定单个人发
		    server.sendMessage(message);
		}
	}

}
