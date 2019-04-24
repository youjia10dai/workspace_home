package cn.itcast.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
@Entity
public class Teacher {

	/**
	 * 被维护端
	 */
	private int id;
	
	private String name;
	
	@ManyToMany(cascade=CascadeType.REFRESH,mappedBy="teachers")//被维护端就要设置这个属性
	public Set<Student> getStudents() {
		return students;
	}
	public Teacher(){}
	public Teacher(String name) {
		super();
		this.name = name;
	}

	/*
	 * 根据id来判断是不是同一个老师
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Teacher other = (Teacher) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	private Set<Student> students=new HashSet<Student>();
	
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
	
}
