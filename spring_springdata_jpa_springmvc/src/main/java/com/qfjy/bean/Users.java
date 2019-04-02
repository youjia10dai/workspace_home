package com.qfjy.bean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String uname;
	private String upass;
	private String name;
	private String remark;
	private Integer age;
	private Date createDate;
	private String usersInfoTelphone;
	@OneToOne(mappedBy = "users", targetEntity =UsersInfo.class, fetch=FetchType.LAZY)
	private UsersInfo usersInfo;
}
