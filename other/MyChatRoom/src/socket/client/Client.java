/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package socket.client;

import socket.message.Message;

/** 
 * @description 客户端
 * @author 陈吕奖
 * @date 2018-12-07 
 */
public interface Client {
    
    
    String acceptMessage()throws Exception;
    
    void dealMessage(Message message)throws Exception;
    
    void close()throws Exception;
}