package org.cl.system.security.web;

import java.io.IOException;
import java.io.PrintWriter;
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
import javax.servlet.http.HttpSession;

import org.cl.common.core.SystemConstant;

/**
 * 权限控制标签，暂时验证是否登录，暂未时间写是否有权限访问
 * 
 * @author chenl
 * @data Jan 13, 2010
 */
public class SecurityFilter implements Filter {

	protected FilterConfig filterConfig = null;

	private List<String> notCheckLoginUrlList = new ArrayList(); // 不需要判断是否登录的页面

	
	public void destroy() {

	}

	
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String currentUrl = request.getServletPath() + (request.getPathInfo() == null ? "" : request.getPathInfo());
		// 判断用户是否登录
		if (!checkLoginFilter(request, response, currentUrl)) {
			PrintWriter out = response.getWriter();
			out.println("<script>top.location.href = '" + request.getContextPath() + "/login.jsp" + "'</script>");
			return;
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	/**
	 * 用户访问此资源是否登录
	 */
	private boolean checkLoginFilter(HttpServletRequest request, HttpServletResponse response, String currentUrl) {
		for (int i = 0; i < notCheckLoginUrlList.size(); i++) {
			if (currentUrl.indexOf(notCheckLoginUrlList.get(i)) >= 0) {
				return true;
			}
		}
		HttpSession session = request.getSession();
		if (session.getAttribute("currentUser") != null) {
			return true;
		}
		return false;
	}

	/**
	 * 初始化
	 */
	
	public void init(FilterConfig filterConfig) throws ServletException {
		String not_check_url_listStr = SystemConstant.getSystemConstant("not_check_url_list");
		String[] not_check_urls = not_check_url_listStr.split("---");
		for (int i = 0; i < not_check_urls.length; i++) {
			notCheckLoginUrlList.add(not_check_urls[i]);
		}
	}
}
