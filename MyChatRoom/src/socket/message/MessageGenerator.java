/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package socket.message;

import com.alibaba.fastjson.JSON;

/** 
 * @description ��Ϣ
 * @author ������
 * @date 2018-12-10 
 */
public class MessageGenerator {

    public final static int TEXT = 1;
    public final static int FILE = 2;
    public final static int IMAGE = 3;
    public final static int INIT = 4;
    
    public static String getMessageJson(Message message){
        return JSON.toJSONString(message);
    } 
    
    public static Message getMessage(String messageJson){
    	 return JSON.parseObject(messageJson, Message.class);
    }
    
    public static void main(String[] args) {
//        System.out.println(getMessageJson(Message.getTextMessageForAll("sdf", "asds")));
//        String messageJson = getMessageJson(Message.getTextMessageForAll("sdf", "asds"));
//        Message message = getMessage(messageJson);
//        System.out.println(message);
    }
}