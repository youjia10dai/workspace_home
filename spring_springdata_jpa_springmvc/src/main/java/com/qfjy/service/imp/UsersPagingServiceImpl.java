package com.qfjy.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.qfjy.bean.Users;
import com.qfjy.dao.UsersPagingDao;
import com.qfjy.service.UsersPagingService;

/**
 * @author CLJ
 *
 */
@Service
public class UsersPagingServiceImpl implements UsersPagingService {

	@Autowired
	private UsersPagingDao dao;
	
	/**
	 *	分页的功能
	 *	int pageNum当前页数
	 *	int pageSize 页面大小
	 *
	 *	Sort分为两种构造凡是Order和Ditrection+property,如果是非常灵活的排序用Order
	 *	return  分页封装的工具类PageUtil
	 *				当前页数
	 *				页大小
	 *				总页数
	 *				总记录数
	 *				查询的结果集
	 *
	 *	@pageNum	当前页数  小标从0开始
	 *	@pageSize   一页的大小
	 */
	@Override
	public Page<Users> pageUsers(int pageNum, int pageSize) {
		//1.最简单的分页
		Pageable page = new PageRequest(pageNum, pageSize);
		Page<Users> pageContext = dao.findAll(page);
		return pageContext;
	}
	
	@Override
	public Page<Users> pageUsers1(int pageNum, int pageSize) {
		//带简单排序的分页
		Pageable page = new PageRequest(pageNum, pageSize, Direction.ASC, "age");
		Page<Users> pageContext = dao.findAll(page);
		return pageContext;
	} 
	
	@Override
	public Page<Users> pageUsers2(int pageNum, int pageSize) {
		//使用Sort进行统一的排序
		Sort sort = new Sort(Direction.ASC, "age");//可以配置多个字段,同时上升或下降
		Pageable page = new PageRequest(pageNum, pageSize, Direction.ASC, "age");
		Page<Users> pageContext = dao.findAll(page);
		return pageContext;
	}
	
	@Override
	public Page<Users> pageUsers3(int pageNum, int pageSize) {
		//使用Sort进行复杂的排序
		Order order1 = new Order(Direction.DESC, "age");
		Order order2 = new Order(Direction.ASC, "id");
		Sort sort = new Sort(order1, order2);//可以配置多个字段,同时上升或下降
		Pageable page = new PageRequest(pageNum, pageSize, sort);
		Page<Users> pageContext = dao.findAll(page);
		return pageContext;
	}
	
	
}
