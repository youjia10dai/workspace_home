package main.spring.database.aspect;
import java.lang.annotation.*;

/** 
 * @description �Զ����ע��,��ע��Ҫ�л�����ָ������Դ�ĵط�
 * @author ������
 * ElementType.TYPE �ͱ�ʾ����д���������Ϸ�
 * @date 2018-09-28 
 */  
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    
    String name() default "zw3";//Ĭ��������3�����ݿ�
}
/*
 *Documented ע��������ע��Ӧ�ñ� javadoc���߼�¼. 
 *Ĭ�������,javadoc�ǲ�����ע���. ���������ע��ʱָ���� @Documented,
 *�����ᱻ javadoc ֮��Ĺ��ߴ���, ����ע��������ϢҲ�ᱻ���������ɵ��ĵ���
 */