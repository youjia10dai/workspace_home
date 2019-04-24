package main.source.common.clone;

/** 
 * @description 演示浅克隆. 只能复制值类型的变量,引用类型变量复制的地址值 
 * 实现方法 对象必须实现Cloneable,并且重写clone方法
 * @author 陈吕奖
 * @date 2018-08-28 
 */  
class Student implements Cloneable{
    private int number;  

    public int getNumber() {  
        return number;  
    }  

    public void setNumber(int number) {  
        this.number = number;  
    }  

    @Override  
    public Object clone() {  
        Studentt stu = null;  
        try{  
            stu = (Studentt)super.clone();  
        }catch(CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return stu;  
    }  
}  
public class CloneTest {  
    public static void main(String args[]) {  
        Studentt stu1 = new Studentt();  
        stu1.setNumber(12345);  
        Studentt stu2 = (Studentt)stu1.clone();  

        System.out.println("学生1:" + stu1.getNumber());  
        System.out.println("学生2:" + stu2.getNumber());  

        stu2.setNumber(54321);  

        System.out.println("学生1:" + stu1.getNumber());  
        System.out.println("学生2:" + stu2.getNumber());  
    }  
}
/*
 * 在浅克隆中，如果原型对象的成员变量是值类型，将复制一份给克隆对象；
 * 如果原型对象的成员变量是引用类型，则将引用对象的地址复制一份给克隆对象，
 * 也就是说原型对象和克隆对象的成员变量指向相同的内存地址。
 * 简单来说，在浅克隆中，当对象被复制时只复制它本身和其中包含的值类型的成员变量，
 * 而引用类型的成员对象并没有复制。
 * 在Java语言中，通过覆盖Object类的clone()方法可以实现浅克隆。
 */