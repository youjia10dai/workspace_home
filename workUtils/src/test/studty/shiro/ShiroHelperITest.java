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
        //改方法在在整个类初始化之前调用
    }

    @AfterClass
    public static void tearDownAfterClass(){
        //改方法在在整个类初始化之后调用
    }

    @Before
    public void setUp(){
        //方法在测试方法前调用,一般用来做测试准备工作
        helper = new ShiroHelper();
    }

    @After
    public void tearDown(){
        //方法在测试方法之后抵用,一般做清理工作
    }

    @Test
    public void testLogin(){
        //构造数据
        String name = "zhang";
        String password = "123";
        //进行测试
        boolean isSuccess = helper.login(name, password);
        //检验操作是否得到期望的结果
        if(isSuccess){
            System.out.println("登入成功");
        }else{
            System.out.println("登入失败");
        }
    }

    @Test
    public void testSetAttribute(){
        helper.setAttribute("a", "A");
        Object attribute = helper.getAttribute("a");
        System.out.println(attribute);
        Assert.assertSame("获取session绑定的值", "A", helper.getAttribute("a"));
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