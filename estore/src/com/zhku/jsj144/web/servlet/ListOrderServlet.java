package com.zhku.jsj144.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhku.jsj144.domain.Orders;
import com.zhku.jsj144.domain.User;
import com.zhku.jsj144.service.OrderService;
import com.zhku.jsj144.service.UserService;

//显示订单（订单查询）
public class ListOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//判断当前用户是普通用户还是管理员
		//普通用户，查看自己的订单  ； 管理员，查看所有的订单
		User user=(User) request.getSession().getAttribute("loginUser");
		String role=user.getRole();//用户角色
		OrderService service=new OrderService();
		
		List<Orders> orders=new ArrayList<Orders>();
		if("user".equals(role)){//普通用户
			orders=service.selectById(user.getId());//所有当前用户的订单
		}
		if("admin".equals(role)){//管理员
			orders=service.selectAll();//查询所有订单
		}
		
		request.setAttribute("orders", orders);
		request.getRequestDispatcher("/listorder.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);

	}

}
