package cn.itcast.domain;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;//指定类型类型
import javax.persistence.Transient;

import cn.itcast.myenum.Gener;
/*
 * 通过注解可以设置表的名称,字段的名称,长度,类型
 * columnDefinition指定存储类型,同时必须指定字段的长度
 * 
 * 字段的注解要放在属性上面,放在字段上面没有效果
 */
/**
 *	1.@Entity实体注解
 *	2.注解的位置在属性get方法上(或字段上面)
 *	3.@Id(表示为id) @GeneratedValue(strategy=GenerationType.AUTO)id的生成方式(自适应的方式)
 *	如果想自己生成id,不写@GeneratedValue
 *	4.实体的默认构造函数一定要有
 *	5.@Column  注解
 *  @Column(length=12,nullable=false)  长度  不能为空
 *  @Column(nullable=false,columnDefinition="CHAR(5)")  不能为空并定义类型
 *  @Column(name="personnane")  设置名字
 *  6.@Table(naem="xxx")  设置表明
 *  7.@Temporal(TemporalType.TIMESTAMP)  直接使用这个来设置时间类型  TIMESTAMP时间戳  DATE只有年月日
 *  8.@Enumerated 枚举的注解   枚举一定不能为空
 *  9.面向对象的方式设置默认值
 *  10.@Lob//特别长的字符串  数据库端生成的字段类型是longText  属性值是String
 *  11.@Lob 也可以表示存放二进制     属性必须是Byte[]   数据库端生成的字段类型是longblob
 *  12. @Transient 不希望进入持久化的字段
 *  13. @Basic(fetch=FetchType.LAZY)   延迟加载的注解
 */ 
@Entity//实体注解
@Table(name="person")
public class Person {
		
		
		/** 
		 * 作用于:属性上方或者 对应的get方法上,用于表示做为主键来进行操作
		 * 注意:养成编码规范,都要将注解声明在属性上,而不是放在get上
		 */
		@Id 
		/**
		 * 用于标注主键的生成策略
		 * 		数据库生成
		 * 		程序生成
		 */
		@GeneratedValue(strategy=GenerationType.AUTO)
		/**
		 * @Basic 表示一个简单的属性到数据库表的字段的映射
		 * 默认注解(默认会为我们每一个属性上都加了一个@Basic)
		 */
	    private int id;  
	    private String name;  
	    
	    private Date birthday;//默认是  DateTime包含时分秒信息1993-02-02
	    
	    //存放长的文本
	    private String info;
	    
	    private Byte[] file;
	    
	    //不希望进入持久化的字段
	    private String imagepath;
	    
	    @Transient
	    public String getImagepath() {
			return imagepath;
		}
		public void setImagepath(String imagepath) {
			this.imagepath = imagepath;
		}
		@Lob @Basic(fetch=FetchType.LAZY)//存放二进制数据      延迟加载和立刻加载
	    public Byte[] getFile() {
			return file;
		}
		public void setFile(Byte[] file) {
			this.file = file;
		}
		@Lob//特别长的字符串
	    public String getInfo() {
			return info;
		}
		public void setInfo(String info) {
			this.info = info;
		}
		
	    @Enumerated(EnumType.STRING)//枚举类型保存的是值还是索引(下标)
	    @Column(nullable=false,columnDefinition="CHAR(5)")
	    private Gener gener=Gener.MAN;//面向对象的方式设置默认值
	    
	    public Gener getGener() {
			return gener;
		}
		public void setGener(Gener gener) {
			this.gener = gener;
		}
		
		@Column(columnDefinition="DATE")//时分秒使用时间戳
		//
		//@Temporal(TemporalType.TIMESTAMP)
		public Date getBirthday() {
			return birthday;
		}
		public void setBirthday(Date birthday) {
			this.birthday = birthday;
		}
		
	    /*
	     * @GeneratedValue 确定主键的生成策略
	     * 默认是使用auto的
	     */
	    public int getId() {  
	        return id;  
	    }  
	    public void setId(int id) {  
	        this.id = id;  
	    }
	    //设置长度,姓名的长度不会太长,不设置默认是  varchar(255)
	    @Column(length=12,nullable=false)
	    public String getName() {
	        return name;  
	    }  
	    public void setName(String name) {  
	        this.name = name;  
	    }  
	    
	    /*
	     * 演示延迟加载的异常
	     */
	    public String myGetName(){
	    	return name.toLowerCase();
	    }
}
