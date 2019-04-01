package com.qfjy.dao;

import java.util.List;

import com.qfjy.bean.Users;

/**
 * 持久化层包的定义规范
 *   mybatis:  com.qfjy.mapper
 *   
 *   hibernate: com.qfjy.dao
 *   
 *   SpringData + Jpa : com.qfjy.repository
 * 
 * 实体包
 * 	bean  entity  pojo  domain
 *
 */

public interface UsersDao {

	public List<Users> findAll();
	
}
