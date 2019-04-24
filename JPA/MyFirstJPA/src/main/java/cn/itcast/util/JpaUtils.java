/*
 * Copyright (c) 2019  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package cn.itcast.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/** 
 * 这里用一句话描述这个类的作用
 * @author 陈吕奖
 * @date 2019-03-20 
 */
public class JpaUtils {

	public static String PERSISTENCEUNITNMAE = "mysqlJPA";
	
	private static EntityManagerFactory factory = null;
	
	
	static{
		factory = Persistence.createEntityManagerFactory(PERSISTENCEUNITNMAE);
	}
	
	public static EntityManager getEntityManager(){
		EntityManager entityManager = factory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		return entityManager;
	}
	
	public static void closeAll(EntityManager entityManager){
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.commit();
		entityManager.close();
	}
	
}
