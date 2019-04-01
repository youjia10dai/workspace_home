package main.spring.database.aspect;
import java.lang.annotation.*;

/** 
 * @description 自定义的注解,标注需要切换到成指定数据源的地方
 * @author 陈吕奖
 * ElementType.TYPE 就表示可以写在类名的上方
 * @date 2018-09-28 
 */  
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    
    String name() default "zw3";//默认是账务3的数据库
}
/*
 *Documented 注解表明这个注解应该被 javadoc工具记录. 
 *默认情况下,javadoc是不包括注解的. 但如果声明注解时指定了 @Documented,
 *则它会被 javadoc 之类的工具处理, 所以注解类型信息也会被包括在生成的文档中
 */