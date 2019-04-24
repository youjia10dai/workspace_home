import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import cn.itcast.util.JpaUtils;
import junit.framework.TestCase;

/**
 * @author 注意事项,
 * 			1.如果不能正常的运行(JAR导入错误),可以删除pom文件中的内容在还原,来实现Jar包的重新导入
 * 			2.注意要配置实体类
 *
 */
public class JPATest1 {

	public static EntityManager em;
	
    @Before
    public void setUp(){
    	System.out.println("获取数据库连接对象");
    	em = JpaUtils.getEntityManager();
    }
    
    @After
    public void tearDown(){
    	System.out.println("释放数据库连接");
    	JpaUtils.closeAll(em);
    }
	
	/** 
	 * 测试获取所有数据
	 * @author 陈吕奖 2019-03-21
	 */ 
	@Test
	public void findAll(){
		String jpql = "SELECT p FROM Person p";
		System.out.println("ffff");
		TypedQuery<Person> query = em.createQuery(jpql, Person.class);
		List<Person> list = query.getResultList();
		for (Person person : list) {
			System.out.println(person);
		}
		TestCase.assertEquals(4, list.size());
	}
	
	/** 
	 * 测试排序
	 * @author 陈吕奖 2019-03-21
	 */ 
	@Test
	public void sort(){
		String jpql = "SELECT p FROM Person p ORDER BY p.id DESC";//DESC降序,  ASC 升序
		TypedQuery<Person> query = em.createQuery(jpql, Person.class);
		List<Person> list = query.getResultList();
		for (Person person : list) {
			System.out.println(person);
		}
		TestCase.assertEquals(9, list.size());
	}
	
	/** 
	 * 测试返回一个数据
	 * @author 陈吕奖 2019-03-21
	 */ 
	@Test
	public void findOne(){
		String jpql = "SELECT COUNT(p.id) FROM Person p WHERE p.id = ?1";
		TypedQuery<Long> query = em.createQuery(jpql, Long.class);
		query.setParameter(1, 4);
		long count = query.getSingleResult();
		TestCase.assertEquals(1, count);
	}
	
	/** 
	 * 测试修改
	 * @author 陈吕奖 2019-03-22
	 */ 
	@Test
	public void update(){
		String jpql = "UPDATE Person o SET o.birthday=:birthday WHERE o.id=:id";
		Query query = em.createQuery(jpql);
		query.setParameter("birthday", new Date());
		query.setParameter("id", 5);
		int updateCount = query.executeUpdate();
		TestCase.assertEquals(1, updateCount);
	}
	
	/** 
	 * 测试查询部分数据(一条数据)  Object[]
	 * @author 陈吕奖 2019-03-22
	 */ 
	@Test
	public void queryPartDataOne(){
		String jpql = "SELECT name, birthday  FROM Person p WHERE p.id = ?1";
		Query query = em.createQuery(jpql);
		query.setParameter(1, 4);
		Object[] object = (Object[]) query.getSingleResult();
		System.out.println(object[0]);
		System.out.println(object[1]);
	}
	
	/** 
	 * 测试查询部分数据(多条数据) List<Object[]>
	 * @author 陈吕奖 2019-03-22
	 */ 
	@Test
	public void queryPartDataMany(){
		String jpql = "SELECT name, birthday FROM Person p";
		Query query = em.createQuery(jpql);
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();
		for (Object[] object : list) {
			System.out.println(object[0]);
			System.out.println(object[1]);
		}
//		System.out.println(object[0]);
//		System.out.println(object[1]);
	}
	
	/** 
	 * 测试查询部分数据   以对象返回
	 * @author 陈吕奖 2019-03-22
	 */ 
	@Test
	public void queryPartDataConverObject(){
		String jpql = "SELECT new Person(name, birthday) FROM Person p";
		TypedQuery<Person> query = em.createQuery(jpql, Person.class);
		List<Person> list = query.getResultList();
		for (Person person : list) {
			System.out.println(person);
		}
	}
	
	/** 
	 * 使用原生SQL语句查询
	 * @author 陈吕奖 2019-03-22
	 */ 
	@Test
	@SuppressWarnings("unchecked")
	public void nativeQuery(){
		String sql = "select * from person";
		Query query = em.createNativeQuery(sql, Person.class);
		List<Person> resultList = query.getResultList();
		for (Person person : resultList) {
			System.out.println(person);
		}
	}
	
}
