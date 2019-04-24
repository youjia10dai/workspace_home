package com.royasoft.errorReport.impl.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 异常上报实体类,对应vwt_error_info表
 * 
 * @author chenlj
 * 
 * @Date 2019-04-08
 */
@Entity
@Table(name = "vwt_error_info")
public class ErrorInfo implements Serializable {

	private static final long serialVersionUID = -7783818490561449745L;

    /** 上报id */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
	
	/** 上报日期 */
    @Column(name = "create_time")
    private Date createdate;
	
    /** 用户名 */
    @Column(name = "username")
    private String username;
    
    /** 电话 */
    @Column(name = "telephone")
    private String telephone;
    
    /** 设备 */
    @Column(name = "equipment")
    private String equipment;
    
    /** 操作系统 */
    @Column(name = "system")
    private String system;
    
    /** 系统版本 */
    @Column(name = "version")
    private String version;
    
    /** 异常类型 */
    @Column(name = "type")
    private String type; 
    
    /** 状态 */
    @Column(name = "state")
    private String state;
    
    /** 描述 */
    @Column(name = "remark")
    private String remark;
    
    /** 日志地址 */
    @Column(name = "url")
    private String url;
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
    
}