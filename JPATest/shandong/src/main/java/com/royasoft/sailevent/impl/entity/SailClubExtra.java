package com.royasoft.sailevent.impl.entity;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author clj
 * @date 2019-05-05
 * 用户额外输入的俱乐部
 */
@Entity
@Table(name = "vwt_set_sail_event_club_extra")
public class SailClubExtra {
	
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
	 * 俱乐部名称
	 */
	@Column(name = "club_name")
	private String clubName;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getClubName() {
		return clubName;
	}

	public void setClubName(String clubName) {
		this.clubName = clubName;
	}
	
}
