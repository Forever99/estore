package com.zhku.jsj144.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhku.jsj144.domain.Orderitem;
import com.zhku.jsj144.domain.Orders;
import com.zhku.jsj144.domain.Product;
import com.zhku.jsj144.domain.User;
import com.zhku.jsj144.service.OrderService;
/*
 * 生成订单
 * 1、生成订单表
 * 2、生成订单项表（订单表和商品表的关系表）
 * 3、修改商品表的商品数量
 * 
 * 难点：
 * 如何管理事务？？？
 */
public class GenerateOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取所需信息
		String money = request.getParameter("money");//订单总额
		String receiveinfo = request.getParameter("receiveinfo");//收货人信息
		System.out.println("receiveinfo："+receiveinfo);//调试    发现表单那里拼写错误
		User loginUser = (User) request.getSession().getAttribute("loginUser");//当前用户
		Map<Product,Integer> cart=(Map<Product,Integer>)request.getSession().getAttribute("cart");//购物车
		//1、生成订单表
		Orders order=new Orders();
		order.setMoney(Double.parseDouble(money));
		order.setReveiveinfo(receiveinfo);
		order.setUser_id(loginUser.getId());

		List<Orderitem> orderitems=new ArrayList<Orderitem>();//订单项列表
		
		Set<Entry<Product, Integer>> entrySet = cart.entrySet();
		for (Entry<Product, Integer> entry : entrySet) {
			Orderitem oi=new Orderitem();//订单项表
			//获得数据
			Product product = entry.getKey();
			Integer buynum = entry.getValue();
			//封装数据
			oi.setProduct_id(product.getId());
			oi.setBuynum(buynum);
			//加入订单项列表中
			orderitems.add(oi);
		}
		order.setList(orderitems);//订单项数据
		request.getSession().removeAttribute("cart");//移除购物车
		
		OrderService orderservice=new OrderService();
		orderservice.insert(order);//生成订单表 
		
		request.setAttribute("msg", "生成订单成功");
		request.getRequestDispatcher("/message2.jsp").forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);

	}

}
