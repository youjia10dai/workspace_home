package main.spring.database.aspect;

import java.lang.reflect.Method;

import main.spring.database.multDataSource.DBContextHolder;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;


/**
 * 
 * @description ʹ�ýӿڵķ���������֪ͨ
 * @author ������
 * @date 2018-09-28
 */
@Component("dataSourceAspect")//componentͨ�õ�ע��    ����������Spring�����������Ҳɨ���ȥ
@Aspect
public class DataSourceAspect implements MethodBeforeAdvice,AfterReturningAdvice 
{
    
    public final Logger log = Logger.getLogger(this.getClass());
	@Override//Ŀ�����֮��ִ��
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
	    //�����ǰ�߳�ʹ�õ����ݿ�(��¼ʹ�ù�һ��֮���û����)
	    DBContextHolder.clearDBType();
	}

	@Override//Ŀ�����֮ǰִ��
	public void before(Method method, Object[] args, Object target)
			throws Throwable {
	    //���ڷ�������,�ٵ�������(���þֲ���,����ȫ�ֵ�)
	    if(method.isAnnotationPresent(DataSource.class)){
            DataSource datasource = method.getAnnotation(DataSource.class);
            DBContextHolder.setDBType(datasource.name());
        }
	    else if(target.getClass().isAnnotationPresent(DataSource.class)){
	           DataSource datasource = target.getClass().getAnnotation(DataSource.class);
	           DBContextHolder.setDBType(datasource.name());
	    }else{
	        DBContextHolder.setDBType(DBContextHolder.zw3);
	    }
	}
}