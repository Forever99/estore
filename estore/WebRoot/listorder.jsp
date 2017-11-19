<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
<body>
	<h3>订单列表</h3>
	<!--   订单号 ，下单时间，总价，下单人（orders表）           商品价格，  商品名，购买数量（orderitem表） -->
	<c:if test="${empty orders }">
          订单列表为空。。。。
    </c:if>
	<c:if test="${not empty orders }">
		<table border="1" width="100%">
			<c:forEach var="order" items="${orders }">
				<tr>
					<td>订单号: ${order.id }</td>
					<td>下单时间: ${order.ordertime }</td>
					<td>总价: ${order.money }</td>
					<td>下单人: ${order.username}</td>
				</tr>
				<c:forEach var="orderitem" items="${order.list }">
					<tr>
						<td></td>
						<td>商品名: ${orderitem.name}</td>
						<td>商品价格: ${orderitem.price}</td>
						<td>购买数量: ${orderitem.buynum}</td>
					</tr>
					<tr>
						<td></td>

						<td><font color="red">下单状态：</font></td>
						<td><font color="red"> 
						<c:if test="${order.paystate ==0 }">
						    未支付
						</c:if> 
						<c:if test="${order.paystate ==1 }">
						    已经支付
						</c:if>

						</font>
						</td>
						<td>
						<c:if test="${order.paystate ==0 }">
						<font color="red">
						 <a href="/pay?id=${order.id }">支付</a>
						 </font>
						</c:if> 
						</td>
					</tr>
				</c:forEach>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>
