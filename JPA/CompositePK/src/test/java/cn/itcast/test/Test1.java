package cn.itcast.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

import cn.itcast.domain.AirLine;
import cn.itcast.domain.AirLinePK;


public class Test1 {

    
    @Test public void save(){
    	
    	//相当于SessionFactory
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysqlJPA");  
        //相当于Session
        EntityManager em = factory.createEntityManager();
        //开启事物
        em.getTransaction().begin();
        
        AirLine air=new AirLine(new AirLinePK("bei", "hai"), "北京到上海的航线");
        em.persist(air);
        em.getTransaction().commit();  
        em.close();  
        factory.close();
    }
}
