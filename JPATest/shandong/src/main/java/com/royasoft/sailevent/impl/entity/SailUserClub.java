package com.royasoft.sailevent.impl.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vwt_set_sail_event_user_club")
public class SailUserClub implements Serializable{

	private static final long serialVersionUID = -5021463884174258673L;

	@Id
	@Column(name = "club_id")
	private Integer clubId;
	
    @Column(name = "user_id")
	private String userId;

	public Integer getClubId() {
		return clubId;
	}

	public void setClubId(Integer clubId) {
		this.clubId = clubId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
