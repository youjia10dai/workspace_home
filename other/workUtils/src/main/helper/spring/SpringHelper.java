package main.helper.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringHelper
{
    private static ApplicationContext applicationContext; // SpringӦ�������Ļ���
    static
    {
        applicationContext = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
    }
//    DBHelper db = (DBHelper)
//    Map map = db.queryForMap("select id from test1 where id = 1");
//    System.out.println(map.get("ID"));
    public static Object getBean(String beanName)
    {
        return applicationContext.getBean(beanName);
    }
	
}
