package cn.itcast.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class AirLine {

	private AirLinePK id;
	private String name;
	
	public AirLine(){
		
	}
	public AirLine(AirLinePK id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	@EmbeddedId//联合主键的注解  有两个地方都要加
	public AirLinePK getId() {
		return id;
	}
	public void setId(AirLinePK id) {
		this.id = id;
	}
	@Column(length=20)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
