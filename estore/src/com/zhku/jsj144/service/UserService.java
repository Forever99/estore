package com.zhku.jsj144.service;

import com.zhku.jsj144.dao.UserDao;
import com.zhku.jsj144.dao.factory.DaoFactory;
import com.zhku.jsj144.domain.User;

//用户业务层
public class UserService {

	public UserDao userdao=DaoFactory.getFactory().newInstance(UserDao.class);
	
	//通过邮箱确认用户是否注册过
	public User selectByEmail(User user) {
		User users=userdao.selectByEmail(user);
		return users;
	}

	//进行注册
	public void insert(User user) {
		userdao.insert(user);
	}

	//判断是否登录成功
	public User selectByNameAndPass(String username, String password) {
		return userdao.selectByNameAndPass(username,password);
	}

}
