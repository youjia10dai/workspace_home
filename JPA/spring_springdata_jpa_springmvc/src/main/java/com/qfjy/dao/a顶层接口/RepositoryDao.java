package com.qfjy.dao.a顶层接口;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.qfjy.bean.Users;

/**
 * 第一种方式
 * 接口: Repository空接口   空接口作用 : 不再是用于规范的.用于做标记的(是一个标记接口)
 *	SpringIOC容器会默认：加@Repostory/@Service注解
 * 
 * 第二种方式
 *	(不采用接口继承的方式,使用添加注解的方式)
 *	@RepositoryDefinition 注解 代替 extends Repository接口
 */

public interface RepositoryDao extends Repository<Users, Integer> {

	/*
	 * 方法声明(命名)规范: 按照Spring Data 的规范,查询方法以find | read | get 开头,By
	 * 涉及条件查询时,条件的属性用条件关键字连接,要注意的是:条件属性以首字母大写
	 * 
	 * SpringData做了以下的事 1.解析方法名称: getById String jpql =
	 * "FROM Users WHERE id = ?1"; Query query = em.createQuery(jpql,
	 * Users.class); query.setParameter(1, id); Users u =
	 * (Users)query.getSingleResult();//query.getResultList()/getSingleResult/
	 * executeUpdate
	 */

	public Users getById(Integer id);

	/**
	 * 根据用户名和密码进行条件查询 FROM Users WHERE unmae = ?1 and upass = ?2
	 */
	public Users findByUnameAndUpass(String unmae, String upass);

	/**
	 * 查询所有的数据
	 */
	public List<Users> readBy();

	/**
	 * 查询记录数
	 */
	public long count();

	/**
	 * 查询记录数根据ID
	 */
	public long countById(Integer id);

	/**
	 * 根据名字或者密码查找
	 */
	public List<Users> findByUnameOrUpass(String uname, String upass);

	/**
	 * 年龄在什么之间 >= <=
	 */
	public List<Users> findByAgeBetween(int min, int max);

	/**
	 * 年龄在什么之间 > <
	 */
	public List<Users> findByAgeGreaterThanEqualAndAgeLessThanEqual(int min, int max);

	/**
	 * 年龄为空,并且密码等于
	 */
	public List<Users> getByAgeIsNullAndUpass(String upass);

	/**
	 * 年龄为空,密码不为空
	 */
	public List<Users> getByAgeIsNullAndUpassIsNotNull();

	/**
	 * Like 查询用户名以java开头的 FROM Users WHERE uname like ?1 前后都未加% 自己参数中加%
	 */
	public List<Users> getByUnameLike(String uname);

	/**
	 * NotLike 查询用户 不包含java的所有用户 FROM Users WHERE uname not like '%java%'
	 */
	public List<Users> getByUnameNotLike(String uname);

	/**
	 * 查询名称以什么为开头的
	 */
	public List<Users> getByUnameStartingWith(String uname);

	/**
	 * 查询名称以什么结尾的
	 */
	public List<Users> getByUnameEndingWith(String uname);

	/**
	 * 查询名称包含
	 */
	public List<Users> getByUnameContaining(String uname);

	/**
	 * 查询名称以什么开头,并且根据年龄排序
	 */
	public List<Users> getByUnameStartingWithOrderByAgeDesc(String name);

	/**
	 * 查询年龄在 19, 20, 35, 38
	 */
	public List<Users> getByAgeIn(List<Integer> ages);

	/**
	 * 两个对象进行级联关系配置后,可以属性级联的方式查询
	 */
	public Users getByUsersInfoTelphone(String telphone);

	/**
	 * 如果级联的属性在本对象中刚好存在,可以使用_的方式来明确,使用的是级联操作
	 */
	public Users getByUsersInfo_Telphone(String telphone);

	/**
	 * 获取年龄最大的用户  无参数
	 */
	@Query(value = "FROM Users WHERE age = (SELECT max(age) FROM Users)")
	public List<Users> maxAge();

	/**
	 * 根据用户名和密码 涉及到传参的问题 FROM Users WHERE uname = ?1 AND upass = ?2 传参的方式支持两种:
	 * ?占位符方式默认从左侧:1  缺点:方法参数的位置必须要JPQL语句一一对应
	 * :name  命名参数的方式    有参数
	 */
	@Query(value = " FROM Users WHERE uname = ?1 and upass = ?2")
	public Users login(String uname, String upass);

	@Query(value = " FROM Users WHERE uname = :uname and upass = :upass")
	public Users loginParam(@Param("uname")String uname, @Param("upass")String upass);
	
	//使用本地SQL语句  查询所有的用户信息  查询所有年龄在20岁以上的(包含)
	@Query(value = "select * from users where age >= ?1", nativeQuery = true)
	public List<Users> nativeSql(int age);
	
	/**
	 * 更新操作
	 * SpringData 方法定义规范,自定义@Query  -->查询功能
	 * 根据主键ID,修改用户名 update users set name =?1 where id =?2
	 * 查询使用的方法是getResultList  getSingleResult  executeUpdate
	 */
	@Query(value = "update users set name =?1 where id =?2", nativeQuery = true)
	@Modifying
	public int updateByid(String name, Integer id);
	
	/**
	 * 添加操作
	 */
	@Query(value = "insert into users (name) values (:name)", nativeQuery = true)
	@Modifying
	public int insert(@Param("name")String name);
	
}
