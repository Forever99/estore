package com.zhku.jsj144.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
//全站请求数据过滤器
public class RequestFilter implements Filter {

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//拦截
		//进行编码设置
		request.setCharacterEncoding("utf-8");
		chain.doFilter(request, response);//放行
	}

	@Override
	public void init(FilterConfig config) throws ServletException {}
}
