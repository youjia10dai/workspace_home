package com.qfjy.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import com.qfjy.bean.Users;
import com.qfjy.dao.UsersDao;
import com.qfjy.service.UsersService;

@Service
@Controller
public class UsersServiceImp implements UsersService {

	@Autowired
	private UsersDao dao;
	
	@Override
	public List<Users> findAll() {
		return dao.findAll();
	}

}
