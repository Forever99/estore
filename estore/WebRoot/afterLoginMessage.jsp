<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
<body>
	<h3>恭喜你登录成功,欢迎用户 :  ${loginUser.username }</h3>
	<h3>
		<a href="/logout">注销</a>
	</h3>
	 <a href="addproduct.jsp">添加商品</a><br/>
  	<a href="/listproduct">显示商品</a><br/>
  	<a href="/listcart.jsp">显示购物车</a><br/>
  	<a href="/listorder">显示订单</a><br/>
  	
</body>
</html>
