/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package socket.server;

import java.util.Map;

import socket.client.Client;
import socket.client.imp.ServerClientImp;
import socket.message.Message;

/** 
 * @description 服务器端
 * @author 陈吕奖
 * @date 2018-12-07 
 */
public interface Server {

    int PORT = 10086;
    String IPADDRESSNAME = "localhost";
    
    String SYSETMIP = "255.255.255.255";
    
    /** 
     * @description 启动服务器
     * @author 陈吕奖 2018-12-07
     */ 
    void startServer()throws Exception;
    
    /** 
     * @description 获取所有已连接的客户端
     * @author 陈吕奖 2018-12-07
     * @return
     */ 
    Map<String, ServerClientImp> getAllClients()throws Exception;
    
    /** 
     * @description 根据ip地址获取客户端
     * @author 陈吕奖 2018-12-07
     * @param ipAddressName
     * @return
     */ 
    ServerClientImp findClientByIpAddressName(String ipAddressName)throws Exception;
    
    /**
     * @description 群发信息
     * @author 陈吕奖 2018-12-07
     * @param message
     */ 
    void groupSendMessages(Message message)throws Exception;
    
    /** 
     * @description 指定ip发送信息
     * @author 陈吕奖 2018-12-07
     * @param message
     * @param ipAddressName
     */ 
    void sendMessage(Message message)throws Exception;
    
}
