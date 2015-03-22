package org.cl.common.struts2;

import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.StrutsTypeConverter;
import org.cl.common.util.StrUtils;

public class LongTypeConverter extends StrutsTypeConverter {

	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (StrUtils.isNullOrBlank(values[0])) {
			return null;
		}
		if (!values[0].startsWith("-")) { // 判断是否是时间格式的转换为数值格式的
			values[0] = values[0].replaceAll("-", "");
			values[0] = values[0].replaceAll(":", "");
		}
		String methodName = ServletActionContext.getRequest().getServletPath();
		if (methodName.indexOf("save") >= 0 && values[0].equals("-1")) {
			return null;
		}
		return new Long(values[0]);
	}

	public String convertToString(Map context, Object o) {
		if (null != o) {
			return o.toString();
		}
		return null;
	}

}
