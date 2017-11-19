package com.zhku.jsj144.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhku.jsj144.domain.User;
import com.zhku.jsj144.service.UserService;

//自动登录过滤器

public class AutoLoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		/*
		 * 1.对于一些资源，不用自动登录，直接放行即可 （1）注册时 /regist /regist.jsp （2）登录时 /login.jsp
		 * /login （3）注销时 /logout 访问以上这些资源时，都无需自动登录 ，直接放行即可
		 * 
		 * 2.获得session，如果处于登录状态，放行；如果没有登录，则进入3
		 * 3.获得cookie，如果没有勾选，清空cookie信息，直接放行，如果勾选了，进入4
		 * 4.获得cookie中的信息，判断里面的信息是否被篡改，如果被篡改，清楚cookie，然后放行；
		 * 如果没有被篡改，则进行登录，并保存到session中
		 * 
		 * 注意：用户在注销时也要清空cookie信息，防止注销不掉（因此用户注销后，虽然session没有了，当时cookie中的信息仍在，
		 * 而且cookie中的信息包括用户名和密码
		 * ，因为注销后会跳转到index.jsp页面，这时候会执行自动登录逻辑，通过cookie中的信息完成，
		 * 因此，为了防止注销不掉发生，需要在销毁session的同时，要销毁cookie中的信息）
		 */

		// 1.
		String requestURI = req.getRequestURI();// 全部路径 a/b/c /xxx
		String uri = requestURI.substring(req.getContextPath().length());// /xxx
//		String uri = req.getRequestURI();
		
		// 访问这些资源时，直接放行
		if (uri.equals("/regist") || uri.equals("/regist.jsp")
				|| uri.equals("/login.jsp") || uri.equals("/login")
				|| uri.equals("/logout")) {
			chain.doFilter(req, resp);// 直接放行
			return;
		}
		// 2.
		User loginUser = (User) req.getSession().getAttribute("loginUser");
		if (loginUser != null) {
			chain.doFilter(req, resp);// 直接放行
			return;
		}
		// 3.
		Cookie[] cookies = req.getCookies();// 获得所有cookie,看是否存在autoLogin
		Cookie cookie = findCookie(cookies);// 寻找cookie
		if (cookie == null) {
			chain.doFilter(req, resp);// 放行
			
			/*
			 * 发现了一些多余代码
			 * 而且chain.doFilter(req, resp);这段代码写了两次
			Cookie cookie2 = new Cookie("autoLogin", "");
			cookie2.setMaxAge(0);
			cookie2.setPath("/");
			resp.addCookie(cookie2);// 清空cookie
			chain.doFilter(req, resp);// 直接放行
			*/
		} 
		else {
			// 4.
			String value = cookie.getValue();
			String[] split = value.split("_estore_");
			String username = split[0];
			String password = split[1];
			// 判断信息是否被篡改
			UserService service = new UserService();
			User user = service.selectByNameAndPass(username, password);// 其实这里做的就是登录动作
			if (user == null) {// 信息篡改，清空cookie后放行
				Cookie cookie2 = new Cookie("autoLogin", "");
				cookie2.setMaxAge(0);
				cookie2.setPath("/");
				resp.addCookie(cookie2);// 清空cookie
				chain.doFilter(req, resp);// 直接放行
			} else {// 进行保存session
				req.getSession().setAttribute("loginUser", user);// 保存登录信息到session域中的loginUser中
			
//				resp.sendRedirect("/afterLoginMessage.jsp");// 返回主页
//				//可能此段代码导致异常
				
				chain.doFilter(req, resp);// 放行
			}
		}
	}

	// 寻找cookie
	private Cookie findCookie(Cookie[] cookies) {
		if (cookies.length == 0) {
			return null;
		}
		for (Cookie cookie : cookies) {
			String name = cookie.getName();
			if ("autoLogin".equals(name)) {
				return cookie;
			}
		}
		return null;
	}

	@Override
	public void destroy() {
	}

}
