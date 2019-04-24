/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package socket.client;

import socket.server.Server;

/** 
 * @description 服务器创建的客户端
 * @author 陈吕奖
 * @date 2018-12-07 
 */
public interface ServerClient {
    
    void sendTextMessageForAll(String message)throws Exception;
    String acceptMessage()throws Exception;
    void close(Server server)throws Exception;
}
