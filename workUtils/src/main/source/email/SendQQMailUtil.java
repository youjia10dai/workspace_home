package main.source.email;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendQQMailUtil {
    
    public static void main(String[] args) throws AddressException,MessagingException {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");// ����Э��
        properties.put("mail.smtp.host", "smtp.qq.com");// ������
        properties.put("mail.smtp.port", 465);// �˿ں�
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");// �����Ƿ�ʹ��ssl��ȫ���� ---һ�㶼ʹ��
        properties.put("mail.debug", "true");// �����Ƿ���ʾdebug��Ϣ true ���ڿ���̨��ʾ�����Ϣ
        // �õ��ػ�����
        Session session = Session.getInstance(properties);
        // ��ȡ�ʼ�����
        Message message = new MimeMessage(session);
        // ���÷����������ַ
        message.setFrom(new InternetAddress("xxx@qq.com"));
        // �����ռ��������ַ 
        message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress("xxx@qq.com"),new InternetAddress("xxx@qq.com"),new InternetAddress("xxx@qq.com")});
        //message.setRecipient(Message.RecipientType.TO, new InternetAddress("xxx@qq.com"));//һ���ռ���
        // �����ʼ�����
        message.setSubject("xmqtest");
        // �����ʼ�����
        message.setText("�ʼ������ʼ������ʼ�����xmqtest");
        // �õ��ʲ����
        Transport transport = session.getTransport();
        // �����Լ��������˻�
        transport.connect("xxx@qq.com", "xxxxxxxxxxxxx");// ����ΪQQ���俪ͨ��stmp�����õ��Ŀͻ�����Ȩ��
        // �����ʼ�
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}