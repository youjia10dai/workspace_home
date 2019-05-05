package com.royasoft.sailevent.impl.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import cn.itcast.domain.Teacher;

/**
 * @author clj
 * @date 2019-05-05
 * 选举的用户
 */
@Entity
@Table(name = "vwt_set_sail_event_user")
public class SailUser implements Serializable{
	private static final long serialVersionUID = 3333203914045554199L;

	/**
	 * 用户ID
	 */
	@Id
    @Column(name = "user_id")
	private String userId;
	
	/**
	 * 单元名称
	 */
	@Column(name = "area_name")
	private String areaName;
	
	/**
	 * 公司名称
	 */
	@Column(name = "company_name")
	private String companyName;
	
	private Set<SailClub> sailClubs=new HashSet<SailClub>();
	
	@ManyToMany(cascade=CascadeType.REFRESH)
	@JoinTable(name="vwt_set_sail_event_user_club",inverseJoinColumns=@JoinColumn(name="club_id")
	,joinColumns=@JoinColumn(name="user_id"))
	//多对多是根据关联表来维护数据的
	public Set<SailClub> getTeachers() {
		return sailClubs;
	}
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}
