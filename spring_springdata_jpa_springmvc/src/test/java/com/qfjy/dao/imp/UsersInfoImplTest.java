package com.qfjy.dao.imp;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qfjy.bean.Users;
import com.qfjy.bean.UsersInfo;
import com.qfjy.dao.UsersInfoDao;

@RunWith(SpringJUnit4ClassRunner.class)
//配置文件的位置
//若当前配置文件名=当前测试类名-context.xml 就可以在当前目录中查找@ContextConfiguration()
@ContextConfiguration("classpath*:spring_core.xml")
public class UsersInfoImplTest {
	@Autowired
	// 自动装配
	private ApplicationContext cxf;

	@Autowired
	private UsersInfoDao dao;
	
	@Test
	public void getById() {
		UsersInfo usersInfo = dao.getById("aaa");
		assertNotNull(usersInfo);
	}
	
}
