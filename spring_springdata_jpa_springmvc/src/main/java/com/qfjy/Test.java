package com.qfjy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qfjy.dao.a顶层接口.RepositoryDao;

/**
 *	测试事务回滚
 */
@Service
public class Test {

	@Autowired
	private RepositoryDao dao;
	
    public static ApplicationContext context =null;
    static{       
         context = new ClassPathXmlApplicationContext("spring_core.xml");
    }
	
	
	public static void main(String[] args) {
		Test test = (Test) context.getBean(Test.class);
		test.test();
	}
	
	/**
	 * 事务已经回滚
	 */
	@Transactional()
	public void test(){
		RepositoryDao dao = context.getBean(RepositoryDao.class);
		int count = dao.updateByid("clj222", 1);
		if( 1==1){
			throw new RuntimeException("dd");
		}
		dao.insert("可以添加");
	}
	
}
