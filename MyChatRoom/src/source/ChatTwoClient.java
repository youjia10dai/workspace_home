/*
 * @author ����
 * @date 2018-12-06 08:53:24  
 * Copyright (c) 2018 Njry. All Rights Reserved.
 */ 
package source;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

/** 
 * @Description: TODO(������һ�仰��������������) 
 * @author ����
 * @date 2018-12-06 
 */

public class ChatTwoClient extends JFrame implements ActionListener {
    private String name;
    private JTextArea text_re;
    private JTextField text_se;
    private PrintWriter cout;
    private JButton buttons[];
    private MultiThreadClient multiThreadClient;
    public ChatTwoClient(String name,Socket socket) throws IOException
    {
        super("��:"+name+InetAddress.getLocalHost().getHostAddress()+":"+socket.getLocalPort());
        multiThreadClient = MultiThreadClient.getInstance(socket);
        this.setBounds(320, 240, 400, 240);
        this.text_re=new JTextArea();
        this.text_re.setEditable(false);
        this.getContentPane().add(new JScrollPane(this.text_re));
        
        JToolBar toolBar=new JToolBar();
        this.getContentPane().add(toolBar,"South");
        toolBar.add(this.text_se=new JTextField(30));
        buttons=new JButton[2];
        buttons[0]=new JButton("����");
        buttons[1]=new JButton("����");
        toolBar.add(buttons[0]);
        toolBar.add(buttons[1]);
        buttons[0].addActionListener(this);
        buttons[1].addActionListener(this);//����ť����¼�������ί�е�ǰ������
        this.setVisible(true);
        this.name=name;
       
        String line="����"+multiThreadClient.getReciveMsg()+"�ɹ�";
        System.out.println("-----------"+multiThreadClient.getReciveMsg());
        while(!line.endsWith("bye"))
        {
            if(multiThreadClient.getReciveMsg() != null){
                text_re.append(multiThreadClient.getReciveMsg());
                multiThreadClient.setReciveMsg(null); //��������Ϊnull�����ø����ݴ�ӡ�������
            }
        }//��ȡ�Է����͵����ݲ���ʾ��ֱ������ΪΪ�ջ�Է�����
        
        buttons[0].setEnabled(false);
        buttons[1].setEnabled(false);
    }
    public ChatTwoClient(String name,String host,int port) throws IOException
    {
        this(name,new Socket(host,port));//������һ�����췽��
    }
    public void actionPerformed(ActionEvent ev)
    {
        if(ev.getActionCommand().equals("����"))
        {
            multiThreadClient.setSendMsg(text_se.getText());
            text_re.append("�ң�"+text_se.getText()+"\n");
            text_se.setText("");
        }//���·��Ͱ�ť�󣬽����ݷ������������Լ�����������
        if(ev.getActionCommand().equals("����"))
        {
            text_re.append("��������\n");
            multiThreadClient.setSendMsg(name+"����\n"+"bye\n");
            buttons[0].setEnabled(false);
            buttons[1].setEnabled(false);
        }//���߰�ť���º󣬷���bye��Ϊ���߱��
    }
    
 
    public static void main(String[] args) throws IOException {
        new ChatTwoClient("mxl","127.0.0.1",6666); //ip��ַ�Ͷ˿�
 
    }
 
} 
