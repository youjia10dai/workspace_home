package com.royasoft.sailevent.impl.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author clj
 * @date 2019-05-06 会长选举中的成员
 */
@Entity
@Table(name = "vwt_set_sail_event_president_member")
public class SailPresidentMember implements Serializable{

	private static final long serialVersionUID = -6620035220641300756L;

	/**
	 * 逻辑主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 用户ID
	 */
	@Column(name = "user_id")
	private String userId;

	/**
	 * 俱乐部ID
	 */
	@Column(name = "club_id")
	private Integer clubId;

	@Column(name = "name1")
	private String name1;

	@Column(name = "tel1")
	private String tel1;

	@Column(name = "name2")
	private String name2;

	@Column(name = "tel2")
	private String tel2;

	@Column(name = "name3")
	private String name3;

	@Column(name = "tel3")
	private String tel3;

	@Column(name = "name4")
	private String name4;

	@Column(name = "tel4")
	private String tel4;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getClubId() {
		return clubId;
	}

	public void setClubId(Integer clubId) {
		this.clubId = clubId;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getTel1() {
		return tel1;
	}

	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getTel2() {
		return tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	public String getName3() {
		return name3;
	}

	public void setName3(String name3) {
		this.name3 = name3;
	}

	public String getTel3() {
		return tel3;
	}

	public void setTel3(String tel3) {
		this.tel3 = tel3;
	}

	public String getName4() {
		return name4;
	}

	public void setName4(String name4) {
		this.name4 = name4;
	}

	public String getTel4() {
		return tel4;
	}

	public void setTel4(String tel4) {
		this.tel4 = tel4;
	}

}
