package main.source.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Student {
    //姓名
    private String name;
    //年龄
    private String age;
    //住址
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
     * 主要的API JSONObject  JSONArray
     * 1. JSONObject.fromObject(objectStr);转换成JsonObject(可以表示Json字符串也可以表示Object)
     * JSONObject 可以将json 转换成为 Java对象, 也可以将 Java 转换为 json字符串
     * 1.JSONObject.toBean(jsonObject, Student.class);  Json字符串转换成Java对象
     * 2.fromObject(obj)   toString();  转换成json 
     * JSONArray 可以将json数组转换成Java对象(只获取一个对象),  也可以将 Java 转换为 json 数组字符串
     * 1.Object o = jsonArray.get(0);
     * 1.1 JSONObject jsonObject2 = JSONObject.fromObject(o);
     * 1.2 Student stu2 = (Student) JSONObject.toBean(jsonObject2, Student.class);
     * 2.fromObject(obj)  toString();   转换成json
     * 3.将list转换成Json只能通过JSONArray
     * 4.将json数组转换成list或数组
     */
    
    // Java --> json
    public static void convertObject() {
        Student stu = new Student();
        stu.setName("JSON");
        stu.setAge("23");
        stu.setAddress("北京市西城区");
        //1、使用JSONObject
        JSONObject json = JSONObject.fromObject(stu);
        //2、使用JSONArray
        JSONArray array = JSONArray.fromObject(stu);
        json.element("11", "11");
        String strJson = json.toString();
        String strArray = array.toString();
        System.out.println("strJson:" + strJson);
        System.out.println("strArray:" + strArray);
        /*
         * strJson:{"address":"北京市西城区","name":"JSON","age":"23"}
         * strArray:[{"address":"北京市西城区","name":"JSON","age":"23"}]
         * []是这两种转换的区别
         */
    }

    // Json --> Java
    public static void jsonStrToJava() {
        //定义两种不同格式的字符串
        String objectStr = "{\"name\":\"JSON\",\"age\":\"24\",\"address\":\"北京市西城区\"}";
        String arrayStr = "[{\"name\":\"JSON\",\"age\":\"24\",\"address\":\"北京市西城区\"}]";
        //1、使用JSONObject
        JSONObject jsonObject = JSONObject.fromObject(objectStr);
        Student stu = (Student) JSONObject.toBean(jsonObject, Student.class);
        //2、使用JSONArray
        JSONArray jsonArray = JSONArray.fromObject(arrayStr);
        //获得jsonArray的第一个元素(Array --> 转换成Java对象,需要多一步步骤)
        Object o = jsonArray.get(0);
        JSONObject jsonObject2 = JSONObject.fromObject(o);
        Student stu2 = (Student) JSONObject.toBean(jsonObject2, Student.class);
        System.out.println("stu:" + stu);
        System.out.println("stu2:" + stu2);
        /*
         * stu:Student [name=JSON, age=24, address=北京市西城区]
           stu2:Student [name=JSON, age=24, address=北京市西城区]
             如果是数组还需要多做一步(只转换数组中的一个数据)
         */
    }

    // Java list ---  json
    public static void listToJSON() {
        Student stu = new Student();
        stu.setName("JSON");
        stu.setAge("23");
        stu.setAddress("北京市海淀区");
        Student stu1 = new Student();
        stu1.setName("JSON");
        stu1.setAge("23");
        stu1.setAddress("北京市海淀区");
        List<Student> lists = new ArrayList<Student>();
        lists.add(stu);
        lists.add(stu1);
        //1、使用JSONObject
        //JSONObject listObject=JSONObject.fromObject(lists);
        //2、使用JSONArray
        JSONArray listArray = JSONArray.fromObject(lists);
        //System.out.println("listObject:"+listObject.toString());
        System.out.println("listArray:" + listArray.toString());
        /*
         * listArray:[{"address":"北京市海淀区","age":"23","name":"JSON"},{"address":"北京市海淀区","age":"23","name":"JSON"}]
         * list只能通过JSONArray进行转换
         * 
         */
    }

    //json数组 --> Java List
    public static void jsonToList() {
        String arrayStr = "[{\"name\":\"JSON\",\"age\":\"24\",\"address\":\"北京市西城区\"}]";
        //转化为list
        List<Student> list2 = (List<Student>) JSONArray.toList(JSONArray.fromObject(arrayStr), Student.class);
        for(Student stu : list2) {
            System.out.println(stu);
        }
        //转化为数组
        Student[] ss = (Student[]) JSONArray.toArray(JSONArray.fromObject(arrayStr), Student.class);
        for(Student student : ss) {
            System.out.println(student);
        }
        /*
         * Student [name=JSON, age=24, address=北京市西城区]
         * Student [name=JSON, age=24, address=北京市西城区]
         * 将Json转换为list或数组
         * 
         */
    }

    // Java Map --> json
    public static void mapToJSON() {
        Student stu = new Student();
        stu.setName("JSON");
        stu.setAge("23");
        stu.setAddress("中国上海");
        Map<String, Student> map = new HashMap<String, Student>();
        map.put("first", stu);
        //1、JSONObject
        JSONObject mapObject = JSONObject.fromObject(map);
        System.out.println("mapObject" + mapObject.toString());
        //2、JSONArray
        JSONArray mapArray = JSONArray.fromObject(map);
        System.out.println("mapArray:" + mapArray.toString());
        //Map 转换为 Json对象数组()
    }

    // json --> Java Map(这种格式的字符串除了这种使用Map的方式,还有一种方式)
    public static void jsonToMap() {
        String strObject = "{\"first\":{\"address\":\"中国上海\",\"age\":\"23\",\"name\":\"JSON\"}}";
        //JSONObject
        JSONObject jsonObject = JSONObject.fromObject(strObject);
        Map map = new HashMap();
        map.put("first", Student.class);
        //使用了toBean方法，需要三个参数 
        MyBean my = (MyBean) JSONObject.toBean(jsonObject, MyBean.class, map);
        System.out.println(my.getFirst());
    }

    //格式复杂的Json字符串
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
