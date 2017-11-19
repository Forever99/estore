package com.zhku.jsj144.domain;
/*
 create table orderitem(
   order_id varchar(50),
   product_id varchar(50),
   buynum int,
   foreign key(order_id) references orders(id),
   foreign key(product_id) references product(id)
);
 */
//订单项实体类（商品和订单多对多的关系，产生了这个类）
public class Orderitem {
	private String order_id;//外键（订单编号）
	private String product_id;//外键（商品编号）
	private int buynum;//商品购买数量
	
	//后期修改------------------------------
	//为了多表查询时的方便，而引入的字段
	private int price;//商品价格
	private String name;//商品名
	
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public int getBuynum() {
		return buynum;
	}
	public void setBuynum(int buynum) {
		this.buynum = buynum;
	}
}
