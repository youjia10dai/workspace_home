/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package socket.message.deal;

import socket.message.Message;
import socket.message.MessageDeal;
import socket.server.Server;
import socket.server.imp.ServerImp;

/** 
 * 这里用一句话描述这个类的作用
 * @author 陈吕奖
 * @date 2018-12-10 
 */
public class DealInitMessage extends MessageDeal {
	
	/**
	 * @description 这里用一句话描述这个构造函数的作用
	 * @param message
	 * @param server
	 */
	public DealInitMessage(Message message, Server server) {
		super(message, server);
	}

	public void dealMessage() throws Exception {
	    //给服务器客户端设置ip地址,方便查找
	    message.client.user.ipAddressName = message.sender;
	    //回复链接成功信息
	    writeBack();
	}

    /** 
     * @description 回复信息
     * @author 陈吕奖 2018-12-23
     */ 
    private void writeBack() {
        Message writeBackMessage = (Message) message.clone();
        writeBackMessage.acceptor = writeBackMessage.sender;
        writeBackMessage.sender = Server.SYSETMIP;
        writeBackMessage.context = "链接成功";
        ((ServerImp)server).sendMessage(writeBackMessage);
    }

}
