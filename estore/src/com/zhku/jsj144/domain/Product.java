package com.zhku.jsj144.domain;

import java.io.Serializable;

/*
 create table product(
  id varchar(50) primary key,
  name varchar(50),
  price double,
  category varchar(50),
  description varchar(100),
  count int,
  imageurl varchar(100)
);
 */
//商品实体类
//public class Product {
public class Product implements Serializable{//因为抛出异常，才去实现 Serializable接口
 //异常： Cannot serialize session attribute cart for session 4ACD6D46CC5845CE7552A3DF9E10E6CF 
	
	private String id;//主键
	private String name;//商品名
	private double price;//单价
	private String category;//种类
	private String description;//商品描述
	private int count;//库存
	private String imageurl;//图片存放地址
	//--------------------------------------
	//后期加入字段
	private String imageurl_s;//小图存放地址
	
	public String getImageurl_s() {
		//imageurl   :/images/1/2/xxx.jpg
		//imageurl_s   :/images/1/2/xxx_s.jpg
		int lastindex=imageurl.lastIndexOf(".");
		String first=imageurl.substring(0,lastindex);//  /images/1/2/xxx
		String last=imageurl.substring(lastindex);//  .jpg
		imageurl_s=first+"_s"+last;
		
		return imageurl_s;
	}
	public void setImageurl_s(String imageurl_s) {
		this.imageurl_s = imageurl_s;
	}
	
	//--------------------------------
	//第二次扩展的方法，为了确保元素唯一性，复写hashcode方法和equals方法
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
}
