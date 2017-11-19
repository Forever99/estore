package com.zhku.jsj144.web.servlet;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhku.jsj144.domain.Product;
import com.zhku.jsj144.service.ProductService;
//修改商品数量
public class UpdateProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");
		ProductService service=new ProductService();
		Product product = service.selectById(id);
		
		
//		System.out.println("id1:"+id);//调试
		String value = request.getParameter("value");
		
//		System.out.println("value："+value);//调试
		
		//修改后---------------------------------------------------------
		Map<Product,Integer> cart=(Map<Product,Integer>)request.getSession().getAttribute("cart");
		
		if(cart!=null){
			cart.put(product, Integer.parseInt(value));//直接设置获得的session即可，然后进行值的覆盖
		}
		
		/*多余代码？？？？
		 * 
		 *修改前------------------------------------------------------
		Set<Entry<Product, Integer>> entrySet = cart.entrySet();
		for (Entry<Product, Integer> entry : entrySet) {
			Product key = entry.getKey();
			System.out.println("id2:"+key.getId());
			//代码判断逻辑有毒？
			//如何判断？？？？？？？？？？？？？？？？
			
			if(key.getId().equals(id)){
				cart.put(key, Integer.parseInt(value));//装入新数据
				System.out.println("0000成功?????");
			}
			if(id.equalsIgnoreCase(key.getId())){
				cart.put(key, Integer.parseInt(value));//装入新数据
				System.out.println("1111成功?????");
			}
			if(id.equals(key.getId())){
				cart.put(key, Integer.parseInt(value));//装入新数据
				System.out.println("2222成功?????");
			}

		}
		request.getSession().removeAttribute("cart");//移除原来的cart购物车
		
		request.getSession().setAttribute("cart", cart);//重新设置购物车
		
		*/
//		response.sendRedirect("/listcart.jsp");
		request.getRequestDispatcher("/listcart.jsp").forward(request, response);//修改后
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);

	}

}
