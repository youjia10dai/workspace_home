package com.qfjy.dao.imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import com.qfjy.bean.Users;
import com.qfjy.dao.UsersDao;

@Repository
public class UsersImpl implements UsersDao {

	//默认事物支持只读事物
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Users> findAll() {
		String querySql = "FROM Users";
		TypedQuery<Users> query = em.createQuery(querySql, Users.class);
		List<Users> results = query.getResultList();
		return results;
	}

	@Override
	public Users selectOne(Integer id) {
		Users users = em.find(Users.class, 1);
		return users;
	}

	@Override
	@Transactional
	public void add(Users u) {
		em.persist(u);
	}

	@Override
	@Transactional
	public int delete(Integer id) {
		//测试,应该不能删除
		String querySqlString = "delete from Users o where o.id=?1";
		Query query = em.createQuery(querySqlString);
		query.setParameter(1, id);
		int count = query.executeUpdate();
		return count;
	}

	@Override
	@Transactional
	public void update(Users u) {
		em.merge(u);
	}

}