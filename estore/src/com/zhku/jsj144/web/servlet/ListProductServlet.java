package com.zhku.jsj144.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhku.jsj144.domain.Product;
import com.zhku.jsj144.service.ProductService;
//显示商品
public class ListProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ProductService service=new ProductService();
		List<Product> listproduct=service.selectAll();//查找所有商品
		
		request.setAttribute("listproduct", listproduct);
		request.getRequestDispatcher("/listproduct.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);

	}

}
