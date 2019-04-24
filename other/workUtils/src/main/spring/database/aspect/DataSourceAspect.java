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
 * @description 使用接口的方法来代替通知
 * @author 陈吕奖
 * @date 2018-09-28
 */
@Component("dataSourceAspect")//component通用的注解    添加这个是让Spring容器把这个类也扫描进去
@Aspect
public class DataSourceAspect implements MethodBeforeAdvice,AfterReturningAdvice 
{
    
    public final Logger log = Logger.getLogger(this.getClass());
	@Override//目标调用之后执行
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
	    //清除当前线程使用的数据库(记录使用过一次之后就没用了)
	    DBContextHolder.clearDBType();
	}

	@Override//目标调用之前执行
	public void before(Method method, Object[] args, Object target)
			throws Throwable {
	    //现在方法上找,再到类上找(先用局部的,在用全局的)
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