<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
    <h3>确认订单信息</h3>
    <form action="/generateOrder" method="post">

  收货人信息：<textarea rows="4" cols="60" name="receiveinfo"></textarea>

  <table border="1" width="100%">

<!--   设置交易总金额为total -->
  <c:set var="total"></c:set>
   <c:forEach var="cartitem" items="${cart }">
   <tr>
 <td>
	       商品图片:<img src="${cartitem.key.imageurl }" width="100" height="100">
</td>
<td>
	         商品名:${ cartitem.key.name} 
</td>
<td>	
	         单价:${ cartitem.key.price} 
</td>	
<td>        
	         种类:${cartitem.key.category} 
</td>
<td>   
	         描述:${cartitem.key.description} 
</td>
<td>
	          购买数量:${cartitem.value}   
</td>
<td>
	          购买金额:${cartitem.value*cartitem.key.price}
</td>
<!-- 这里，total的进行计算总的金额 -->
<c:set var="total" value="${total+cartitem.value*cartitem.key.price} "></c:set>
	</c:forEach>
  </table>
  <input type="hidden" name="money" value="${total }">
  <h3 align="right">总计：${total }</h3>
<!-- <img src="/ico/generateOrder.jpg" onclick="javascript:window.location.href='/generateOrder'" align="right">  -->
<div align="right"><input type="submit" value="提交订单" ></div>
</form>
  </body>
</html>
