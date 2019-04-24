/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */
package gui.client;

import javax.swing.JComboBox;
import socket.client.imp.ClientImp;

/**
 * @description ���Ŀͻ��˴���
 * @author ������
 * @date 2018-12-13
 */
public class ClientWindowForOne extends BaseClientWindow {

    private static final long serialVersionUID = 1L;

    public ClientWindowForOne(ClientWindowController controller, ClientImp client, String acceptorIpAdderName)
            throws Exception {
        super(controller, client, acceptorIpAdderName);
    }

    @Override
    public void dealSendEvent() throws Exception {
        String sendContext = sendContextField.getText();
        if(!"".equals(sendContext.trim())){
            showMessage(client.getName() + ": "+ sendContext);
            client.sendTextMessageForOne(acceptorIpAdderName ,sendContext);
        }
        clearSendContext();
    }

    @Override
    public void initFriendsComponent() {
        friendsBox = new JComboBox<String>(new String[]{getFriendName(acceptorIpAdderName)});
    }

    @Override
    public void closeWindow(){
        dispose();
        controller.closeClientWindow(this.acceptorIpAdderName);
    }

}