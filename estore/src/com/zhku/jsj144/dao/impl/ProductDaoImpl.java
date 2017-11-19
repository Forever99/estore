package com.zhku.jsj144.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.zhku.jsj144.dao.ProductDao;
import com.zhku.jsj144.domain.Orderitem;
import com.zhku.jsj144.domain.Product;
import com.zhku.jsj144.utils.JDBCUtils;
import com.zhku.jsj144.utils.TransactionUtil;

public class ProductDaoImpl implements ProductDao {

	@Override
	public void insertProduct(Product product) {//添加商品
		try {
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		String sql="insert into product values(?,?,?,?,?,?,?)";
		String id=generateId();//生成id
		Object[] params={id,product.getName(),product.getPrice(),product.getCategory(),
				product.getDescription(),product.getCount(),product.getImageurl()};
		qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private String generateId() {//生成id
		String uuid = UUID.randomUUID().toString();
		int abs = Math.abs(uuid.hashCode());
		String id="product_"+abs;
		return id;
	}

	@Override
	public List<Product> selectAll() {//查找所有商品
		try {
			QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
			String sql="select * from product";
			List<Product> listproduct=qr.query(sql,new BeanListHandler(Product.class));
			return listproduct;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Product selectById(String id) {//通过商品id获取商品信息
		try {
			QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
			String sql="select * from product where id=?";
			Product product=qr.query(sql, new BeanHandler(Product.class),id);
			return product;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateProductCount(Orderitem orderitem) throws SQLException {//修改商品数量

		int buynum=orderitem.getBuynum();//购买数量
		String product_id=orderitem.getProduct_id();//商品id
		QueryRunner qr=new QueryRunner();//手动管理事务
		String sql="select count from product where id=?";
		//商品总量
		int count=(Integer) qr.query(TransactionUtil.getConnection(),sql,new ScalarHandler(),product_id);
		
		int change=count-buynum;//修改后数量
		String sql2="update product set count=? where id=?";
		Object[] param={change,product_id};//参数
		qr.update(TransactionUtil.getConnection(),sql2,param);//修改商品数量	
	}

}
