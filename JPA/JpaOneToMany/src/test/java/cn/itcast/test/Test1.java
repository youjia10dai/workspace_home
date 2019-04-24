package cn.itcast.test;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

import cn.itcast.bean.Order;
import cn.itcast.bean.OrderItem;
import cn.itcast.util.UUIDUtils;
public class Test1 {
    @Test public void createTable(){  
        //可以验证生成表是否正确 
    	//执行这里的时候表就已经创建了
    	/*
    	 * CREATE TABLE `person` (
			  `id` int(11) NOT NULL AUTO_INCREMENT,
			  `name` varchar(12) DEFAULT NULL,
			  PRIMARY KEY (`id`)
		   ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8
    	 */
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysqlJPA");  
        factory.close();  
    }  

    @Test public void save(){
    	 EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysqlJPA");  
	        //相当于Session
	        EntityManager em = factory.createEntityManager();
	        //开启事物
	        em.getTransaction().begin();
	        
	        //创建Order对象
	        Order o=new Order();
	        o.setAmount(999f);
	        o.setId(UUIDUtils.get12UUID());
	        
	        //创建OrderItem对象
	        OrderItem o1=new OrderItem();
	        o1.setCellPrice(99f);
	        o1.setName("篮球");
	        OrderItem o2=new OrderItem();
	        o2.setCellPrice(66f);
	        o2.setName("足球");
	        
	        o.addOrderItem(o1);
	        o.addOrderItem(o2);
	        
	        em.persist(o);
	        //提交事务
	        em.getTransaction().commit();  
	        em.close();  
	        factory.close(); 
    }
    @Test public void delete(){
   	 EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysqlJPA");  
	        //相当于Session
	        EntityManager em = factory.createEntityManager();
	        //开启事物
	        em.getTransaction().begin();
	        
	        Order o=em.find(Order.class, "00815fea4c98");
//	        Order merge = em.merge(o);//这个对象才是可以删除的
	        
	        em.remove(o);
	        //提交事务
	        em.getTransaction().commit();  
	        em.close();  
	        factory.close(); 
   }
}
