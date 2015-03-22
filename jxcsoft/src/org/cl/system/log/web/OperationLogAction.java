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
import org.cl.system.log.service.OperationLogService;

/**
 * 系统操作日志
 * 
 * @author chenl
 * @data Mar 22, 2010
 */
@Namespace("/system")
@Action("operationLog")
@Results( {
		@Result(name = "listOperationLog", location = "/WEB-INF/content/system/log/listOperationLog.jsp"),
		@Result(name = "listOperationLogForLoginLog", location = "/WEB-INF/content/system/log/listOperationLogForLoginLog.jsp") })
public class OperationLogAction extends BaseAction {
	private OperationLogService operationLogService = (OperationLogService) SpringTool
			.getBean(OperationLogService.class);

	/**
	 * 分页查询系统登录日志
	 */
	public String listOperationLog() throws Exception {
		Map searchMap = super.buildSearch();
		page = new Page(HttpTool.getPageNum());
		operationLogService.findByPage(page, searchMap);
		return "listOperationLog";
	}

	/**
	 * 按登录日志获取所属的操作日志
	 */
	public String listOperationLogForLoginLog() throws Exception {
		Map searchMap = super.buildSearch();
		page = new Page(HttpTool.getPageNum());
		super.transferBuildSearch(searchMap, "and", "EQ","L", "transfer_loginLogId", "loginLogId");
		operationLogService.findByPage(page, searchMap);
		return "listOperationLogForLoginLog";
	}
}
