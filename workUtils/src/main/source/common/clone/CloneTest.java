package main.source.common.clone;

/** 
 * @description ��ʾǳ��¡. ֻ�ܸ���ֵ���͵ı���,�������ͱ������Ƶĵ�ֵַ 
 * ʵ�ַ��� �������ʵ��Cloneable,������дclone����
 * @author ������
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

        System.out.println("ѧ��1:" + stu1.getNumber());  
        System.out.println("ѧ��2:" + stu2.getNumber());  

        stu2.setNumber(54321);  

        System.out.println("ѧ��1:" + stu1.getNumber());  
        System.out.println("ѧ��2:" + stu2.getNumber());  
    }  
}
/*
 * ��ǳ��¡�У����ԭ�Ͷ���ĳ�Ա������ֵ���ͣ�������һ�ݸ���¡����
 * ���ԭ�Ͷ���ĳ�Ա�������������ͣ������ö���ĵ�ַ����һ�ݸ���¡����
 * Ҳ����˵ԭ�Ͷ���Ϳ�¡����ĳ�Ա����ָ����ͬ���ڴ��ַ��
 * ����˵����ǳ��¡�У������󱻸���ʱֻ��������������а�����ֵ���͵ĳ�Ա������
 * ���������͵ĳ�Ա����û�и��ơ�
 * ��Java�����У�ͨ������Object���clone()��������ʵ��ǳ��¡��
 */