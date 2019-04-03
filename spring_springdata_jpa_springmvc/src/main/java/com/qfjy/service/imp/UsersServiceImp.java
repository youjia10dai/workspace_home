package com.qfjy.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfjy.bean.Users;
import com.qfjy.dao.UsersDao;
import com.qfjy.service.UsersService;

@Service
public class UsersServiceImp implements UsersService{

	@Autowired
	private UsersDao dao;
	
	@Override
	public Users getById(Integer id) {
		return dao.getById(id);
	}

}
