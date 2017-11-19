package com.zhku.jsj144.service;

import java.util.List;

import com.zhku.jsj144.dao.ProductDao;
import com.zhku.jsj144.dao.factory.DaoFactory;
import com.zhku.jsj144.domain.Product;
//商品业务层
public class ProductService {

	ProductDao pdao=DaoFactory.getFactory().newInstance(ProductDao.class);
	//添加商品
	public void insertProduct(Product product) {
		pdao.insertProduct(product);
	}
	public List<Product> selectAll() {//查找所有商品
		List<Product> listproduct=pdao.selectAll();//查找所有商品
		return listproduct;
	}
	public Product selectById(String id) {//通过商品id获取商品信息
		Product product=pdao.selectById(id);
		return product;
	}

}
