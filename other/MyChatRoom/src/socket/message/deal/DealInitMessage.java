/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package socket.message.deal;

import socket.message.Message;
import socket.message.MessageDeal;
import socket.server.Server;
import socket.server.imp.ServerImp;

/** 
 * ������һ�仰��������������
 * @author ������
 * @date 2018-12-10 
 */
public class DealInitMessage extends MessageDeal {
	
	/**
	 * @description ������һ�仰����������캯��������
	 * @param message
	 * @param server
	 */
	public DealInitMessage(Message message, Server server) {
		super(message, server);
	}

	public void dealMessage() throws Exception {
	    //���������ͻ�������ip��ַ,�������
	    message.client.user.ipAddressName = message.sender;
	    //�ظ����ӳɹ���Ϣ
	    writeBack();
	}

    /** 
     * @description �ظ���Ϣ
     * @author ������ 2018-12-23
     */ 
    private void writeBack() {
        Message writeBackMessage = (Message) message.clone();
        writeBackMessage.acceptor = writeBackMessage.sender;
        writeBackMessage.sender = Server.SYSETMIP;
        writeBackMessage.context = "���ӳɹ�";
        ((ServerImp)server).sendMessage(writeBackMessage);
    }

}
