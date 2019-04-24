package com.qfjy.dao.royasoft;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;


/**
 *	加上NoRepositoryBean,表示这个接口不会单独的创建实现类
 *	只有在作为父接口的时候才会为他创建实现类
 */
@NoRepositoryBean
public interface BaseDao<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T>{


}