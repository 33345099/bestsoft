package org.cl.system.security.web;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.cl.common.core.web.BaseAction;
import org.cl.common.util.HttpTool;
import org.cl.interfaces.UserInterface;
import org.cl.userManager.entity.User;

/**
 * 首页相关信息
 * 
 * @author chenl
 * 
 */
@Namespace("/system")
@Results( {
		@Result(name = "index", location = "/WEB-INF/index.jsp"),
		@Result(name = "showPersonInfo", location = "/WEB-INF/showPersonInfo.jsp"),
		@Result(name = "myInfo", location = "/WEB-INF/myInfo.jsp") })
public class IndexAction extends BaseAction {

	/**
	 * 显示个人信息
	 */
	public String showPersonInfo() throws Exception {
		User user = (User) userService.findById(UserInterface.getCurentUser()
				.getId());
		HttpTool.setAttribute("user", user);
		return "showPersonInfo";
	}

	/**
	 * 首页
	 */
	public String myInfo() throws Exception {
		return "myInfo";
	}
}
