/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.source.common.clone;

import org.apache.commons.beanutils.BeanUtils;

/** 
 * @description 使用Java的工具类型进行对象的复制
 * 只能复制bean对象,不能复制数组
 * @author 陈吕奖
 * @date 2018-08-28 
 */
public class CloneTest3 {

    public static void main(String[] args) throws Exception{
        Address addr = new Address();  
        addr.setAdd("杭州市");  
        Studentt stu1 = new Studentt();  
        stu1.setNumber(123);  
        stu1.setAddr(addr);  
        Studentt stu2 = new Studentt(); 
        BeanUtils.copyProperties(stu2,stu1);
        Address addr1 = new Address();  
        addr1.setAdd("杭州市1");
        stu2.setAddr(addr1);
        System.out.println("学生1:" + stu1.getNumber() + ",地址:" + stu1.getAddr().getAdd());  
        System.out.println("学生2:" + stu2.getNumber() + ",地址:" + stu2.getAddr().getAdd());
    }
}
/*
 * 这种写法无论多少种属性都只需要一行代码搞定，很方便吧！
 * 除BeanUtils外还有一个名为PropertyUtils的工具类，
 * 它也提供copyProperties()方法，作用与BeanUtils的同名方法十分相似，
 * 主要的区别在于BeanUtils提供类型转换功能，
 * 即发现两个JavaBean的同名属性为不同类型时，在支持的数据类型范围内进行转换，
 * 而PropertyUtils不支持这个功能，但是速度会更快一些。在实际开发中，
 * BeanUtils使用更普遍一点，犯错的风险更低一点。
 */
 
