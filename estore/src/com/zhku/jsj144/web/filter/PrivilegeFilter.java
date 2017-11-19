package com.zhku.jsj144.web.filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhku.jsj144.domain.User;

/*
 * 权限控制过滤器
 * 1.先认证，后权限
 *   认证：我是谁？  
 *   权限：我能做什么？
 *
 * 2.当前资源是否需要权限访问，如果不需要，直接放行；如果需要，则进入3,4
 * 3.没有登录
 *      只能注册，登录 ，和查看商品   （提醒登录）
 * 4. 登录 
 *    看看登录角色是user还是admin
 *    角色是user：
 *         能：   添加购物车，显示购物车，  注销    【 放进有权限的list列表中：userlist】
 *         不能：添加商品
 *    角色是admin：
 *         能：    添加商品，注销    【 放进有权限的list列表中：adminlist】
 *         不能：添加购物车，显示购物车
 */

public class PrivilegeFilter implements Filter {

	// 创建两个容器，用来存储普通用户和管理员的有权的列表项，即:哪些是可以访问的资源
	private List<String> userlist = new ArrayList<String>();// 普通用户有权列表
	private List<String> adminlist = new ArrayList<String>();// 管理员有权列表

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			// 读取配置文件user.txt 获取普通用户的权限列表
			// InputStream in =
			// PrivilegeFilter.class.getClassLoader().getResourceAsStream("/user.txt");
			String userPath = PrivilegeFilter.class.getClassLoader()
					.getResource("user.txt").getFile();
			BufferedReader bufr = new BufferedReader(new FileReader(userPath));
			String line = null;
			while ((line = bufr.readLine()) != null) {
				userlist.add(line);// 添加权限
			}
			bufr.close();
			
			
			// 读取配置文件admin.txt 获取管理员的权限列表
			String adminPath = PrivilegeFilter.class.getClassLoader()
					.getResource("admin.txt").getFile();//首先，此处文件写为了user.txt是错了，现在改正了
			BufferedReader bufr2 = new BufferedReader(new FileReader(adminPath));
			String line2 = null;
			while ((line2 = bufr2.readLine()) != null) {
				adminlist.add(line2);// 添加权限
			}
			bufr2.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		// 获取当前访问资源的路径
		String requestURI = req.getRequestURI();// 全部路径 a/b/c /xxx
		String uri = requestURI.substring(req.getContextPath().length());// 只获得访问资源  路径： /xxx
		
		//发现，当添加到购物车时，没有权限控制！！！
		//因为权限控制写的是：/addtocart
		//而uri中的是/addtocart?id=product_1691441181
		//因此，?之后的要去掉
	    int last = uri.lastIndexOf("?");
	    if(last!=-1){//如果存在参数，则要进行处理
	    	uri=uri.substring(0,last);//处理后的情况  /addtocart
	    }
		
		if (userlist.contains(uri) ||adminlist.contains(uri)) {// 当前访问路径需要权限
	
			
			User loginUser = (User) req.getSession().getAttribute("loginUser");// 获取登录用户信息

			if (loginUser == null) {// 当前用户没有登录
//				System.out.println("对不起，你还没有登录，请先登录111-------------------");//调试
				req.setAttribute("msg", "对不起，你还没有登录，请先登录");
				req.getRequestDispatcher("/login.jsp").forward(req, resp);
			} else {// 登录了

				String role = loginUser.getRole();// 登录用户的角色
//				System.out.println("---------------role:"+role);//调试
				if ("user".equals(role)) {// 当前是普通用户
					if (userlist.contains(uri)) {// 有权限访问，放行
						chain.doFilter(req, resp);// 放行
					} else {// 提醒用户没有权限访问
//						System.out.println("对不起，你是普通用户，你没有权限访问该资源");//调试
						req.setAttribute("msg", "对不起，你是普通用户，你没有权限访问该资源");
						req.getRequestDispatcher("/message2.jsp").forward(req,resp);
					}
				}
				
				if ("admin".equals(role)) {// 当前是管理员
					if (adminlist.contains(uri)) {// 有权限访问，放行
						chain.doFilter(req, resp);// 放行
					} else {// 提醒用户没有权限访问
//						System.out.println("对不起，你是管理员，你没有权限访问该资源");//调试
						req.setAttribute("msg", "对不起，你是管理员，你没有权限访问该资源");
						req.getRequestDispatcher("/message2.jsp").forward(req,resp);
					}
				}
			}
		}
		else{//当前资源不需要权限，放行
//			System.out.println("对不起，你还没有登录，请先登录222-------------------");//调试
			chain.doFilter(req, resp);// 放行
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
