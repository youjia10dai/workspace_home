package com.qfjy.service;

import org.springframework.data.domain.Page;

import com.qfjy.bean.Users;

/**
 * @author HP
 *
 */
public interface UsersPagingService {

	/**
	 * 分页
	 */
	public Page<Users> pageUsers(int pageNum, int pageSize);
	
	public Page<Users> pageUsers1(int pageNum, int pageSize);
	
	public Page<Users> pageUsers2(int pageNum, int pageSize);
	
	public Page<Users> pageUsers3(int pageNum, int pageSize);
}
