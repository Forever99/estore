package com.zhku.jsj144.dao;

import com.zhku.jsj144.domain.User;

public interface UserDao {

	User selectByEmail(User user);//通过邮箱确认用户是否注册过

	void insert(User user);//进行注册

	User selectByNameAndPass(String username, String password);//判断是否登录成功

}
