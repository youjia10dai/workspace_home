package cn.itcast.bean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "person2")
public class Person {

	/*
	 * 一对多 多的一方文为维护端, 被维护端没有修改外键的权利,所以在(持久化)多的一方需要添加被维护端来获取外键的信息 一对一
	 * 程序员选择维护端,直接持久化维护端,维护端有权限修改外键值(被维护端也不需要维护的数据)
	 */

	/*
	 * 这个Person作为维护端
	 */

	/**
	 * 一个人对应一个身份证
	 */
	private Integer id;

	public Person() {

	}

	public Person(String name) {
		super();
		this.name = name;
	}

	private String name;

	private IdCrad idCrad;

	// 一对一没有延迟加载
	@OneToOne(cascade = CascadeType.ALL, optional = false) // 不能为空
	@JoinColumn(name = "idcrad_id") // 维护端需要添加的东西 就是在维护端添加一个额外的字段
	public IdCrad getIdCrad() {
		return idCrad;
	}

	public void setIdCrad(IdCrad idCrad) {
		this.idCrad = idCrad;
	}

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(length = 12, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
