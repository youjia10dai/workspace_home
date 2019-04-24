package cn.itcast;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

import cn.itcast.bean.IdCrad;
import cn.itcast.bean.Person;

public class Test1 {

	@Test
	public void save() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysqlJPA");
		// 相当于Session
		EntityManager em = factory.createEntityManager();
		// 开启事物
		em.getTransaction().begin();
		Person p = new Person("ddd");
		p.setIdCrad(new IdCrad("ddddd"));

		em.persist(p);
		// 提交事务
		em.getTransaction().commit();
		em.close();
		factory.close();
	}
	
	@Test
	public void find(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysqlJPA");
		// 相当于Session
		EntityManager em = factory.createEntityManager();
		IdCrad crad = em.find(IdCrad.class, 1);
		em.close();
		factory.close();
	}
}
