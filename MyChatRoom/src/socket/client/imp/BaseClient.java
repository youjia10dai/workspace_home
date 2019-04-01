/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package socket.client.imp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import entity.User;
import socket.client.Client;
import socket.message.Message;
import socket.message.MessageGenerator;
import socket.server.Server;
import util.BaseUtils;
import util.PropertyUtils;

/** 
 * @description 
 * @author ������
 * @date 2018-12-11 
 */
public abstract class BaseClient extends BaseUtils implements Client{
    
    public boolean isValid = true;
    public User user = new User();
    protected Socket socket;
    protected PrintWriter emitter;
    protected BufferedReader acceptor;
 
    //�ͻ��˹��캯��
    public BaseClient() throws Exception{
        log.debug(10);
        init();
    }
    
    //�������Ͽͻ��˹��캯��
    public BaseClient(Socket socket) throws Exception{
        init(socket);
    }
    
    public void init()throws Exception{
        this.user = User.createUser(PropertyUtils.loadProperties("user"));
        this.socket = new Socket(Server.IPADDRESSNAME, Server.PORT);
        initEmitter();
        initAcceptor();
        startAccept();
    }
    
    public void init(Socket socket)throws Exception{
        this.socket = socket;
        initEmitter();
        initAcceptor();
        startAccept();
    }
    
    /**
     * @description ��ʼ��������
     * @author ������ 2018-12-07
     * @throws Exception 
     */ 
    protected void initEmitter() throws Exception{
        OutputStream os = socket.getOutputStream();//�ֽ������
        emitter = new PrintWriter(os);//���������װ�ɴ�ӡ��
    }
    
    /** 
     * @description ��ʼ������
     * @author ������ 2018-12-07
     * @throws Exception 
     */ 
    protected void initAcceptor() throws Exception{
        acceptor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        log.debug("��������ʼ��");
    }
    
    public void sendMessage(Message message){
        String messageJson = MessageGenerator.getMessageJson(message);
        emitter.println(messageJson);
        emitter.flush();
    }
    
    public String getName(){
        return user.name;
    }
    
    public String getIpAddressName(){
        return user.ipAddressName;
    }
    
    public void close() throws Exception {
        socket.close();
    }
    
    /** 
     * @description ��ʼ����
     * @author ������ 2018-12-07
     */ 
    protected void startAccept() {
        Thread acceptThread = new Thread(new Acceptor());
        acceptThread.start();
    }
 
    public String acceptMessage() throws Exception {
        String message = acceptor.readLine();
        return message == null ? "" : message;
    }
    
    class Acceptor implements Runnable{
        public void run() {
            try {
                log.debug("�����߳̿�ʼ");
                getMessages();
            }
            catch (Exception e) {
                log.debug("�����߳̽���");
                e.printStackTrace();
                isValid = false;
            }
        }
        
        private void getMessages() throws Exception{
            while(true){
                Thread.sleep(30);
                log.info("������Ϣ");
                String messageJson = acceptMessage();
                log.debug(messageJson);
                if(!"".equals(messageJson)){
                    Message message = MessageGenerator.getMessage(messageJson);
                    dealMessage(message);
                }
            }
        }
    }
}