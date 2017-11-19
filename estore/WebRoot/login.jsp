<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>

<body>
	<h3>登录</h3>
	<font color="red">${msg }</font>
	<form action="/login" method="post">
		<table border="1">
			<tr>
				<td>用户名</td>
				<td><input type="text" name="username"></td>
			</tr>
			<tr>
				<td>密码</td>
				<td><input type="password" name="password"></td>
			</tr>
			<tr>
				<td colspan="2"><input type="checkbox" name="check" value="on">自动登录</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" value="提交">&nbsp;&nbsp;
					<input type="reset" value="重置"></td>

			</tr>
		</table>
	</form>
</body>
</html>
