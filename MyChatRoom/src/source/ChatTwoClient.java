/*
 * @author 计瑞
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
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author 计瑞
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
        super("我:"+name+InetAddress.getLocalHost().getHostAddress()+":"+socket.getLocalPort());
        multiThreadClient = MultiThreadClient.getInstance(socket);
        this.setBounds(320, 240, 400, 240);
        this.text_re=new JTextArea();
        this.text_re.setEditable(false);
        this.getContentPane().add(new JScrollPane(this.text_re));
        
        JToolBar toolBar=new JToolBar();
        this.getContentPane().add(toolBar,"South");
        toolBar.add(this.text_se=new JTextField(30));
        buttons=new JButton[2];
        buttons[0]=new JButton("发送");
        buttons[1]=new JButton("下线");
        toolBar.add(buttons[0]);
        toolBar.add(buttons[1]);
        buttons[0].addActionListener(this);
        buttons[1].addActionListener(this);//给按钮添加事件监听，委托当前对象处理
        this.setVisible(true);
        this.name=name;
       
        String line="连接"+multiThreadClient.getReciveMsg()+"成功";
        System.out.println("-----------"+multiThreadClient.getReciveMsg());
        while(!line.endsWith("bye"))
        {
            if(multiThreadClient.getReciveMsg() != null){
                text_re.append(multiThreadClient.getReciveMsg());
                multiThreadClient.setReciveMsg(null); //重新设置为null，不让该内容打印在面板上
            }
        }//读取对方发送的内容并显示，直到内容为为空或对方下线
        
        buttons[0].setEnabled(false);
        buttons[1].setEnabled(false);
    }
    public ChatTwoClient(String name,String host,int port) throws IOException
    {
        this(name,new Socket(host,port));//调用另一个构造方法
    }
    public void actionPerformed(ActionEvent ev)
    {
        if(ev.getActionCommand().equals("发送"))
        {
            multiThreadClient.setSendMsg(text_se.getText());
            text_re.append("我："+text_se.getText()+"\n");
            text_se.setText("");
        }//按下发送按钮后，将内容发出，并更新自己聊天框的内容
        if(ev.getActionCommand().equals("下线"))
        {
            text_re.append("你已下线\n");
            multiThreadClient.setSendMsg(name+"离线\n"+"bye\n");
            buttons[0].setEnabled(false);
            buttons[1].setEnabled(false);
        }//下线按钮按下后，发送bye作为下线标记
    }
    
 
    public static void main(String[] args) throws IOException {
        new ChatTwoClient("mxl","127.0.0.1",6666); //ip地址和端口
 
    }
 
} 
