package com.qfjy.dao.imp.c分页和排序;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qfjy.bean.Users;
import com.qfjy.service.UsersPagingService;

@RunWith(SpringJUnit4ClassRunner.class)
// 配置文件的位置
// 若当前配置文件名=当前测试类名-context.xml 就可以在当前目录中查找@ContextConfiguration()
@ContextConfiguration("classpath*:spring_core.xml")
public class PagingAndSortingRepositoryDaoImplTest {
	@Autowired
	private UsersPagingService service;

	@Test
	public void pageUsers() {
		//页数从0开始
		Page<Users> pageUsers = service.pageUsers(0, 4);
		List<Users> content = pageUsers.getContent();
		System.out.println("当前页有多少元素"+content.size());
		for (Users users : content) {
			System.out.println(users);
		}
		int totalPages = pageUsers.getTotalPages();
		System.out.println("总页数" + totalPages);
		
		int size = pageUsers.getSize();
		System.out.println("页大小" + size);
		
		long elements = pageUsers.getTotalElements();
		System.out.println("总记录数" + elements);
		
		int number = pageUsers.getNumber();
		System.out.println("当前页数:" + (number + 1));
		
		int numberOfElements = pageUsers.getNumberOfElements();
		System.out.println("查询出的记录数:" + numberOfElements);
	}

	@Test
	public void pageUsers1() {
		Page<Users> pageUsers = service.pageUsers1(0, 4);
		List<Users> content = pageUsers.getContent();
		for (Users users : content) {
			System.out.println(users);
		}
	}

	@Test
	public void pageUsers2() {
		Page<Users> pageUsers = service.pageUsers2(0, 4);
		List<Users> content = pageUsers.getContent();
		for (Users users : content) {
			System.out.println(users);
		}
	}
	
	@Test
	public void pageUsers3() {
		Page<Users> pageUsers = service.pageUsers3(0, 4);
		List<Users> content = pageUsers.getContent();
		for (Users users : content) {
			System.out.println(users);
		}
	}
	
	
}
