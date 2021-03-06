package com.royasoft.sailevent.impl.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
	
	@ManyToMany(cascade=CascadeType.REFRESH, mappedBy="sailClubs",fetch = FetchType.EAGER)//被维护端就要设置这个属性
	private Set<SailUser> sailUsers;
	
	public Set<SailUser> getSailUsers() {
		return sailUsers;
	}

	public void setSailUsers(Set<SailUser> sailUsers) {
		this.sailUsers = sailUsers;
	}

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
