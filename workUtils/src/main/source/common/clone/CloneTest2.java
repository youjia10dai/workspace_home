package main.source.common.clone;  

/** 
 * @description ��ʾ��ȿ�¡  ֵ���ͱ������������ͱ������Ǵ���һ������
 * �����ʵ�־���,�������͵ı���Ҳʵ��Cloneable�ӿ�,������дclone����
 * �ú�һ�����ĸ���
 * @author ������
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
            stu = (Studentt)super.clone();   //ǳ����  
        }catch(CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        stu.addr = (Address)addr.clone();   //��ȸ���  
        return stu;  
    }  
}  
public class CloneTest2 {  

    public static void main(String args[]) {  

        Address addr = new Address();  
        addr.setAdd("������");  
        Studentt stu1 = new Studentt();  
        stu1.setNumber(123);  
        stu1.setAddr(addr);  

        Studentt stu2 = (Studentt)stu1.clone();  

        System.out.println("ѧ��1:" + stu1.getNumber() + ",��ַ:" + stu1.getAddr().getAdd());  
        System.out.println("ѧ��2:" + stu2.getNumber() + ",��ַ:" + stu2.getAddr().getAdd());  

        addr.setAdd("������");  

        System.out.println("ѧ��1:" + stu1.getNumber() + ",��ַ:" + stu1.getAddr().getAdd());  
        System.out.println("ѧ��2:" + stu2.getNumber() + ",��ַ:" + stu2.getAddr().getAdd());  
    }  
}
/*
 * �����¡�У�����ԭ�Ͷ���ĳ�Ա������ֵ���ͻ����������ͣ���������һ�ݸ���¡�������¡��ԭ�Ͷ�����������ö���Ҳ����һ�ݸ���¡����
 * ����˵�������¡�У����˶����������⣬���������������г�Ա����Ҳ�����ơ� 
 * ��Java�����У������Ҫʵ�����¡������ͨ������Object���clone()����ʵ�֣�Ҳ����ͨ�����л�(Serialization)�ȷ�ʽ��ʵ�֡�
 *����������������滹�����ܶ��������ͣ������ڲ��������͵��������ְ����������ͣ�ʹ��clone�����ͻ���鷳����ʱ���ǿ��������л��ķ�ʽ��ʵ�ֶ�������¡����
 */

