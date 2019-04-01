/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package socket.client;

import socket.server.Server;

/** 
 * @description �����������Ŀͻ���
 * @author ������
 * @date 2018-12-07 
 */
public interface ServerClient {
    
    void sendTextMessageForAll(String message)throws Exception;
    String acceptMessage()throws Exception;
    void close(Server server)throws Exception;
}
