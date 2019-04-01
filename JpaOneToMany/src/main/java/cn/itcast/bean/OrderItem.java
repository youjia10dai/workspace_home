package cn.itcast.bean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrderItem {
	/**
	 * 订单项对象
	 * 一个订单项对应一个订单
	 */
	
	/*
	 * id
	 */
	private Integer id;
	
	/*
	 * 商品的名字
	 */
	private String name;
	
	/*
	 * 商品的单价
	 */
	private Float cellPrice;

	private Order order;//被order维护着
	
	/*
	 * 只有级联的刷新和级联的更新
	 * 商品的价格变了订单的总价也要变
	 * @ManyToOne 这个是立刻加载
	 * optional 选项  true  表示关联字段可以为空  false  表示不能为空
	 */
	@ManyToOne(cascade={CascadeType.REFRESH,CascadeType.MERGE},optional=false,fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")//关系维护端,制定一个字段(数据库中的字段名,自动和被维护端的主键连接查询)
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	@Id @GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(length=40,nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(nullable=false)
	public Float getCellPrice() {
		return cellPrice;
	}

	public void setCellPrice(Float cellPrice) {
		this.cellPrice = cellPrice;
	}
}
