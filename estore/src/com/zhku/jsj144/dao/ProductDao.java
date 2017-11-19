package com.zhku.jsj144.dao;

import java.sql.SQLException;
import java.util.List;

import com.zhku.jsj144.domain.Orderitem;
import com.zhku.jsj144.domain.Product;

public interface ProductDao {

	void insertProduct(Product product);//添加商品

	List<Product> selectAll();//查找所有商品

	Product selectById(String id);//通过商品id获取商品信息

	void updateProductCount(Orderitem orderitem)throws SQLException;//修改商品数量

}
