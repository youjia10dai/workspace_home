package com.qfjy.dao.f自定义的Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.qfjy.bean.Users;

//@Repository
public class MyUsersDaoImpl implements MyUsersRepository{
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public Users find(Integer id) {
		System.out.println("自己的实现");
		Users users = em.find(Users.class, id);
		return users;
	}
}
