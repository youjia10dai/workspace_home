package com.qfjy.dao.b增删改查接口;

import org.springframework.data.repository.CrudRepository;

import com.qfjy.bean.Users;

public interface CrudRepositoryDao extends CrudRepository<Users, Integer>{
	
}