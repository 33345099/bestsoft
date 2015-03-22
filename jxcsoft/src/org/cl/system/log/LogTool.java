package org.cl.system.log;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.cl.common.core.SystemConstant;
import org.cl.common.util.SpringTool;
import org.cl.common.util.XmlDom4j;
import org.cl.system.log.entity.LoginLog;
import org.cl.system.log.entity.OperationLog;
import org.cl.system.log.service.LoginLogService;
import org.cl.system.log.service.OperationLogService;
import org.cl.userManager.entity.User;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类
 * 
 * @author chenl
 * @data Dec 3, 2009
 */

public class LogTool {
	protected final static Logger logger = LoggerFactory.getLogger("系统访问信息");

	/**
	 * 记录登录日志
	 * 
	 * @param request
	 * @param user
	 */
	public static void saveLoginLog(HttpServletRequest request, User user) {
		LoginLogService loginLogService = (LoginLogService) SpringTool.getBean(LoginLogService.class);
		LoginLog loginLog = new LoginLog();
		loginLog.setUserName(user.getName());
		loginLog.setLoginTime(new Date());
		loginLog.setLoginIp(request.getRemoteHost());
		loginLog.setOrgName(user.getOrganization().getName());
		loginLogService.save(loginLog);
		request.getSession().setAttribute("loginLogId", loginLog.getId());
	}

	/**
	 * 系统保存操作日志
	 * 
	 * @param request
	 * @param methodName
	 * @param isSuccess
	 */
	public static void saveOperationLog(String methodName, Integer isSuccess) {
		HttpServletRequest request = ServletActionContext.getRequest();
		OperationLogService operationLogServiceService = (OperationLogService) SpringTool
				.getBean(OperationLogService.class);
		String property = SystemConstant.logProperties.getProperty(methodName);
		if (property != null) {
			User user = (User) request.getSession().getAttribute("currentUser");
			if (null == user) {
				return;
			}
			OperationLog operationLog = new OperationLog();
			String[] values = property.split("--");
			if (values.length != 2) {
				logger.error("日志配置不正确，请重新配置!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
			}
			operationLog.setOrgName(user.getOrganization().getName());
			operationLog.setBusinessName(values[0]);
			operationLog.setOperationName(values[1]);
			operationLog.setOperationDate(new Date());
			operationLog.setLoginLogId((Long) request.getSession().getAttribute("loginLogId"));
			operationLog.setUserName(user.getName());
			operationLog.setIsSuccess(isSuccess);
			operationLogServiceService.save(operationLog);
		}
	}

	/**
	 * 加载系统日志文件
	 */
	public static void initLog() {
		XmlDom4j xmlDom4j = new XmlDom4j();
		Document document = xmlDom4j.loadFile("log-config.xml");
		Element element = null;
		List<Element> logElementList = xmlDom4j.getRootElement(document).selectNodes("log");
		String key = null;
		String value = null;
		for (int i = 0; i < logElementList.size(); i++) {
			element = logElementList.get(i);
			key = element.attributeValue("key");
			value = element.attributeValue("value");
			SystemConstant.logProperties.put(key, value);
		}
	}

}
