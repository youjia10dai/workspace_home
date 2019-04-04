package com.qfjy.dao.imp.a顶层接口;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.log4j.chainsaw.Main;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import com.qfjy.bean.Users;
import com.qfjy.dao.a顶层接口.RepositoryDao;

@RunWith(SpringJUnit4ClassRunner.class)
// 配置文件的位置
// 若当前配置文件名=当前测试类名-context.xml 就可以在当前目录中查找@ContextConfiguration()
@ContextConfiguration("classpath*:spring_core.xml")
public class RepositoryDaoImplTest {

	@Autowired
	// 自动装配
	private ApplicationContext cxf;

	@Autowired
	private RepositoryDao dao;

	@Test
	public void getAllBeans() {
		// 获取所有的bean的name
		String[] names = cxf.getBeanDefinitionNames();
		for (String name : names) {
			System.out.println(name);
		}
	}

	@Test
	public void getById() {
		Users users = dao.getById(1);
		assertNotNull(users);
	}

	@Test
	public void findByUnameAndUpass() {
		Users users = dao.findByUnameAndUpass("qq", "q");
		assertNotNull(users);
	}

	@Test
	public void readBy() {
		List<Users> list = dao.readBy();
		assertTrue(list.size() > 0);
	}

	@Test
	public void countById() {
		long count = dao.countById(1);
		assertTrue(count > 0);
	}

	@Test
	public void findByUnameOrUpass() {
		List<Users> list = dao.findByUnameOrUpass("qq", "qwe");
		assertSame(list.size(), 2);
	}

	@Test
	public void findByAgeBetween() {
		List<Users> list = dao.findByAgeBetween(11, 22);
		assertSame(list.size(), 2);
	}

	@Test
	public void findByAgeGreaterThanEqualAndAgeLessThanEqual() {
		List<Users> list = dao.findByAgeGreaterThanEqualAndAgeLessThanEqual(11, 22);
		assertSame(list.size(), 2);
	}

	@Test
	public void getByUsersInfoTelphone() {
		Users users = dao.getByUsersInfoTelphone("13851537761");
		System.out.println(users);
	}

	@Test
	public void getByUsersInfo_Telphone() {
		Users users = dao.getByUsersInfo_Telphone("13851537761");
		System.out.println(users);
	}
	
	@Test
	public void testQuery() {
		List<Users> list = dao.maxAge();
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	@Test
	public void login(){
		Users user = dao.login("1", "11");
		assertNotNull(user);
	}

	@Test
	public void nativeSql(){
		List<Users> users = dao.nativeSql(10);
		assertTrue(users.size() > 0);
	}
	
	@Test
	public void updateById(){
		int count = dao.updateByid("clj218", 1);
		assertTrue(count == 1);
	}
	
	@Test
	public void insert(){
		int count = dao.insert("可以添加");
		assertTrue(count == 1);
	}
	
	@Test
	@Rollback(false)
	@Transactional()
	public void testTransactional(){
		int count = dao.updateByid("clj228", 1);
//		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		dao.insert("可以添加");
	}
}