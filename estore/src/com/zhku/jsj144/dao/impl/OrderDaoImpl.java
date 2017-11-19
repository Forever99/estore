package com.zhku.jsj144.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.itcast.commons.CommonUtils;

import com.zhku.jsj144.dao.OrderDao;
import com.zhku.jsj144.domain.Orderitem;
import com.zhku.jsj144.domain.Orders;
import com.zhku.jsj144.domain.User;
import com.zhku.jsj144.utils.JDBCUtils;
import com.zhku.jsj144.utils.TransactionUtil;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void insertOrder(Orders order) throws SQLException {//生成订单

		QueryRunner qr=new QueryRunner();//手动管理事务
		String sql="insert into orders values(?,?,?,?,?,?)";
		String time=new Date().toLocaleString();//生成时间
//		System.out.println("info:"+order.getReveiveinfo());//调试  
		Object[] param={order.getId(),order.getMoney(),time,order.getReveiveinfo(),0,order.getUser_id()};
		
		//qr.update(sql,param);//使用错误
		//异常：QueryRunner requires a DataSource to be invoked in this way, 
		//or a Connection should be passed in
		qr.update(TransactionUtil.getConnection(), sql,param);
	}

	@Override
	public List<Orders> selectById(String id) {//所有当前用户的订单【难！！！！！！！！！！】
		try {
			QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
			/*订单查询的所有信息：【查询四个表】
			 * 订单号 ，下单时间，总价（orders表）     商品价格，  商品名（product表），购买数量（orderitem表），下单人（user表） 
			 *  
			 * 为了查询便利，
			 * 在Orders类中添加 了 字段：       private String username;//下单人名字
			 * 在Orderitem类中添加了字段：private int price;//商品价格
			 *						private String name;//商品名
			 * 添加字段后，查询的表变为：【查询两个表】
			 * 订单号 ，下单时间，总价，下单人（orders表）           商品价格，  商品名，购买数量（orderitem表）
			 * 
			 */
			List<Orders> orderresultList=new ArrayList<Orders>();//最终结果
			
			//用户下的订单
//			String sql="select * from orders where user_id=?";//用户订单  错误
			String sql="select orders.*,user.username from orders,user where orders.user_id=user.id and user_id=?";//用户订单
			List<Orders> orderList = qr.query(sql, new BeanListHandler<Orders>(Orders.class),id);//所有订单
			for (Orders orders : orderList) {
//				//某个订单编号下的所有订单项
				//错误
//				String sql2="select * from orders,orderitem where orders.id=orderitem.order_id and orders.id=?";
				
				//要什么数据，查什么数据，然后进行封装
				String sql2 = "select orderitem.*,product.name,product.price from orderitem,product "
						+ " where orderitem.product_id=product.id and orderitem.order_id=?";
				List<Orderitem> orderitemList = qr.query(sql2, new BeanListHandler<Orderitem>(Orderitem.class),orders.getId());
				orders.setList(orderitemList);//订单下 设置  订单项列表
				orderresultList.add(orders);//订单列表中  添加订单
			}
			return orderresultList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Orders> selectAll() {//查询所有订单
		try {
			QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
			List<Orders> orderresultList=new ArrayList<Orders>();//最终结果
			
			//所有用户下的订单
//			String sql="select * from orders";//用户和订单
			String sql="select orders.*,user.username from orders,user where orders.user_id=user.id";//用户订单
			List<Orders> orderList = qr.query(sql, new BeanListHandler<Orders>(Orders.class));//所有订单
			for (Orders orders : orderList) {
				//某个订单编号下的所有订单项
				//错误！！！
//				String sql2="select * from orders,orderitem where orders.id=orderitem.order_id and orders.id=?";
				//要什么数据，查什么数据，然后进行封装
				String sql2 = "select orderitem.*,product.name,product.price from orderitem,product "
						+ "where orderitem.product_id=product.id and orderitem.order_id=?";
				List<Orderitem> orderitemList = qr.query(sql2, new BeanListHandler<Orderitem>(Orderitem.class),orders.getId());
				orders.setList(orderitemList);//订单下 设置  订单项列表
				orderresultList.add(orders);//订单列表中  添加订单
			}
			return orderresultList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/*多表查询的一些想法
	//用户下的订单
	String sql="select * from user,orders where user.id=orders.user_id and user.id=?";//用户和订单
	//某个订单编号下的所有订单项
	String sql2="select * from orders,orderitem where orders.id=orderitem.order_id and orders.id=?";
	//订单下的，某个订单项下的商品名
	String sql3="select * from orderitem,product where orderitem.product_id=product.id and orderitem.orderitem.order_id=?";
	return null;
	 */

	@Override
	public void updateState(String id) {//修改订单状态

		try {
			QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
			String sql="update orders set paystate=? where id=?";
			Object[] param={1,id};
			qr.update(sql, param);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

}
