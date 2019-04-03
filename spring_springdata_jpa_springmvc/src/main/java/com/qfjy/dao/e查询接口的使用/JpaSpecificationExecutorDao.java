package com.qfjy.dao.e查询接口的使用;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.qfjy.bean.Users;

/**
 * 
 *	JpaSpecificationExecutor接口不能单独使用,要配合其他JPA接口使用
 */
public interface JpaSpecificationExecutorDao extends JpaRepository<Users, Integer>,JpaSpecificationExecutor<Users>{

}
