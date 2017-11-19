package com.zhku.jsj144.service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import com.zhku.jsj144.dao.OrderDao;
import com.zhku.jsj144.dao.OrderitemDao;
import com.zhku.jsj144.dao.ProductDao;
import com.zhku.jsj144.dao.factory.DaoFactory;
import com.zhku.jsj144.domain.Orderitem;
import com.zhku.jsj144.domain.Orders;
import com.zhku.jsj144.utils.TransactionUtil;

public class OrderService {
	
	OrderDao orderDao=DaoFactory.getFactory().newInstance(OrderDao.class);
	OrderitemDao orderitemDao=DaoFactory.getFactory().newInstance(OrderitemDao.class);
	ProductDao productDao=DaoFactory.getFactory().newInstance(ProductDao.class);
	//重点：关联订单表，订单项表，商品表
	public void insert(Orders order) {//生成订单
		String orderid=generateId();
		order.setId(orderid);
		
		try {
			//ThreadLocal的使用，进行事务管理
			TransactionUtil.startTransaction();//开启事务
			
			orderDao.insertOrder(order);//生成订单
			
			
			List<Orderitem> orderitemlist = order.getList();
			for (Orderitem orderitem : orderitemlist) {
				orderitem.setOrder_id(orderid);
				orderitemDao.insertOrderitem(orderitem);//插入订单项
				
				productDao.updateProductCount(orderitem);//修改商品数量
			}
			
			TransactionUtil.commit();//提交事务
			
		} catch (SQLException e) {
			TransactionUtil.rollback();//回滚事务
			throw new RuntimeException("生成订单失败....."+e);
		}finally{
			TransactionUtil.relase();//关闭资源
		}
		
	}

	private String generateId() {//生成id
		String uuid=UUID.randomUUID().toString();
		int hashcode=Math.abs(uuid.hashCode());
		String id="order_"+hashcode;
		return id;
	}

	public List<Orders> selectById(String id) {//所有当前用户的订单
		List<Orders> orders=orderDao.selectById(id);
		return orders;
	}

	public List<Orders> selectAll() {//查询所有订单
		List<Orders> orders=orderDao.selectAll();
		return orders;
	}

	public void updateState(String id) {//修改订单状态
		orderDao.updateState(id);
	}

}
