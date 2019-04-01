package com.qfjy.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qfjy.bean.Users;
import com.qfjy.dao.UsersDao;
import com.qfjy.service.UsersService;

@Service
public class UsersServiceImp implements UsersService {

	@Autowired
	private UsersDao dao;

	@Override
	public List<Users> findAll() {
		
		return dao.findAll();
	}

	@Override
	public Users selectOne(Integer id) {
		return dao.selectOne(id);
	}

	@Override
	public void add(Users u) {
		dao.add(u);
	}

	@Override
	public int delete(Integer id) {
		return dao.delete(id);
	}

	@Override
	public void update(Users u) {
		dao.update(u);
	}
	
}
