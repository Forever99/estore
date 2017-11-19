package com.zhku.jsj144.domain;

import java.util.Date;
import java.util.List;

/*
 create table orders(
  id varchar(50) primary key,
  money double,
  ordertime datetime,
  receiveinfo varchar(100),
  paystate int,
  user_id varchar(50),
  foreign key(user_id) references user(id)
);
 */
//订单实体类
public class Orders {
	private String id;//主键
	private double money;//订单金额
	private Date ordertime;//订单时间
	private String reveiveinfo;//收货地址
	private int paystate;//支付状态（0表示未支付，1表示支付成功）
	private String user_id;//外键（用户编号）
	
	//后期修改--------------------------------
	private List<Orderitem> list;//订单项的列表，就是当前订单下的包含有什么订单情况
	
	//后期修改2------------------------------
	//为了多表查询的方便，而引入的
	private String username;//下单人名字
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<Orderitem> getList() {
		return list;
	}
	public void setList(List<Orderitem> list) {
		this.list = list;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public String getReveiveinfo() {
		return reveiveinfo;
	}
	public void setReveiveinfo(String reveiveinfo) {
		this.reveiveinfo = reveiveinfo;
	}
	public int getPaystate() {
		return paystate;
	}
	public void setPaystate(int paystate) {
		this.paystate = paystate;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
}
