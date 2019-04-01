package cn.itcast.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

import cn.itcast.domain.Student;
import cn.itcast.domain.Teacher;

public class Test1 {
	
	/*
	 * 创建表是否正确
	 */
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

    
    /**
     * 多对多只是用于维护关系,数据还是要先创建
     * 
     * 只是创建数据
     */
    
    @Test public void save(){
    	
    	//相当于SessionFactory
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysqlJPA");  
        //相当于Session
        EntityManager em = factory.createEntityManager();
        //开启事物
        em.getTransaction().begin();
        Student s=new Student("clj");
        Teacher t=new Teacher("lhm");
        em.persist(s); //持久化实体  
        em.persist(t); //持久化实体  
        em.getTransaction().commit();  
        em.close();  
        factory.close();
    }
    
    /**
     * 学生和老师建立连接
     */
    @Test public void buildTS(){
    	//相当于SessionFactory
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysqlJPA");  
        //相当于Session
        EntityManager em = factory.createEntityManager();
        //开启事物
        em.getTransaction().begin();
        Student student = em.find(Student.class, 1);
        student.addTeacher(em.getReference(Teacher.class, 1));//延迟加载,这里的效率高,如果使用find将会到数据库查询
        em.persist(student);
        em.getTransaction().commit();  
        em.close();  
        factory.close();
    }
    /**
     * 学生和老师删除关系
     */
    @Test public void deleteTS(){
    	//相当于SessionFactory
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysqlJPA");  
        //相当于Session
        EntityManager em = factory.createEntityManager();
        //开启事物
        em.getTransaction().begin();
        Student student = em.find(Student.class, 1);
        student.removeTeacher(em.getReference(Teacher.class, 1));//延迟加载,这里的效率高,如果使用find将会到数据库查询
        em.persist(student);
        em.getTransaction().commit();  
        em.close();  
        factory.close();
    }
    
    /**
     * 被维护端的悲哀
     * 删除老师    因为老师是被维护端,所以没有权限去修改外键的信息
     * 所以要删除老师,必须通过学生将关系解除,才能删除
     */
    @Test public void deleteTeacher(){
    	//相当于SessionFactory
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysqlJPA");  
        //相当于Session
        EntityManager em = factory.createEntityManager();
        //开启事物
        em.getTransaction().begin();
//        Student student = em.find(Student.class, 1);
//        student.removeTeacher(em.getReference(Teacher.class, 1));//延迟加载,这里的效率高,如果使用find将会到数据库查询
        Student student = em.find(Student.class, 1);
        student.removeTeacher(em.getReference(Teacher.class, 1));
        em.remove(em.getReference(Teacher.class, 1));
        em.getTransaction().commit();  
        em.close();  
        factory.close();
    }
    
    /**
     * 删除学生   因为学生是关系维护方,有权限修改外键的信息
     * 所以可以直接的删除
     */
    @Test public void deleteStudent(){
    	//相当于SessionFactory
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysqlJPA");  
        //相当于Session
        EntityManager em = factory.createEntityManager();
        //开启事物
        em.getTransaction().begin();
//        Student student = em.find(Student.class, 1);
//        student.removeTeacher(em.getReference(Teacher.class, 1));//延迟加载,这里的效率高,如果使用find将会到数据库查询
        Student student = em.find(Student.class, 1);
        em.remove(student);
        em.getTransaction().commit();  
        em.close();  
        factory.close();
    }
}
