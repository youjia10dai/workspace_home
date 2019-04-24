package test.studty.shiro;

import main.study.shiro.ShiroHelper;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class ShiroHelperITest {

    private ShiroHelper helper;
    
    @BeforeClass
    public static void setUpBeforeClass(){
        //�ķ��������������ʼ��֮ǰ����
    }

    @AfterClass
    public static void tearDownAfterClass(){
        //�ķ��������������ʼ��֮�����
    }

    @Before
    public void setUp(){
        //�����ڲ��Է���ǰ����,һ������������׼������
        helper = new ShiroHelper();
    }

    @After
    public void tearDown(){
        //�����ڲ��Է���֮�����,һ����������
    }

    @Test
    public void testLogin(){
        //��������
        String name = "zhang";
        String password = "123";
        //���в���
        boolean isSuccess = helper.login(name, password);
        //��������Ƿ�õ������Ľ��
        if(isSuccess){
            System.out.println("����ɹ�");
        }else{
            System.out.println("����ʧ��");
        }
    }

    @Test
    public void testSetAttribute(){
        helper.setAttribute("a", "A");
        Object attribute = helper.getAttribute("a");
        System.out.println(attribute);
        Assert.assertSame("��ȡsession�󶨵�ֵ", "A", helper.getAttribute("a"));
    }

    @Test
    public void testGetUserName(){
        testLogin();
        System.out.println(helper.getUserName());
    }

    @Test
    public void testHasRole(){
        testLogin();
        System.out.println(helper.hasRole("admin"));
    }

    @Test
    public void testHasPermitted(){
        testLogin();
        System.out.println(helper.hasPermitted("perm3"));
    }

    @Test
    public void testLogout(){
        testLogin();
        helper.logout();
        System.out.println(helper.hasPermitted("guest"));
    }
}