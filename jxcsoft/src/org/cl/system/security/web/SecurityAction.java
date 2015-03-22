package org.cl.system.security.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.cl.common.core.SystemConstant;
import org.cl.common.core.web.BaseAction;
import org.cl.common.util.HttpTool;
import org.cl.common.util.SpringTool;
import org.cl.common.util.StrUtils;
import org.cl.common.widget.tree.TreeEntity;
import org.cl.system.log.LogTool;
import org.cl.system.resource.entity.Menu;
import org.cl.system.resource.service.MenuService;
import org.cl.system.security.KeyManager;
import org.cl.system.security.context.ContextHolder;
import org.cl.userManager.entity.User;
import org.cl.userManager.service.UserService;

/**
 * 安全Action
 * 
 * @author chenl
 * @data 2009-9-4
 */
@Namespace("/system")
@Results( { @Result(name = "index", location = "/WEB-INF/index.jsp"),
		@Result(name = "login", location = "/login.jsp"),
		@Result(name = "regedit", location = "/regedit.jsp")})
public class SecurityAction extends BaseAction {
	private UserService userService = (UserService) SpringTool
			.getBean(UserService.class);

	private String code;
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 注册
	 * @return
	 */
	public String regedit(){
		KeyManager keyManager = SpringTool.getSpringBean(KeyManager.class);
		if(keyManager.isRegedited()){
			return "login";
		}
		String result = keyManager.regedit(code)?"1":"2";			
		HttpTool.setAttribute("regeditStatus", result);
		return "regedit";
	}

	/**
	 * 登录
	 */
	public String login() throws Exception {
		KeyManager keyManager = SpringTool.getSpringBean(KeyManager.class);
		if(keyManager.isOutOfDate()){
			//如果试用过期
			HttpTool.setAttribute("error", "试用期结束，请<a style='color:black;' href='" + ServletActionContext.getServletContext().getContextPath() + "/regedit.jsp'>注册</a>");
			return "login";
		}
		HttpSession session = ServletActionContext.getRequest()
				.getSession(true);
		String username = HttpTool.getParameter("username", "").trim();
		String password = HttpTool.getParameter("password", "").trim();
		// 检验用户名和密码是否为空
		if (StrUtils.isNullOrBlank(username)
				|| StrUtils.isNullOrBlank(password)) {
			return "login";
		}
		// 根据用户加载用户
		User user = userService.loadUserByUsername(username);
		// 登录失败
		if (null == user) {
			HttpTool.setAttribute("error", "不存在此用户!");
			return "login";
		}
		if (!user.getPassword().equals(password)) {
			HttpTool.setAttribute("error", "密码错误!");
			return "login";
		}
		if (null != user.getIsEnable() && user.getIsEnable() == 0) {
			HttpTool.setAttribute("error", "该账户已经停用，请联系管理员!");
			return "login";
		}
		// 组装用户
		user = userService.loadSecurityUser(user);
		session.setAttribute("currentUser", user);
		//将登陆的用户添加到在线列表中
		ContextHolder contextHolder = SpringTool.getSpringBean(ContextHolder.class);
		contextHolder.addUser(username, new Date(), session.getId());

		LogTool.saveLoginLog(ServletActionContext.getRequest(), user);// 记录登录日志
		return jump("security!index.action", null);
	}

	/**
	 * 显示首页页面
	 */
	public String index() throws Exception {
		HttpTool.setAttribute("rootMenu", ((MenuService) SpringTool
				.getBean(MenuService.class))
				.findById(SystemConstant.ROOT_MENU_ID));
		return "index";
	}

	/**
	 * 注销
	 */
	public String logout() throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.invalidate();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");// 防止弹出的信息出现乱码
		PrintWriter out = response.getWriter();
		out.print("<script>top.window.location.href='"
				+ ServletActionContext.getRequest().getContextPath()
				+ "/login.jsp'</script>");
		return null;
	}

	/**
	 * 根据父ID显示菜单树
	 */

	public String treeMenu() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		Long parentId = HttpTool.getLongParameter("parentId");
		// 遍历用户可以访问的所有菜单
		List<Menu> userAllMenuList = ((User) request.getSession().getAttribute(
				"currentUser")).getMenus();
		List<Menu> list = new ArrayList();
		for (int i = 0; i < userAllMenuList.size(); i++) {
			Menu menu = userAllMenuList.get(i);
			if (null != menu.getParentId()
					&& menu.getParentId().compareTo(parentId) == 0) {
				list.add(menu);
			}
		}
		List jsonList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Menu menu = list.get(i);
			TreeEntity tree = new TreeEntity(menu.getId().toString(), menu
					.getName(), menu.getIsLeaf(), menu.getUrl());
			jsonList.add(tree);
		}
		return json(jsonList);
	}

	/**
	 * 修改密码
	 */
	public String updatePassword() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("currentUser");
		String newPassword = HttpTool.getParameter("user.password");
		userService.updatePassword(newPassword, user.getId());
		session.invalidate();
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");// 防止弹出的信息出现乱码
		out.print("<script>alert('修改密码成功，请重新登录！');</script>");
		out.print("<script>top.window.location.href='"
				+ request.getContextPath() + "/login.jsp'</script>");
		return null;
	}
}
