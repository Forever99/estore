package com.zhku.jsj144.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhku.jsj144.domain.User;
import com.zhku.jsj144.service.UserService;
//登录
public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");//用户名
		String password = request.getParameter("password");//密码
		String check = request.getParameter("check");//是否勾选自动登录按钮
		UserService service=new UserService();
		
		//判断是否登录成功
		User user=service.selectByNameAndPass(username,password);
		if(user==null){
			//登录失败
			request.setAttribute("msg", "用户名或者密码错误，请重新登录");
			request.getRequestDispatcher("/login.jsp").forward(request, response);//重新注册
		}
		else{
			//登录成功
			
			//勾选了自动登录按钮
			if("on".equals(check)){
				//将用户名和密码保存到cookie中
				Cookie cookie=new Cookie("autoLogin",username+"_estore_"+password);
				cookie.setMaxAge(60*60*24*7);//设置保存时间为1周
				cookie.setPath("/");//当前主机下
				response.addCookie(cookie);//将cookie回给浏览器
			}
			
			//保存登录成功信息，并进行页面的跳转
			request.getSession().setAttribute("loginUser", user);//保存登录信息到session域中的loginUser中
			response.sendRedirect("/afterLoginMessage.jsp");//返回主页
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);

	}

}
