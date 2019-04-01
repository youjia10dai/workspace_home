package com.qfjy.dao.imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.qfjy.bean.Users;
import com.qfjy.dao.UsersDao;

@Repository
public class UsersImpl implements UsersDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Users> findAll() {
		String jpql = "FROM Users";
		TypedQuery<Users> query = em.createQuery(jpql, Users.class);
		List<Users> list = query.getResultList();
		return list;
	}

}
