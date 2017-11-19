package com.zhku.jsj144.dao;

import java.sql.SQLException;

import com.zhku.jsj144.domain.Orderitem;

public interface OrderitemDao {

	void insertOrderitem(Orderitem orderitem)throws SQLException;//插入订单项

}
