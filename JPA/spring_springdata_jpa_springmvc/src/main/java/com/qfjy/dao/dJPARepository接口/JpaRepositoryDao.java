package com.qfjy.dao.dJPARepository接口;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.qfjy.bean.Users;

public interface JpaRepositoryDao extends JpaRepository<Users, Integer>{

	
	//查询部分字段
	@Query(value ="select a.uname, b.`name` "
		       	+ "  from users a left join usersinfo b "
	        	+ "    on a.id = b.uid "
	        	+ "  where a.id = ?1",nativeQuery = true)
	public List<Object[]> getById(Integer id);
}
