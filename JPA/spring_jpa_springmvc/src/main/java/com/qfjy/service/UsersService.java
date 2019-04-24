package com.qfjy.service;

import java.util.List;

import com.qfjy.bean.Users;

public interface UsersService {
	public List<Users> findAll();
	
	public Users selectOne(Integer id);
	
	public void add(Users u);
	
	public int delete(Integer id);
	
	public void update(Users u);
}
