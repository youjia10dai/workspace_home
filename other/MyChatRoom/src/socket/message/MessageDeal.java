/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package socket.message;

import socket.server.Server;
import util.BaseUtils;

/** 
 * 这里用一句话描述这个类的作用
 * @author 陈吕奖
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
