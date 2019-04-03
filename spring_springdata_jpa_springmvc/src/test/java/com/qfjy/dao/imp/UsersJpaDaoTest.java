package com.qfjy.dao.imp;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.qfjy.dao.UsersJPADao;

@RunWith(SpringJUnit4ClassRunner.class)
//配置文件的位置
//若当前配置文件名=当前测试类名-context.xml 就可以在当前目录中查找@ContextConfiguration()
@ContextConfiguration("classpath*:spring_core.xml")
public class UsersJpaDaoTest {
	@Autowired
	private UsersJPADao dao;
	
	@Test
	public void getById(){
		List<Object[]> users = dao.getById(1);
		Object[] objects = users.get(0);
		System.out.println(objects[0]);
		System.out.println(objects[1]);
	}
	
}
