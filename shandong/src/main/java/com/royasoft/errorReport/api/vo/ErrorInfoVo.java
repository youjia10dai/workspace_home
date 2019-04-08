package com.royasoft.errorReport.api.vo;

import java.io.Serializable;
import java.util.Date;

public class ErrorInfoVo implements Serializable{

	private static final long serialVersionUID = -7355560352343518990L;

	/** 上报id */
    private Integer id;
	
	/** 上报日期 */
    private Date createdate;
	
    /** 用户名 */
    private String username;
    
    /** 电话 */
    private String telephone;
    
    /** 设备 */
    private String equipment;
    
	/** 操作系统 */
    private String system;
    
    public ErrorInfoVo() {
	}

	public ErrorInfoVo(Date createdate, String username, String telephone, String equipment, String system,
			String version, String type, String state, String remark, String url) {
		super();
		this.createdate = createdate;
		this.username = username;
		this.telephone = telephone;
		this.equipment = equipment;
		this.system = system;
		this.version = version;
		this.type = type;
		this.state = state;
		this.remark = remark;
		this.url = url;
	}

	/** 系统版本 */
    private String version;
    
    /** 异常类型 */
    private String type; 
    
    /** 状态 */
    private String state;
    
    /** 描述 */
    private String remark;
    
    /** 日志地址 */
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
