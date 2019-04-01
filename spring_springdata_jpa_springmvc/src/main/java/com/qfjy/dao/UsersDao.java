package com.qfjy.dao;

import org.springframework.data.repository.Repository;

import com.qfjy.bean.Users;

/**
 * 接口: Repository空接口   空接口作用 : 不再是用于规范的.用于做标记的(是一个标记接口)
 *	SpringIOC容器会默认：加@Repostory/@Service注解
 *
 *	@RepositoryDefinition 注解 代替 extends Repository接口
 */

public interface UsersDao extends Repository<Users, Integer>{

	public Users getById(Integer id);
	
}
