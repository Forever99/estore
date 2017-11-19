<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
<body>
	<h3>注册</h3>
	<font color="red">${msg }</font>
	<form action="/regist" method="post">
		<table border="1">
			<tr>
				<td>用户名</td>
				<td><input type="text" name="username" value="${user.username }"></td>
			</tr>
			<tr>
				<td>密码</td>
				<td><input type="password" name="password" value="${user.password }"></td>
			</tr>
			<tr>
				<td>确认密码</td>
				<td><input type="password" name="repassword" value="${user.password }"></td>
			</tr>
			<tr>
				<td>邮箱</td>
				<td><input type="text" name="email"  value="${user.email }"></td>
			</tr>
			<tr>
				<td>昵称</td>
				<td><input type="text" name="nickname"  value="${user.nickname}"></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
				<input type="submit" value="提交">&nbsp;&nbsp;
				<input type="reset" value="重置">
				</td>
				
			</tr>
		</table>
	</form>
</body>
</html>
