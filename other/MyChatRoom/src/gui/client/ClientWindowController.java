/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package gui.client;

import java.util.HashMap;
import java.util.Map;

import socket.client.imp.ClientImp;
import socket.message.Message;
import util.BaseUtils;

/** 
 * @description �ͻ��˴��ڹ�����
 * @author ������
 * @date 2018-12-12 
 */
public class ClientWindowController extends BaseUtils {


    public Map<String, BaseClientWindow> controller = new HashMap<String, BaseClientWindow>();
    private ClientImp client; 
    
    public ClientWindowController(){
        try {
            init();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void init() throws Exception{
        initSocket();
        createMainClient();
    }

    private void initSocket() throws Exception {
        client = new ClientImp(this);
    }

    private void createMainClient() throws Exception {
        createClientWindow(ClientWindowForAll.MAIN_CLIENT_NAME);
    }

    public void createClientWindow(String ipAdderName) throws Exception{
        if(!isExsit(ipAdderName)){
            log.debug("��ʼ������");
            BaseClientWindow clientWindow = createClientWindowByIpAdderName(ipAdderName);
            controller.put(ipAdderName, clientWindow);
        }else{
            getClientWindowByIpAdderName(ipAdderName).requestFocus();
        }
    }
    
    private boolean isExsit(String clientName){
        return controller.get(clientName) == null ? false : true;
    }
    
    public BaseClientWindow createClientWindowByIpAdderName(String ipAdderName) throws Exception{
        if(ClientWindowForAll.MAIN_CLIENT_NAME.equals(ipAdderName)){
            return new ClientWindowForAll(this, client, ipAdderName);
        }
        return new ClientWindowForOne(this, client, ipAdderName);
    }
    
    public BaseClientWindow getClientWindowByIpAdderName(String ipAdderName){
        return controller.get(ipAdderName);
    }
    
    public void closeClientWindow(String ipAdderName){
        controller.remove(ipAdderName);
    }
    
    /** 
     * @description ������Ϣ����Ϣ�����Ϣ��Ϣ��
     * @author ������ 2018-12-12
     * @param string
     * @throws Exception 
     */ 
    public void showMessage(Message message) throws Exception {
        log.debug(message.acceptor);
        //���������Ϊall,������Ҳ���all
        BaseClientWindow clientWindow ;
        if("all".equals(message.acceptor)){
            clientWindow = getClientWindowByUserName("all");
        }else{
            clientWindow = getClientWindowByUserName(message.sender);
        }
        if(clientWindow == null){
            createClientWindow(message.sender);
            clientWindow = getClientWindowByIpAdderName(message.sender);
        }
        log.debug("��������������Ϣ");
        clientWindow.showMessage(clientWindow.getFriendName(message.sender)+ ": " + message.context);
    }

    private BaseClientWindow getClientWindowByUserName(String ipAdderName) {
        return controller.get(ipAdderName);
    }

    public static void main(String[] args) throws Exception {
        ClientWindowController controller = new ClientWindowController();
    }
    
}
