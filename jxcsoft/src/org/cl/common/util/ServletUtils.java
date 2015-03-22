package org.cl.common.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;

public class ServletUtils {
	/**
	 * 取得带相同前缀的Request Parameters.
	 * 
	 * @param request
	 * @param prefix
	 *            前缀 多个前缀以-隔开
	 * @param isAttribute
	 *            取参数的范围
	 * @return
	 */
	public static Map getParametersStartWith(HttpServletRequest request,
			String prefix, boolean isAttribute) {
		Assert.notNull(prefix);
		Enumeration paramNames = null;
		if (isAttribute) {
			paramNames = request.getAttributeNames();
		} else {
			paramNames = request.getParameterNames();
		}
		String[] prefixs = prefix.split(",");
		Map params = new HashMap();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			boolean flag = false;
			for (int k = 0; k < prefixs.length; k++) {
				if (paramName.startsWith(prefixs[k])) {
					flag = true;
				}
			}
			if (!flag) {
				continue;
			}
			String[] values = null;
			Object valueTemp = null;
			if (isAttribute) {
				valueTemp = request.getAttribute(paramName);
				values = new String[] { valueTemp.toString() };
			} else {
				values = request.getParameterValues(paramName);
			}
			if (values == null || values.length == 0) {
				continue;
			}
			if (values.length > 1) {
				valueTemp = values;
			} else {
				valueTemp = values[0];
			}
			if (values.length > 1) {
				params.put(paramName, values);
			} else {
				if (StrUtils.isNotNullOrBlank(valueTemp)
						&& !"-1".equals(valueTemp)) {
					params.put(paramName, valueTemp);
				}
			}
		}
		return params;
	}

	/**
	 * 传递参数
	 * 
	 * @param request
	 * @param prefix
	 */
	public static void transferStartWith(HttpServletRequest request,
			String prefix) {
		Enumeration paramNames = request.getParameterNames();
		String[] prefixs = prefix.split("-");
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			boolean flag = false;
			for (int k = 0; k < prefixs.length; k++) {
				if (paramName.startsWith(prefixs[k])) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				continue;
			}
			String[] values = request.getParameterValues(paramName);
			if (values == null || values.length == 0) {
				continue;
			}
			Object valueTemp = null;
			if (values.length > 1) {
				valueTemp = values;
			} else {
				valueTemp = values[0];
			}
			if (values.length > 1) {
				request.setAttribute(paramName, values);
			} else {
				if (StrUtils.isNotNullOrBlank(valueTemp)) {
					request.setAttribute(paramName, valueTemp);
				}
			}
		}
	}
}
