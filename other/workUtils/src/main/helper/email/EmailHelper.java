/*
 * Copyright (c) 2019  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.helper.email;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.PostConstruct;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import main.spring.config.PropertyConfigure;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sun.misc.BASE64Encoder;

/** 
 * @description �ʼ����͹�����
 * @author ������
 * @date 2019-01-22 
 */
@SuppressWarnings("restriction")
@Component("emailHelper")
//@Scope("prototype")
public class EmailHelper {
    public final Logger log = Logger.getLogger(this.getClass());
    public static String HOST = "SMTP.JS.CHINAMOBILE.COM";//smtp������  ��ͬ���������Ҫ��
    
    @Autowired
    private PropertyConfigure config;
    
    private String username;//�û���
    private String password;//����
    private String sender;//������  ��ʾ�ķ�������(���)
    private String receiver;//������ �������ʼ���ַ��  "wangq@njruiyue.cn,chenlj@njruiyue.cn"
    Properties props = System.getProperties();
    
    private Session session;
    private Message message;
    private Multipart multipart;
    private Transport transport;
    public EmailHelper(){
    }
    
    @PostConstruct
    public void init(){
        //�ó��Ľ���: ���췽�� > @Autowired > @PostConstruct
        try {
            initUser();
            initSession();
            initMessage();
        }
        catch (Exception e) {
            log.debug("�ʼ������ʼ���쳣");
            e.printStackTrace();
        }
    }
    
    private void initUser() throws Exception{
        username = config.getValue("username");
        password = config.getValue("password");
        sender = config.getValue("sender");
        receiver = config.getValue("receiver");
    }
    
    private void initSession(){
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", HOST);
        session = Session.getInstance(props, new javax.mail.Authenticator(){
            // ��session�������˻���Ϣ��Transport�����ʼ�ʱ��ʹ�� 
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
       });
    }
    
    private void initMessage() throws Exception{
        // �����ʼ�����  
        message = new MimeMessage(session);
        // ������(�������)
        message.setFrom(new InternetAddress(username, sender));        
        // �ռ��� 
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
    }
    
    public void send(String subject, String htmlContent) throws Exception{
        setMessage(subject, htmlContent, null);
        if(isConnected()){
            transport.sendMessage(message, message.getRecipients(javax.mail.Message.RecipientType.TO));
        }
    }
    
    public void send(String subject, String htmlContent, String[] attachNames) throws Exception{
        setMessage(subject, htmlContent, attachNames);
        if(isConnected()){
            transport.sendMessage(message, message.getRecipients(javax.mail.Message.RecipientType.TO));
        }
    }
    
    private void setMessage(String subject, String htmlContent, String[] attachNames) throws Exception{
        setSubject(subject);
        setContext(htmlContent);
        addAttachs(attachNames);
        message.setContent(multipart);
    }
    
    private void setSubject(String subject) throws Exception{
        message.setSubject(subject);
    }
    
    private void setContext(String htmlContent) throws Exception{
        multipart = new MimeMultipart();
        if (htmlContent != null){
            BodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlContent, "text/html;charset=UTF-8;");
            multipart.addBodyPart(htmlPart);
        }
    }
    
    public void addAttachs(String[] attachNames) throws Exception{
        addAttachsStringOrFile(attachNames);
    }
    
    public void addAttachs(File[] attachNames) throws Exception{
        addAttachsStringOrFile(attachNames);
    }
    
    private <T> void addAttachsStringOrFile(T[] attachNames) throws Exception{
        if(attachNames != null && attachNames.length != 0)
        {
            for(T attachName : attachNames) {
                addAttach(attachName);
            }
        }
    }
    
    @SuppressWarnings("restriction")
	private <T> void addAttach(T attachName)throws Exception{
        FileDataSource fileds = null;
        if(attachName instanceof File){
            fileds = new FileDataSource((File)attachName);
        }else {
            fileds = new FileDataSource((String)attachName);
        }
        BodyPart bp = new MimeBodyPart();
        bp.setDataHandler(new DataHandler(fileds));
        bp.setFileName("=?GBK?B?" + new BASE64Encoder().encode(fileds.getName().getBytes()) + "?=");
        multipart.addBodyPart(bp);
    }
    
    private boolean isConnected() throws Exception{
        transport = session.getTransport("smtp");
        transport.connect(HOST, username, password);
        System.out.println("�Ƿ����ӳɹ���"+ transport.isConnected());
        return transport.isConnected();
    }

}