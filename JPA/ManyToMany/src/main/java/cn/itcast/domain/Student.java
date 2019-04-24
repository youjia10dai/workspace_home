package cn.itcast.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
@Entity
public class Student {

	/*
	 * 将学生作为维护端
	 * 维护端有权限去设置外键的值
	 */
	
	
	
	private int id;
	public Student(){}
	public Student(String name) {
		super();
		this.name = name;
	}
	/*
	 * 多对多没有级联的更新,删除,修改
	 */
	//inverseJoinColumns表示的是被维护端的ID
	@ManyToMany(cascade=CascadeType.REFRESH)
	@JoinTable(name="student_teacher",inverseJoinColumns=@JoinColumn(name="teacher_id")
	,joinColumns=@JoinColumn(name="student_id"))
	//多对多是根据关联表来维护数据的
	public Set<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}

	private String name;
	
	private Set<Teacher> teachers=new HashSet<Teacher>();
	
	@Id @GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@Column(length=12,nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/*
	 * 添加老师的方法
	 */
	public void addTeacher(Teacher e){
		teachers.add(e);
	}
	/**
	 * 删除老师的方法
	 */
	public void removeTeacher(Teacher e){
		teachers.remove(e);
	}
}
