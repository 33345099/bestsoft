package org.cl.common.core.web;

import org.apache.struts2.ServletActionContext;
import org.cl.common.util.ServletUtils;
import org.cl.system.log.LogTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * struts2统一入口拦截器
 * 
 * @author chenl
 * @2009-6-19 下午11:34:58
 */
public class StrutsGlobalInterceptor extends AbstractInterceptor {
	protected final Logger logger = LoggerFactory.getLogger("系统访问信息");

	
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		String result = null;
		Integer isSuccess = 1;
		String methodName = ServletActionContext.getRequest().getServletPath();
		int methodIndexOf = methodName.indexOf("!");
		if (methodIndexOf >= 0) {
			methodName = methodName.substring(methodIndexOf + 1);
			methodName = methodName.substring(0, methodName.indexOf("."));
		}
		methodName = actionInvocation.getAction().getClass().getSimpleName() + "." + methodName;
		try {
			ServletUtils.transferStartWith(ServletActionContext.getRequest(), "transfer");
			ServletUtils.transferStartWith(ServletActionContext.getRequest(), "filter");
			result = actionInvocation.invoke();
			// 传递查询参数和传递参数
		} catch (Exception e) {
			isSuccess = 0;
			logger.error(e.getMessage(), e);
		} finally {
			LogTool.saveOperationLog(methodName, isSuccess);
		}
		return result;
	}
}
