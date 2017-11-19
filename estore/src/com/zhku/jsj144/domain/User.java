package com.zhku.jsj144.domain;
/*
 create table user(
   id varchar(50) primary key,
   username varchar(50),
   password varchar(50),
   email varchar(50),
   nickname varchar(50),
   role varchar(30)
);
 */
//用户实体类
public class User {
	private String id;//主键
	private String username;//用户名
	private String password;//密码
	private String email;//邮箱
	private String nickname;//昵称
	private String role;//角色（user表示普通用户，admin表示管理员）
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
