<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <script type="text/javascript">
  	function addtocart(id){
//   		window.location.href="/addtocart?id="+id;
  		location.href="/addtocart?id="+id;
  	}
  
  </script>
  </head>
  <body>
   <h3>商品列表</h3>
   <c:forEach var="product" items="${listproduct }">
		
         商品名:${ product.name } <br/>
         单价:${ product.price}<br/>
         种类:${ product.category}<br/>
         描述:${ product.description}<br/>
        库存:${ product.count}<br/>
<!--         图片地址：${product.imageurl }<br/> -->
<!-- 方法一：直接设置图片的高度，宽度，进行设置图片显示大小 -->
<!--        商品图片:<br/><img src="${product.imageurl }" width="250" height="250"> -->
        商品图片:<br/><img src="${product.imageurl_s }" >
        <img src="/ico/buy.bmp" onclick="addtocart('${product.id}')">
       <hr/>
		</c:forEach>

   
  </body>
</html>
