/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package socket.client;

import socket.message.Message;

/** 
 * @description �ͻ���
 * @author ������
 * @date 2018-12-07 
 */
public interface Client {
    
    
    String acceptMessage()throws Exception;
    
    void dealMessage(Message message)throws Exception;
    
    void close()throws Exception;
}