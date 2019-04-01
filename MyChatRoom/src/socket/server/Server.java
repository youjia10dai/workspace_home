/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package socket.server;

import java.util.Map;

import socket.client.Client;
import socket.client.imp.ServerClientImp;
import socket.message.Message;

/** 
 * @description ��������
 * @author ������
 * @date 2018-12-07 
 */
public interface Server {

    int PORT = 10086;
    String IPADDRESSNAME = "localhost";
    
    String SYSETMIP = "255.255.255.255";
    
    /** 
     * @description ����������
     * @author ������ 2018-12-07
     */ 
    void startServer()throws Exception;
    
    /** 
     * @description ��ȡ���������ӵĿͻ���
     * @author ������ 2018-12-07
     * @return
     */ 
    Map<String, ServerClientImp> getAllClients()throws Exception;
    
    /** 
     * @description ����ip��ַ��ȡ�ͻ���
     * @author ������ 2018-12-07
     * @param ipAddressName
     * @return
     */ 
    ServerClientImp findClientByIpAddressName(String ipAddressName)throws Exception;
    
    /**
     * @description Ⱥ����Ϣ
     * @author ������ 2018-12-07
     * @param message
     */ 
    void groupSendMessages(Message message)throws Exception;
    
    /** 
     * @description ָ��ip������Ϣ
     * @author ������ 2018-12-07
     * @param message
     * @param ipAddressName
     */ 
    void sendMessage(Message message)throws Exception;
    
}
