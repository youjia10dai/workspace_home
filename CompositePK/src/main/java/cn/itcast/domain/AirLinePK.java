package cn.itcast.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable//必须加的注解
public class AirLinePK implements Serializable{//必须实现接口

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String startCity;
	
	private String endCity;

	@Column(length=3)
	public String getStartCity() {
		return startCity;
	}
	public AirLinePK(){
		
	}
	public AirLinePK(String startCity, String endCity) {
		super();
		this.startCity = startCity;
		this.endCity = endCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}
	@Column(length=3)
	public String getEndCity() {
		return endCity;
	}

	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}

	/*
	 * 这两个方法也必须实现
	 * 主键就是用于比较
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endCity == null) ? 0 : endCity.hashCode());
		result = prime * result + ((startCity == null) ? 0 : startCity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AirLinePK other = (AirLinePK) obj;
		if (endCity == null) {
			if (other.endCity != null)
				return false;
		} else if (!endCity.equals(other.endCity))
			return false;
		if (startCity == null) {
			if (other.startCity != null)
				return false;
		} else if (!startCity.equals(other.startCity))
			return false;
		return true;
	}
	
}
