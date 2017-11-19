package com.zhku.jsj144.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.zhku.jsj144.dao.OrderitemDao;
import com.zhku.jsj144.domain.Orderitem;
import com.zhku.jsj144.utils.TransactionUtil;

public class OrderitemDaoImpl implements OrderitemDao {

	@Override
	public void insertOrderitem(Orderitem orderitem) throws SQLException {//插入订单项

		QueryRunner qr=new QueryRunner();//手动管理事务
		String sql="insert into orderitem values(?,?,?)";
		Object[] param={orderitem.getOrder_id(),orderitem.getProduct_id(),orderitem.getBuynum()};
//		qr.update(sql,param);//使用错误
		qr.update(TransactionUtil.getConnection(), sql,param);//插入数据
	}

}
