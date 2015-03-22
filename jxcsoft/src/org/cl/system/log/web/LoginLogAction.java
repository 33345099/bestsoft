package org.cl.system.log.web;

import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.cl.common.core.web.BaseAction;
import org.cl.common.util.HttpTool;
import org.cl.common.util.SpringTool;
import org.cl.common.widget.pagination.Page;
import org.cl.system.log.service.LoginLogService;

/**
 * 用户登录日志Action
 * 
 * @author chenl
 * @data Dec 2, 2009
 */
@Namespace("/system")
@Action("loginLog")
@Results( { @Result(name = "listLoginLog", location = "/WEB-INF/content/system/log/listLoginLog.jsp") })
public class LoginLogAction extends BaseAction {
	protected LoginLogService loginLogService = (LoginLogService) SpringTool.getBean(LoginLogService.class);

	/**
	 * 现实用户登录日志列表
	 */
	public String listLoginLog() throws Exception {
		Map searchMap = super.buildSearch();
		page = new Page(HttpTool.getPageNum());
		loginLogService.findByPage(page, searchMap);
		return "listLoginLog";
	}
}
