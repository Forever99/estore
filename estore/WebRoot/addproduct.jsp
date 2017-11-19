<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
<body>
	<h3>添加商品</h3>
	<form action="/addproduct" method="post" enctype="multipart/form-data">
		<table border="1">
			<tr>
				<td>商品名</td>
				<td><input type="text" name="name"></td>
			</tr>
			<tr>
				<td>单价</td>
				<td><input type="text" name="price"></td>
			</tr>
			<tr>
				<td>种类</td>
				<td><select name="category">
						<option value="生活电器">生活电器</option>
						<option value="家具建材">家具建材</option>
						<option value="汽车/配件/用品">汽车/配件/用品</option>
						<option value="衣服">衣服</option>
						<option value="美妆/个人护理">美妆/个人护理</option>
						<option value="手机/数码/电脑办公">手机/数码/电脑办公</option>
				</select></td>
			</tr>
			<tr>
				<td>描述</td>
				<td><textarea rows="4" cols="30" name="description"></textarea></td>
			</tr>
			<tr>
				<td>库存</td>
				<td><input type="text" name="count"></td>
			</tr>
			<tr>
				<td>商品图片</td>
				<td><input type="file" name="imageurl"></td>
			</tr>
				<tr>
				<td colspan="2" align="center">
				<input type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="reset" value="重置">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
