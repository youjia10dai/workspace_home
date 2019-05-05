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
 * @date 2019-05-05
 * 俱乐部
 */
@Entity
@Table(name = "vwt_set_sail_event_club")
public class SailClub implements Serializable{
	
	private static final long serialVersionUID = 9185459844938273153L;
	
	/**
	 * 俱乐部ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "club_id")
	private Integer clubId;
	
	/**
	 * 俱乐部名称
	 */
	@Column(name = "club_name")
	private String clubName;
	
	public Integer getClubId() {
		return clubId;
	}

	public void setClubId(Integer clubId) {
		this.clubId = clubId;
	}

	public String getClubName() {
		return clubName;
	}

	public void setClubName(String clubName) {
		this.clubName = clubName;
	}
}
