/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */
package socket.message;

import socket.client.imp.BaseClient;
import com.alibaba.fastjson.annotation.JSONField;
import entity.User;
import gui.client.ClientWindowForAll;

/**
 * @description 消息
 * @author 陈吕奖
 * @date 2018-12-10
 */
public class Message implements Cloneable {
    public int type;
    public String sender;
    public String acceptor;
    public String context;
    @JSONField(serialize = false)
    public BaseClient client;

    public Message() {
    }

    private Message(int type, String sender, String acceptor, String context) {
        this.type = type;
        this.sender = sender;
        this.acceptor = acceptor;
        this.context = context;
    }

    public static Message getTextMessageForAll(User user, String context) {
        return new Message(MessageGenerator.TEXT, user.ipAddressName, ClientWindowForAll.MAIN_CLIENT_NAME, context);
    }

    public static Message getTextMessageForOne(User user, String acceptIpAddressName, String context) {
        return new Message(MessageGenerator.TEXT, user.ipAddressName, acceptIpAddressName, context);
    }

    public static Message getInitMessage(User user) {
        return new Message(MessageGenerator.INIT, user.ipAddressName, null, "");
    }

    @Override
    public Object clone() {
        Message stu = null;
        try {
            stu = (Message) super.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return stu;
    }
}
