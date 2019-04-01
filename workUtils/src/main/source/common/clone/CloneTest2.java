package main.source.common.clone;  

/** 
 * @description 演示深度克隆  值类型变量和引用类型变量都是创建一个副本
 * 具体的实现就是,引用类型的变量也实现Cloneable接口,并且重写clone方法
 * 让后一个个的复制
 * @author 陈吕奖
 * @date 2018-08-28 
 */  
class Address implements Cloneable {  
    private String add;  

    public String getAdd() {  
        return add;  
    }  

    public void setAdd(String add) {  
        this.add = add;  
    }

    @Override  
    public Object clone() {  
        Address addr = null;  
        try{  
            addr = (Address)super.clone();  
        }catch(CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return addr;  
    }  
}  

class Studentt implements Cloneable{  
    private int number;  

    private Address addr;  

    public Address getAddr() {  
        return addr;  
    }  

    public void setAddr(Address addr) {  
        this.addr = addr;  
    }  

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
            stu = (Studentt)super.clone();   //浅复制  
        }catch(CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        stu.addr = (Address)addr.clone();   //深度复制  
        return stu;  
    }  
}  
public class CloneTest2 {  

    public static void main(String args[]) {  

        Address addr = new Address();  
        addr.setAdd("杭州市");  
        Studentt stu1 = new Studentt();  
        stu1.setNumber(123);  
        stu1.setAddr(addr);  

        Studentt stu2 = (Studentt)stu1.clone();  

        System.out.println("学生1:" + stu1.getNumber() + ",地址:" + stu1.getAddr().getAdd());  
        System.out.println("学生2:" + stu2.getNumber() + ",地址:" + stu2.getAddr().getAdd());  

        addr.setAdd("西湖区");  

        System.out.println("学生1:" + stu1.getNumber() + ",地址:" + stu1.getAddr().getAdd());  
        System.out.println("学生2:" + stu2.getNumber() + ",地址:" + stu2.getAddr().getAdd());  
    }  
}
/*
 * 在深克隆中，无论原型对象的成员变量是值类型还是引用类型，都将复制一份给克隆对象，深克隆将原型对象的所有引用对象也复制一份给克隆对象。
 * 简单来说，在深克隆中，除了对象本身被复制外，对象所包含的所有成员变量也将复制。 
 * 在Java语言中，如果需要实现深克隆，可以通过覆盖Object类的clone()方法实现，也可以通过序列化(Serialization)等方式来实现。
 *（如果引用类型里面还包含很多引用类型，或者内层引用类型的类里面又包含引用类型，使用clone方法就会很麻烦。这时我们可以用序列化的方式来实现对象的深克隆。）
 */

