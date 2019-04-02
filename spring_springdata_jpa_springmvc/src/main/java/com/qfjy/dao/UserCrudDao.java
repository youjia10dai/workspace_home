package com.qfjy.dao;

import org.springframework.data.repository.CrudRepository;

import com.qfjy.bean.Users;

public interface UserCrudDao extends CrudRepository<Users, Integer>{
	
}