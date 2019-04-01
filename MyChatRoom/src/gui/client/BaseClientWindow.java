/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package gui.client;

import entity.Friend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import org.apache.log4j.Logger;

import socket.client.imp.ClientImp;
import util.PropertyUtils;

/** 
 * @description ͨ�õĿͻ��˴���
 * @author ������
 * @date 2018-12-13 
 */
public abstract class BaseClientWindow extends JFrame implements ActionListener{

    public final Logger log = Logger.getLogger(this.getClass());
    public JTextArea acceptMessages;
    public JTextField sendContextField;
    protected JComboBox<String> friendsBox;
    protected JButton buttons[];
    
    protected String acceptorIpAdderName;
    protected ClientWindowController controller;
    protected ClientImp client;
    public Friend friends;
    
    public BaseClientWindow(ClientWindowController controller, ClientImp client, String acceptorIpAdderName)throws Exception
    {
        initSocket(controller, client);
        initFriend();
        initWindow(acceptorIpAdderName);
    }
    
    /** 
     * @description ������һ�仰�����������������
     * @author ������ 2018-12-12
     */ 
    private void initSocket(ClientWindowController controller, ClientImp client){
        this.controller = controller;
        this.client = client;
    }
    
    private void initFriend(){
        friends = Friend.createFriend(client.user);
    }
    
    public void initWindow(String acceptorIpAdderName){
        this.acceptorIpAdderName = acceptorIpAdderName;
        initComponent();
        buildWindow();
        addAllActionListener();
    }
    
    @SuppressWarnings("unchecked")
    private void initComponent(){
        //��ʼ�����
        this.acceptMessages = new JTextArea();
        this.acceptMessages.setEditable(false);
        buttons = new JButton[2];
        buttons[0] = new JButton("����");
        buttons[1] = new JButton("���");
        initFriendsComponent();
    }
    
    //����ѡ�� Ⱥ���͵������� ���ص������ǲ�һ����
    public abstract void initFriendsComponent();
    
    private void buildWindow(){
        //��������
        JToolBar toolBar = new JToolBar();
        toolBar.add(this.sendContextField=new JTextField(30));
        toolBar.add(buttons[0]);
        toolBar.add(friendsBox);
        toolBar.add(buttons[1]);
        log.debug("���ô�������" + friends.friendByName);
        this.setTitle(getFriendName(acceptorIpAdderName));
        this.setBounds(0, 0, 600, 600);
        this.getContentPane().add(new JScrollPane(this.acceptMessages));
        this.getContentPane().add(toolBar,"South");
        this.setVisible(true);
    }
    
    private void addAllActionListener(){
        buttons[0].addActionListener(this);
        buttons[1].addActionListener(this);
        friendsBox.addActionListener(this);//����ť����¼�������ί�е�ǰ������
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                closeWindow();
            }
        });
    }
    
    public abstract void closeWindow();
    
    //��������¼�
    public void actionPerformed(ActionEvent e) {
        try {
            dealEvent(e);
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    
    private void dealEvent(ActionEvent e) throws Exception{
        if(e.getActionCommand().equals("����"))
        {
            dealSendEvent();
        }
        if(e.getActionCommand().equals("���"))
        {
            clearAcceptMessage();
        }
        //
        if(e.getActionCommand().equals("comboBoxChanged")){
            log.debug(friends.getFriendIpAdderNameByName(friendsBox.getSelectedItem().toString()));
            controller.createClientWindow(friends.getFriendIpAdderNameByName(friendsBox.getSelectedItem().toString()));
        }
    }
    
    //Ⱥ���͵��������� �����͵ķ�ʽ�ǲ�һ��
    public abstract void dealSendEvent() throws Exception;
    

    
    public void showMessage(String context){
        synchronized (this) {
            acceptMessages.append(context +"\n");
        }
    }
    
    public void clearSendContext(){
        sendContextField.setText("");
    }
    
    private void clearAcceptMessage(){
        acceptMessages.setText("");
    }
    
    public String getFriendName(String ipAdderName){
        return friends.getFriendNameByIpAdderName(ipAdderName);
    }
    
    public String getFriendIpAdderName(String name){
        return friends.getFriendIpAdderNameByName(name);
    }
}