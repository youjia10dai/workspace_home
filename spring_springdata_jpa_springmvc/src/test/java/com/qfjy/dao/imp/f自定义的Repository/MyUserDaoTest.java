package com.qfjy.dao.imp.f自定义的Repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qfjy.bean.Users;
import com.qfjy.dao.f自定义的Repository.MyUsersDao;

@RunWith(SpringJUnit4ClassRunner.class)
//配置文件的位置
//若当前配置文件名=当前测试类名-context.xml 就可以在当前目录中查找@ContextConfiguration()
@ContextConfiguration("classpath*:spring_core.xml")
public class MyUserDaoTest {

	@Autowired
	private MyUsersDao dao;
	
	@Test
	public void test(){
		Users users = dao.find(1);
		System.out.println(users);
	}
	
}
