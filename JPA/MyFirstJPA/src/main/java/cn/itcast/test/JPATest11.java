package cn.itcast.test;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.BeforeClass;
import org.junit.Test;

import cn.itcast.domain.Person;

public class JPATest11 {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * 表创建的时机
	 */
	@Test
	public void createTable() {
		// 可以验证生成表是否正确
		// 执行这里的时候表就已经创建了
		/*
		 * CREATE TABLE `person` ( `id` int(11) NOT NULL AUTO_INCREMENT, `name`
		 * varchar(12) DEFAULT NULL, PRIMARY KEY (`id`) ) ENGINE=InnoDB
		 * AUTO_INCREMENT=2 DEFAULT CHARSET=utf8
		 */
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysqlJPA");
		System.out.println(factory);
		factory.close();
	}

	// 1
	@Test
	public void save() {
		//1.得到EntityManagerFactory对象  mybatis：SqlSessionFactory  Hibernate:SessionFactory
		//线程安全的.且加载一次。(加载核心配置文件)
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysqlJPA");
		
		//2.得到EntityManager  mybatis：Session
		//线程不安全的.每次都需要创建一个会话.及时的进行关闭
		EntityManager em = factory.createEntityManager();
		
		//3.开启事物
		em.getTransaction().begin();
		
		//4.业务逻辑
		Person person = new Person(); // person为new状态
		person.setName("传智播客111");
		// 在Hibernate中以前使用的是save,但现在最好使用persist
		em.persist(person); // 持久化实体
		
		//5.提交事物
		em.getTransaction().commit();
		
		//6.关闭EntityManager
		em.close();
		
		//7.关闭EntityManagerFactory
		factory.close();
	}
	// new 、托管、脱管、删除 四种状态

	@Test
	public void update() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysqlJPA");
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Person person = em.find(Person.class, 2);
		person.setName("hmk"); // person为托管状态
		em.getTransaction().commit();
		em.close();
		factory.close();
	}

	@Test
	public void update2() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysqlJPA");
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Person person = em.find(Person.class, 2);
		em.clear(); // 把实体管理器中的所有实体变为脱管状态
		person.setName("hmk2");
		em.merge(person); // 把脱管状态变为托管状态,merge可以自动选择insert or update 数据
		em.getTransaction().commit();
		em.close();
		factory.close();
	}

	@Test
	public void remove() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysqlJPA");
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Person person = em.find(Person.class, 2);
		em.remove(person); // 删除实体
		em.getTransaction().commit();
		em.close();
		factory.close();
	}

	@Test
	public void find() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysqlJPA");
		EntityManager em = factory.createEntityManager();
		Person person = em.find(Person.class, 3); // 类似于hibernate的get方法,没找到数据时，返回null
		System.out.println(person.getName());
		em.close();
		factory.close();
	}

	@Test
	public void find2() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysqlJPA");
		EntityManager em = factory.createEntityManager();
		// 并且真正获取数据时连接不能关闭
		Person person = em.getReference(Person.class, 3); // 类似于hibernate的load方法,延迟加载.没相应数据时会出现异常
		System.out.println(person.getName()); // 真正调用时才查找数据
		em.close();
		factory.close();
	}

	/*
	 * 使用HQL语句进行查询
	 */
	@Test
	public void query() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysqlJPA");
		EntityManager em = factory.createEntityManager();
		/*
		 * 命名参数查询 id=:id
		 * 
		 * 位参数查询 ?1
		 */
		// 带转换的返回类型 也可以直接返回Query对象,自己进行强转
		TypedQuery<Person> query = em.createQuery("select o from Person o where o.id=?1", Person.class);
		query.setParameter(1, 1);
		Person person = query.getSingleResult();// getResultList()获取多结果的
		System.out.println(person.getName());
		em.close();
		factory.close();
	}

	/*
	 * 使用HQL语句进行删除  占位符表达式1
	 */
	@Test
	public void deleteQuery() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysqlJPA");
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		/*
		 * 命名参数查询 id=:id
		 * 
		 * 位参数查询 ?1
		 */
		// 带转换的返回类型 也可以直接返回Query对象,自己进行强转
		Query query = em.createQuery("delete from Person o where o.id=?1");
		// "update Person o set o.name=:name where o.id=:id"
		query.setParameter(1, 1);
		query.executeUpdate();
		em.getTransaction().commit();
		em.close();
		factory.close();
	}

	/*
	 * 使用HQL语句进行更新 占位符表达式2
	 */
	@Test
	public void deleteUpdate() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysqlJPA");
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		/*
		 * 命名参数查询 id=:id
		 * 
		 * 位参数查询 ?1
		 */
		// 带转换的返回类型 也可以直接返回Query对象,自己进行强转
		Query query = em.createQuery("update Person o set o.name=:name where o.id=:id");
		//
		query.setParameter("id", 2);
		query.setParameter("name", "ddd");

		query.executeUpdate();
		em.getTransaction().commit();
		em.close();
		factory.close();
	}
}
