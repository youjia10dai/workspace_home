package com.qfjy.dao.imp;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qfjy.bean.Users;
import com.qfjy.dao.UsersDao;

@RunWith(SpringJUnit4ClassRunner.class)
//配置文件的位置
//若当前配置文件名=当前测试类名-context.xml 就可以在当前目录中查找@ContextConfiguration()
@ContextConfiguration("classpath*:spring_core.xml")
public class UsersImplTest  {

    @Autowired
    //自动装配
    private ApplicationContext cxf;
    
    @Autowired
    private UsersDao dao;
    
    @Test
    public void getAllBeans(){
		//获取所有的bean的name
		String[] names = cxf.getBeanDefinitionNames();
		for (String name : names) {
			System.out.println(name);
		}
    }
    
    @Test
	public void findAll() {
    	List<Users> users = dao.findAll();
    	for (Users user : users) {
			System.out.println(user);
		}
    	assertTrue(users.size() > 0);
	}
    
    @Test
    public void slectOne(){
    	Users users = dao.selectOne(1);
    	assertSame(users.getUname(), "1");
    }
    
    @Test
    public void add(){
    	Users users = new Users();
    	users.setUname("10");
    	users.setName("10");
    	dao.add(users);
    }
    
    @Test
    public void delete(){
    	int count = dao.delete(10);
    	assertSame(count, 1);
    }
    
    @Test
    public void update(){
    	Users users = new Users();
    	users.setId(3);
    	users.setName("3333");
    	dao.update(users);
    }
}