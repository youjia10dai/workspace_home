package com.qfjy.dao.imp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qfjy.bean.Users;
import com.qfjy.dao.JpaSpecificationExecutorDao;

@RunWith(SpringJUnit4ClassRunner.class)
//配置文件的位置
//若当前配置文件名=当前测试类名-context.xml 就可以在当前目录中查找@ContextConfiguration()
@ContextConfiguration("classpath*:spring_core.xml")
public class JpaSpecificationExecutorImplTest {

	@Autowired
	private JpaSpecificationExecutorDao dao;
	
	//单条件查询
	@Test
	public void testSingleCondition(){
		Specification<Users> spec = new Specification<Users>(){

			/**
			 * @return Predicate: 定义了查询条件
			 * @param Root<User> root: 跟对象。封装了查询条件对象
			 * @param CriteriaQuery<?> query:定义了一个基本的查询。一般不使用
			 * @param CriteriaBuilder cd:创建一个查询条件
			 */
			@Override
			public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.equal(root.get("uname"), "qq");
				return predicate;
			}
			
		};
		List<Users> list = dao.findAll(spec);
		for (Users users : list) {
			System.out.println(users);
		}
	}

	//多条件查询 
	@Test
	public void testMulCondition(){
		List<Users> list = dao.findAll(new Specification<Users>() {
			@Override
			public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				list.add(cb.equal(root.get("uname"), "qq"));
				list.add(cb.equal(root.get("upass"), "q"));
				return cb.and(list.toArray(new Predicate[]{}));
			}
		});
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	//多条件查询,并分页
	@Test
	public void testMulConditionAndPage(){
		Specification<Users> specification = new Specification<Users>() {
			@Override
			public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.like(root.get("uname").as(String.class), "3%");
				return predicate;
			}
		};
		Pageable pageable = new PageRequest(0, 3);
		Page<Users> pageInof = dao.findAll(specification, pageable);
		for (Users users : pageInof) {
			System.out.println(users);
		}
	}
	
	//多条件查询并排序
	@Test
	public void testMulConditionAndOrder(){
		Specification<Users> specification = new Specification<Users>() {
			@Override
			public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.like(root.get("uname").as(String.class), "3%");
				return predicate;
			}
		};
		Sort sort = new Sort(Direction.DESC, "age");
		List<Users> list = dao.findAll(specification, sort);
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	//多条件查询排序并分页
	@Test
	public void testMulConditionAndPageAndOrder(){
		Specification<Users> specification = new Specification<Users>() {
			@Override
			public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.like(root.get("uname").as(String.class), "3%");
				return predicate;
			}
		};
		Sort sort = new Sort(Direction.DESC, "age");
		Pageable pageable = new PageRequest(0, 1, sort);
		Page<Users> pageInfo = dao.findAll(specification, pageable);
		for (Users users : pageInfo) {
			System.out.println(users);
		}
	}
	
}
