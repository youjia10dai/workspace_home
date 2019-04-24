package com.test.spring;

public class BeanLifecycle {

	private String name;
	
	private String sex;

	public void init(){
		System.out.println("初始化方法");
	}
	
	public void close(){
		System.out.println("关闭方法");
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	
}
