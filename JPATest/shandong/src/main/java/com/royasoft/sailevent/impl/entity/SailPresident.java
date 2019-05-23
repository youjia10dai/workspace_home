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
 * @date 2019-05-06
 * 会长选举
 */
@Entity
@Table(name = "vwt_set_sail_event_president")
public class SailPresident implements Serializable {

	private static final long serialVersionUID = 4548052300846585408L;

	/**
	 * 逻辑主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	/**
	 * 选举人ID
	 */
	@Column(name = "voter_user_id")
	private String voteUserId;
	
	/**
	 * 被选举人ID
	 */
	@Column(name = "selected_user_id")
	private String selectedUserId;
	
	/**
	 * 俱乐部ID
	 */
	@Column(name = "club_id")
	private Integer clubId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVoteUserId() {
		return voteUserId;
	}

	public void setVoteUserId(String voteUserId) {
		this.voteUserId = voteUserId;
	}

	public String getSelectedUserId() {
		return selectedUserId;
	}

	public void setSelectedUserId(String selectedUserId) {
		this.selectedUserId = selectedUserId;
	}

	public Integer getClubId() {
		return clubId;
	}

	public void setClubId(Integer clubId) {
		this.clubId = clubId;
	}
}