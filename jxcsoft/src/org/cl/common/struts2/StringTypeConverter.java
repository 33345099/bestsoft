package org.cl.common.struts2;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;
import org.cl.common.util.StrUtils;


public class StringTypeConverter extends StrutsTypeConverter {
	
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (StrUtils.isNullOrBlank(values[0]))
			return null;
		return values[0];
	}

	
	public String convertToString(Map context, Object o) {
		String[] strArray = (String[]) o;
		for (int i = 0; i < strArray.length; i++) {
			if (StrUtils.isNullOrBlank(strArray[i])) {
				strArray[i] = null;
			}
		}
		return strArray[0];
	}
}
