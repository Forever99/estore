package com.zhku.jsj144.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhku.jsj144.service.OrderService;
/*
 * 支付订单
 * 修改订单状态
 */
public class PayServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");//订单id
		OrderService service=new OrderService();
		service.updateState(id);//修改订单状态
		
		request.setAttribute("msg", "支付订单成功");
		request.getRequestDispatcher("/message2.jsp").forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);

	}

}
