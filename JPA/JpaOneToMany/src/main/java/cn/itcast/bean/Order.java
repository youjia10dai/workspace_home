package cn.itcast.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="orders")//自动生成的表明和mysql的关键字冲突
public class Order {

	/**
	 * 订单对象
	 * 一个订单有多个订单项
	 */
	private String id;//id
	
	/*
	 * 订单的总价
	 */
	private Float amount;

	private List<OrderItem> orders=new ArrayList<OrderItem>();
	
	/*
	 * 使用API才会有用
	 * cascade设置级联的操作
	 * All表示 四个都写
	 * REMOVE  级联删除
	 * REFRESH   级联刷新操作 （只会查询获取操作）重新获取数据  refresh方法
	 * PERSIST  级联持久化（保存）操作（持久保存拥有方实体时，也会持久保存该实体的所有相关数据。）
	 * MERGE   级联更新（合并）操作（将分离的实体重新合并到活动的持久性上下文时，也会合并该实体的所有相关数据。）
	 * 
	 * 如果是一对多那么默认是延迟加载
	 * @OneToMany  Many在后面默认就是延迟加载
	 * 
	 * 关系维护端(谁有额外的ID谁就是关系维护端)
	 * 制定这个就表示是关系被维护,通过order维护,order是OrderItem中的属性
	 * mappedBy="order"
	 */
	@OneToMany(cascade={CascadeType.REMOVE,CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE},
			fetch=FetchType.LAZY,mappedBy="order")//被维护端要指定维护端的维护信息
	public List<OrderItem> getOrders() {
		return orders;
	}
	
	/*
	 * 在订单中添加添加订单项的方法
	 */
	
	public void addOrderItem(OrderItem o){
		/*设置这对象主要用于OrderItem表添加时获取Order的
		 * id(OrderItem为关系维护端,Order无法为他设置外键的值)
		 */
		o.setOrder(this);//只有关系维护端才能更新外键值
		orders.add(o);
	}

	public void setOrders(List<OrderItem> orders) {
		this.orders = orders;
	}

	@Id @Column(length=12)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(nullable=false)
	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}
	

}
