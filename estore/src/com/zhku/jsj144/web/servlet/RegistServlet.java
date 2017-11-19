package com.zhku.jsj144.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.zhku.jsj144.domain.User;
import com.zhku.jsj144.service.UserService;
//注册
public class RegistServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		User user=new User();
		//封装数据
		try {
			BeanUtils.populate(user, request.getParameterMap());//封装数据到user
			//校验密码
			String password = request.getParameter("password");//密码
			String repassword = request.getParameter("repassword");//确认密码
			if(password!=null&&password!=null){
				if(!password.equals(repassword)){//密码不匹配
					request.setAttribute("msg", "对不起，两次密码输入不匹配，请重新输入新密码");
					user.setPassword("");//清空密码
					request.setAttribute("user", user);//用户填写信息
					request.getRequestDispatcher("/regist.jsp").forward(request, response);//重新注册
					return;
				}
			}
			
			//如果邮箱确认用户是否注册过，1.如果已经数据库中存在该邮箱号，则说明用户已经注册过，则注册失败，
			//2.注册成功
			UserService service=new UserService();
			User findUser=service.selectByEmail(user);
			if(findUser!=null){
				request.setAttribute("msg", "对不起，该邮箱已被注册，请重新填写邮箱信息");
				user.setEmail("");//清空邮箱
				request.setAttribute("user", user);//用户填写信息
				request.getRequestDispatcher("/regist.jsp").forward(request, response);//重新注册
			}
			else{
				//进行注册
				service.insert(user);//进行注册
				//获得注册后的user信息
				user=service.selectByNameAndPass(user.getUsername(), user.getPassword());
				request.getSession().setAttribute("loginUser", user);
				request.setAttribute("msg", "恭喜你注册成功");
				request.getRequestDispatcher("/afterLoginMessage.jsp").forward(request, response);//返回主页
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);

	}

}
