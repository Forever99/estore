package com.zhku.jsj144.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//注销
public class LogoutServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		//注销
		session.removeAttribute("loginUser");//移除
//		session.invalidate();//移除
		
		Cookie cookie=new Cookie("autoLogin","");//避免注销不掉
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);//清空cookie
		
		request.getRequestDispatcher("/index.jsp").forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);

	}

}
