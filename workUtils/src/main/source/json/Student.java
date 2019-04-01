package main.source.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Student {
    //����
    private String name;
    //����
    private String age;
    //סַ
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Student [name=" + name + ", age=" + age + ", address=" + address + "]";
    }

    /*
     * ��Ҫ��API JSONObject  JSONArray
     * 1. JSONObject.fromObject(objectStr);ת����JsonObject(���Ա�ʾJson�ַ���Ҳ���Ա�ʾObject)
     * JSONObject ���Խ�json ת����Ϊ Java����, Ҳ���Խ� Java ת��Ϊ json�ַ���
     * 1.JSONObject.toBean(jsonObject, Student.class);  Json�ַ���ת����Java����
     * 2.fromObject(obj)   toString();  ת����json 
     * JSONArray ���Խ�json����ת����Java����(ֻ��ȡһ������),  Ҳ���Խ� Java ת��Ϊ json �����ַ���
     * 1.Object o = jsonArray.get(0);
     * 1.1 JSONObject jsonObject2 = JSONObject.fromObject(o);
     * 1.2 Student stu2 = (Student) JSONObject.toBean(jsonObject2, Student.class);
     * 2.fromObject(obj)  toString();   ת����json
     * 3.��listת����Jsonֻ��ͨ��JSONArray
     * 4.��json����ת����list������
     */
    
    // Java --> json
    public static void convertObject() {
        Student stu = new Student();
        stu.setName("JSON");
        stu.setAge("23");
        stu.setAddress("������������");
        //1��ʹ��JSONObject
        JSONObject json = JSONObject.fromObject(stu);
        //2��ʹ��JSONArray
        JSONArray array = JSONArray.fromObject(stu);
        json.element("11", "11");
        String strJson = json.toString();
        String strArray = array.toString();
        System.out.println("strJson:" + strJson);
        System.out.println("strArray:" + strArray);
        /*
         * strJson:{"address":"������������","name":"JSON","age":"23"}
         * strArray:[{"address":"������������","name":"JSON","age":"23"}]
         * []��������ת��������
         */
    }

    // Json --> Java
    public static void jsonStrToJava() {
        //�������ֲ�ͬ��ʽ���ַ���
        String objectStr = "{\"name\":\"JSON\",\"age\":\"24\",\"address\":\"������������\"}";
        String arrayStr = "[{\"name\":\"JSON\",\"age\":\"24\",\"address\":\"������������\"}]";
        //1��ʹ��JSONObject
        JSONObject jsonObject = JSONObject.fromObject(objectStr);
        Student stu = (Student) JSONObject.toBean(jsonObject, Student.class);
        //2��ʹ��JSONArray
        JSONArray jsonArray = JSONArray.fromObject(arrayStr);
        //���jsonArray�ĵ�һ��Ԫ��(Array --> ת����Java����,��Ҫ��һ������)
        Object o = jsonArray.get(0);
        JSONObject jsonObject2 = JSONObject.fromObject(o);
        Student stu2 = (Student) JSONObject.toBean(jsonObject2, Student.class);
        System.out.println("stu:" + stu);
        System.out.println("stu2:" + stu2);
        /*
         * stu:Student [name=JSON, age=24, address=������������]
           stu2:Student [name=JSON, age=24, address=������������]
             ��������黹��Ҫ����һ��(ֻת�������е�һ������)
         */
    }

    // Java list ---  json
    public static void listToJSON() {
        Student stu = new Student();
        stu.setName("JSON");
        stu.setAge("23");
        stu.setAddress("�����к�����");
        Student stu1 = new Student();
        stu1.setName("JSON");
        stu1.setAge("23");
        stu1.setAddress("�����к�����");
        List<Student> lists = new ArrayList<Student>();
        lists.add(stu);
        lists.add(stu1);
        //1��ʹ��JSONObject
        //JSONObject listObject=JSONObject.fromObject(lists);
        //2��ʹ��JSONArray
        JSONArray listArray = JSONArray.fromObject(lists);
        //System.out.println("listObject:"+listObject.toString());
        System.out.println("listArray:" + listArray.toString());
        /*
         * listArray:[{"address":"�����к�����","age":"23","name":"JSON"},{"address":"�����к�����","age":"23","name":"JSON"}]
         * listֻ��ͨ��JSONArray����ת��
         * 
         */
    }

    //json���� --> Java List
    public static void jsonToList() {
        String arrayStr = "[{\"name\":\"JSON\",\"age\":\"24\",\"address\":\"������������\"}]";
        //ת��Ϊlist
        List<Student> list2 = (List<Student>) JSONArray.toList(JSONArray.fromObject(arrayStr), Student.class);
        for(Student stu : list2) {
            System.out.println(stu);
        }
        //ת��Ϊ����
        Student[] ss = (Student[]) JSONArray.toArray(JSONArray.fromObject(arrayStr), Student.class);
        for(Student student : ss) {
            System.out.println(student);
        }
        /*
         * Student [name=JSON, age=24, address=������������]
         * Student [name=JSON, age=24, address=������������]
         * ��Jsonת��Ϊlist������
         * 
         */
    }

    // Java Map --> json
    public static void mapToJSON() {
        Student stu = new Student();
        stu.setName("JSON");
        stu.setAge("23");
        stu.setAddress("�й��Ϻ�");
        Map<String, Student> map = new HashMap<String, Student>();
        map.put("first", stu);
        //1��JSONObject
        JSONObject mapObject = JSONObject.fromObject(map);
        System.out.println("mapObject" + mapObject.toString());
        //2��JSONArray
        JSONArray mapArray = JSONArray.fromObject(map);
        System.out.println("mapArray:" + mapArray.toString());
        //Map ת��Ϊ Json��������()
    }

    // json --> Java Map(���ָ�ʽ���ַ�����������ʹ��Map�ķ�ʽ,����һ�ַ�ʽ)
    public static void jsonToMap() {
        String strObject = "{\"first\":{\"address\":\"�й��Ϻ�\",\"age\":\"23\",\"name\":\"JSON\"}}";
        //JSONObject
        JSONObject jsonObject = JSONObject.fromObject(strObject);
        Map map = new HashMap();
        map.put("first", Student.class);
        //ʹ����toBean��������Ҫ�������� 
        MyBean my = (MyBean) JSONObject.toBean(jsonObject, MyBean.class, map);
        System.out.println(my.getFirst());
    }

    //��ʽ���ӵ�Json�ַ���
    public static void complex(){
        String str="{\"data\":{\"my\":\"this is my first\",\"second\":\"this is second!\"}}";
        JSONObject jsonObject=JSONObject.fromObject(str);
        JSONObject dataObject=jsonObject.getJSONObject("data");
        System.out.println("dd:"+dataObject.toString());
    }
    
    public static void main(String[] args) {
        complex();
    }
}
