/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.source.common.clone;

import org.apache.commons.beanutils.BeanUtils;

/** 
 * @description ʹ��Java�Ĺ������ͽ��ж���ĸ���
 * ֻ�ܸ���bean����,���ܸ�������
 * @author ������
 * @date 2018-08-28 
 */
public class CloneTest3 {

    public static void main(String[] args) throws Exception{
        Address addr = new Address();  
        addr.setAdd("������");  
        Studentt stu1 = new Studentt();  
        stu1.setNumber(123);  
        stu1.setAddr(addr);  
        Studentt stu2 = new Studentt(); 
        BeanUtils.copyProperties(stu2,stu1);
        Address addr1 = new Address();  
        addr1.setAdd("������1");
        stu2.setAddr(addr1);
        System.out.println("ѧ��1:" + stu1.getNumber() + ",��ַ:" + stu1.getAddr().getAdd());  
        System.out.println("ѧ��2:" + stu2.getNumber() + ",��ַ:" + stu2.getAddr().getAdd());
    }
}
/*
 * ����д�����۶��������Զ�ֻ��Ҫһ�д���㶨���ܷ���ɣ�
 * ��BeanUtils�⻹��һ����ΪPropertyUtils�Ĺ����࣬
 * ��Ҳ�ṩcopyProperties()������������BeanUtils��ͬ������ʮ�����ƣ�
 * ��Ҫ����������BeanUtils�ṩ����ת�����ܣ�
 * ����������JavaBean��ͬ������Ϊ��ͬ����ʱ����֧�ֵ��������ͷ�Χ�ڽ���ת����
 * ��PropertyUtils��֧��������ܣ������ٶȻ����һЩ����ʵ�ʿ����У�
 * BeanUtilsʹ�ø��ձ�һ�㣬����ķ��ո���һ�㡣
 */
 
