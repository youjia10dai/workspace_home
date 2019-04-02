package com.qfjy.bean;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="usersinfo")
public class UsersInfo {
	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
//	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;
	
	private String name;
	
	private String telphone;

	@Transient
	private Integer uid;
	
	@OneToOne(targetEntity=Users.class,fetch=FetchType.LAZY ) // 不能为空
	@JoinColumn(name = "uid")
	private Users users;

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

}
