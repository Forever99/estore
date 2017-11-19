package com.zhku.jsj144.dao.impl;

import java.sql.SQLException;
import java.util.UUID;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.zhku.jsj144.dao.UserDao;
import com.zhku.jsj144.domain.User;
import com.zhku.jsj144.utils.JDBCUtils;
import com.zhku.jsj144.utils.MD5Utils;

public class UserDaoImpl implements UserDao{

	@Override
	public User selectByEmail(User user) {//通过邮箱确认用户是否注册过
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		String sql="select * from user where email=?";
		Object[] param={user.getEmail()};
		try {
			User users=qr.query(sql, param, new BeanHandler(User.class));
			return users;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void insert(User user) {//进行注册
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		String sql="insert into user values(?,?,?,?,?,?)";
		String id=generateId();
		String password=MD5Utils.md5(user.getPassword());//加密后的密码
		Object[] param={id,user.getUsername(),password,user.getEmail(),user.getNickname(),"user"};
		try {
			qr.update(sql, param);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	//生成Id
	private String generateId() {
		String id=UUID.randomUUID().toString();//uuid
		int hashcode=Math.abs(id.hashCode());
		id="user_"+hashcode;
		return id;
	}

	@Override
	public User selectByNameAndPass(String username, String password) {//判断是否登录成功
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		String sql="select * from user where username=? and password=?";
		//对密码进行md5加密
		password=MD5Utils.md5(password);//加密后的密码
		Object[] param={username,password};
		try {
			return qr.query(sql, new BeanHandler(User.class),param);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
