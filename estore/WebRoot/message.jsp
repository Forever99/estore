<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

  <head>
  <script type="text/javascript">
   setTimeout('window.location="/index.jsp"',5000);  //5秒后调用代码 
  </script>
  </head>
  <body>
 <h3>${msg }</h3>
 <h3>5秒后自动跳转到estore商城主页,如果没跳转，请手动<a href="/index.jsp">点击</a></h3>
  <a href="/listproduct">继续购买商品</a><br/>

  </body>
</html>
