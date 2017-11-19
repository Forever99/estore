package com.zhku.jsj144.dao;

import java.sql.SQLException;
import java.util.List;

import com.zhku.jsj144.domain.Orders;

public interface OrderDao {

	void insertOrder(Orders order)throws SQLException;//生成订单

	List<Orders> selectById(String id);//所有当前用户的订单

	List<Orders> selectAll();//查询所有订单

	void updateState(String id);//修改订单状态

}
