<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <script type="text/javascript">
  function change(id,oldvalue,value){
//   var i=document.getElementsByName("count");
//   i.value=value;

   if(isNaN(value)||value==""){
   		alert("对不起,您输入的不是数值,请不要随便填写...");
   		//写会原来的数值
   		 window.location.href="/updateproduct?id="+id+"&value="+oldvalue;
		return;
   }
  window.location.href="/updateproduct?id="+id+"&value="+value;
  }
  
  </script>
  </head>
  <body>
  <h3>购物车显示</h3>
  <c:if test="${empty cart }">
       当前购物车没有物品，请去购物。。。。
  </c:if>
  <c:if test="${not empty cart }">
  <table border="1" width="100%">
<!--   设置交易总金额为total -->
  <c:set var="total"></c:set>
   <c:forEach var="cartitem" items="${cart }">
   <tr>
<!-- <td> -->
<!-- 多选框先暂时不做 -->
<!--     <input type="checkbox" name="check"  value="on"> -->
<!--  </td> -->
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
	          购买数量:&nbsp;
<!-- 	          思考：对于减少到0后，就不能再减少；对于加，加到库存大小，就不能再增加-->
			  <c:if test="${cartitem.value-1 >= 0 }">
			  <img src="/ico/minus.jpg" onclick="change('${cartitem.key.id} ','${cartitem.value}','${cartitem.value-1}')" > 
			  </c:if>
			  
			   <c:if test="${cartitem.value-1< 0}">
<!-- 			    <img src="/ico/minus.jpg">  -->
			   </c:if>
			   
<!-- 			   文本框输入时，做出改变 -->
	          <input type="text" name="count" value="${cartitem.value}" size="5" onblur="change('${cartitem.key.id} ','${cartitem.value}',this.value)">
	          
	          <c:if test="${cartitem.value+1<=cartitem.key.count }">
	          <img src="/ico/add.jpg" onclick="change('${cartitem.key.id} ','${cartitem.value}','${cartitem.value+1}')" > 
	          </c:if>
	           <c:if test="${cartitem.value+1>cartitem.key.count }">
<!-- 	            <img src="/ico/add.jpg" >  -->
	          </c:if>
</td>
<td>
	          购买金额:${cartitem.value*cartitem.key.price}
</td>
<!-- 这里，total的进行计算总的金额 -->
<c:set var="total" value="${total+cartitem.value*cartitem.key.price} "></c:set>
	</c:forEach>
		
  </table>

  <h3 align="right">总计：${total }</h3>
<img src="/ico/jiesuan.jpg" onclick="javascript:window.location.href='/confirmInfo.jsp'" align="right"> 
  </c:if>
  </body>
</html>
