/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package gui.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;

import socket.client.imp.ClientImp;

/** 
 * @description Ⱥ�Ŀͻ��˴���
 * @author ������
 * @date 2018-12-11 
 */
public class ClientWindowForAll extends BaseClientWindow {

    private static final long serialVersionUID = 1L;
    //��������������
    public static final String MAIN_CLIENT_NAME = "all";
    public ClientWindowForAll(ClientWindowController controller, ClientImp client, String acceptorIpAdderName)
            throws Exception {
        super(controller, client, acceptorIpAdderName);
    }
    
    @Override
    public void dealSendEvent() throws Exception{
        String _sendContext = sendContextField.getText();
        if(!"".equals(_sendContext.trim())){
            showMessage(client.getName() + ": "+ _sendContext);
            client.sendTextMessageForAll(_sendContext);
        }
        clearSendContext();
    }

    @Override
    public void initFriendsComponent() {
        String[] _allNames = friends.getAllNames();
        List<String> allNames = new ArrayList<String>();
        for(String name : _allNames) {
            if(!name.equals(client.getName()) && !name.equals("system")){
                allNames.add(name);
            }
        }
        log.debug(allNames);
        friendsBox = new JComboBox<String>(allNames.toArray(new String[]{}));
    }

    @Override
    public void closeWindow(){
        System.exit(0);
    }
    
}