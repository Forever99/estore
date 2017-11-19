package com.zhku.jsj144.web.servlet;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhku.jsj144.domain.Product;
import com.zhku.jsj144.service.ProductService;
//添加商品到购物车
/*
 * 思路：
 * 主要围绕名为cart的session进行展开
 * 
 * 1.购物车保存到session中(cart)
 * 2.session中存储map<Product,Integer>  -->商品  和数量
 *   其中，使用linkedHashMap进行存储,为了确保有序输出
 *   通过id确保元素唯一，进行复写product中的hashcode方法和equals方法
 *   
 * 3.如果session中该product为空，则为1；否则进行加1操作 
 */
public class AddToCartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String id = request.getParameter("id");
		ProductService service=new ProductService();
		Product product=service.selectById(id);//通过商品id获取商品信息
		
		//获取存入session的购物车cart
		Map<Product,Integer> cart= (Map<Product, Integer>) request.getSession().getAttribute("cart");
		if(cart==null){
			cart=new LinkedHashMap<Product,Integer>();//创建一个购物车
		}
		
		//遍历购物车
		if(cart.containsKey(product)){//购物车中已经有该数据
			Integer value = cart.get(product);
			value=value+1;
			cart.put(product, value);//已经买过
		}
		else{
			cart.put(product, 1);//还没买过
		}
		
		/*-------
		 * 错误代码
		 * -------
		Set<Entry<Product, Integer>> entrySet = cart.entrySet();
		for (Entry<Product, Integer> entry : entrySet) {
			Product key = entry.getKey();//键
			Integer value = entry.getValue();//值
			//通过id判断是否存在有当前购物车
			if(id.equals(key.getId())){//在购物车已经存在
				value=value+1;
				cart.put(key, value);//覆盖值
			}
		}
		//经过遍历后，发现没有找到，则把它存入cart中，并且设置数量为1
		cart.put(product, 1);//该商品为product，数量为1
		
		*/
		
		request.getSession().setAttribute("cart", cart);//购物车信息保存到session中
		request.setAttribute("msg", "添加购物车成功");
//		request.getRequestDispatcher("/message.jsp").forward(request, response);
		request.getRequestDispatcher("/message2.jsp").forward(request, response);
		
		
	}



	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);

	}

}
