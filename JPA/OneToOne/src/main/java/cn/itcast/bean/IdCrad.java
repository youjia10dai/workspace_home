package cn.itcast.bean;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
@Entity
public class IdCrad {
	/**
	 * IdCrad 为关系被维护端,Person为关系维护端
	 * 关系维护端就是在自己表中添加关联表的主键信息
	 * 
	 */

	/**
	 * 一个身份证对应一个人
	 */
	private Integer id;

	private String idCradNo;

	private Person person;

	// mappedBy="idCrad"有这个注解表示为关系被维护端
	@OneToOne(cascade = { CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "idCrad")
	public Person getPerson() {
		
		return person;
	}
	public IdCrad(){
		
	}
	public IdCrad(String idCradNo) {
		super();
		this.idCradNo = idCradNo;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Id @GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Column(length=18,nullable=false)
	public String getIdCradNo() {
		return idCradNo;
	}

	public void setIdCradNo(String idCradNo) {
		this.idCradNo = idCradNo;
	}
	
}
