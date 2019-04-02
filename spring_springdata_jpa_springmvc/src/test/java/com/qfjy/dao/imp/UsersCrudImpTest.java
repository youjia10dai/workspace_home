package com.qfjy.dao.imp;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qfjy.bean.Users;
import com.qfjy.dao.UserCrudDao;

@RunWith(SpringJUnit4ClassRunner.class)
//配置文件的位置
//若当前配置文件名=当前测试类名-context.xml 就可以在当前目录中查找@ContextConfiguration()
@ContextConfiguration("classpath*:spring_core.xml")
public class UsersCrudImpTest {
	@Autowired
	private UserCrudDao dao;
	
	/**
	 * 实现添加操作
	 * JPA:
	 * 	em.persist(u);
	 *  tran.commit();
	 *  
	 * SpringData:
	 * 	@Transaction
	 */
	
	/**
	 * 添加操作
	 */
	@Test
	public void insert(){
		Users users = new Users();
		users.setUpass("123456");
		users.setUname("clj218");
		dao.save(users);
		//底层使用的JPA实现的
	}
	
	/**
	 * 批量添加
	 * 底层调用的是save(),还是一条一条的添加(并不是真正的批量添加操作)
	 * 但是事物是统一的,即有一条数据插入报错就全部回滚
	 * 和我们使用for循环来调save()不一样,一个save就是一个事物,出错不会全部的回滚
	 */
	@Test
	public void batchInsert(){
		List<Users> list = new ArrayList<Users>();
		Users users = new Users();
		users.setUpass("123456");
		users.setUname("clj318");
		
		Users users1 = new Users();
		users1.setUpass("123456");
		users1.setUname("clj318");
		
		list.add(users);
		list.add(users1);
		dao.save(list);
	}
	
	/**
	 * 批量删除方式:
	 * 	delete from users where id in(1,2,3,4,5);
	 * 
	 * 	crud的delete方法(deleteAll)都是单个删除,假删除
	 * 
	 * 	JpaRepository:删除 真批量删除
	 */
	@Test
	public void delete(){
		dao.deleteAll();
	}
	
}
