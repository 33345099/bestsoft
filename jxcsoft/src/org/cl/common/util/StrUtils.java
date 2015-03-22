package org.cl.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 字符串工具类
 * 
 * @author chenl
 * 
 */

public class StrUtils {
	/**
	 * 判断字符串是否不为空或者不为空字符
	 * 
	 * @param str
	 *            允许为NULL
	 * @return
	 */
	public static boolean isNotNullOrBlank(Object o) {
		if (null == o) {
			return false;
		} else {
			String str = o.toString();
			if ("".equals(str.trim())) {
				return false;
			} else {
				return true;
			}
		}
	}

	/**
	 * 判断字符串是否为空或者不为空字符
	 * 
	 * @param str
	 *            允许为NULL
	 * @return
	 */
	public static boolean isNullOrBlank(Object o) {
		return !isNotNullOrBlank(o);
	}

	/**
	 * 将sourceObject转换为字符串后与compareStr对象比较，如果相等就返回afterIsEqualToObject对象，
	 * 不相等就返回sourceObject
	 * 
	 * @param sourceObject
	 *            被比较的对象
	 * @param compareStr
	 *            比较的对象
	 * @param object
	 *            如果相等，则返回afterIsEqualToObject对象
	 */
	public static Object toStringForCompare(Object sourceObject,
			String compareStr, Object afterIsEqualToObject) {
		if (null == sourceObject) {
			return sourceObject;
		} else if (sourceObject.toString().equals(compareStr)) {
			return compareStr;
		} else {
			return null;
		}

	}

	/**
	 * 如果对象为空，则返回空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String isNullToBank(Object str) {
		if (null == str) {
			return "";
		} else {
			return str.toString();
		}
	}

	/**
	 * 将对象转换为String类型
	 * 
	 * @param str
	 * @return
	 */
	public static String ObjectToStr(Object o, String defaultValue) {
		if (null == o) {
			return defaultValue;
		} else {
			return o.toString();
		}
	}

	/**
	 * 将对象转换为String类型
	 * 
	 * @param str
	 * @return
	 */
	public static String ObjectToString(Object o) {
		if (null == o) {
			return null;
		} else {
			return o.toString();
		}
	}

	/**
	 * 将对象转换为Long类型
	 * 
	 * @param str
	 * @return
	 */
	public static Long ObjectToLong(Object o, Long defaultValue) {
		if (null == o) {
			return defaultValue;
		} else {
			return new Long(o.toString());
		}
	}

	/**
	 * 将对象转换为Long类型
	 * 
	 * @param str
	 * @return
	 */
	public static Long toLong(Object o) {
		if (null == o) {
			return 0l;
		} else {
			return new Long(o.toString());
		}
	}

	/**
	 * 将对象转换为Integer类型
	 * 
	 * @param str
	 * @return
	 */
	public static Integer ObjectToInteger(Object o) {
		if (null == o) {
			return null;
		} else {
			return ((java.math.BigDecimal) o).intValue();
		}
	}

	/**
	 * 将对象转换为Integer类型
	 * 
	 * @param str
	 * @return
	 */
	public static Double ObjectToDouble(Object o) {
		if (null == o) {
			return null;
		} else {
			return ((java.math.BigDecimal) o).doubleValue();
		}
	}

	/**
	 * 集合转换成字符串
	 * 
	 * @param collection
	 *            集合
	 * @param separator
	 *            分隔符
	 * @return
	 */
	public static String collectionToStr(Collection collection,
			String separator, boolean all) {
		if (null == collection || collection.size() == 0) {
			return null;
		}
		Iterator it = collection.iterator();
		Object object = null;
		String str = "";
		while (it.hasNext()) {
			object = it.next();
			str = str + object.toString() + separator;
		}
		if (collection.size() > 0) {
			if (all) {
				str = "," + str;
			} else {
				str = str.substring(0, str.length() - (separator.length()));
			}
		}
		return str;
	}

	/**
	 * 数组转换成字符串
	 * 
	 * @param collection
	 *            集合
	 * @param separator
	 *            分隔符
	 * @return
	 */
	public static String arrayToStr(Object[] objects, String separator,
			boolean all) {
		if (null == objects || objects.length == 0) {
			return null;
		}
		String str = "";
		for (int i = 0; i < objects.length; i++) {
			str = str + objects[i].toString() + separator;
		}
		if (objects.length > 0) {
			if (all) {
				str = "," + str;
			} else {
				str = str.substring(0, str.length() - (separator.length()));
			}
		}
		return str;
	}

	/**
	 * id字符串转换为集合
	 * 
	 * @param dataScopeOrgs
	 * @return
	 */
	public static List<Long> idsToList(String ids) {
		List list = new ArrayList();
		if (StrUtils.isNullOrBlank(ids)) {
			return list;
		}
		String[] idArray = ids.split(",");
		for (int i = 0; i < idArray.length; i++) {
			if (StrUtils.isNotNullOrBlank(idArray[i])) {
				list.add(new Long(idArray[i]));
			}
		}
		return list;
	}

	/**
	 * 去掉前后的逗号
	 * 
	 * @param dataScopeOrgs
	 * @return
	 */
	public static String cutComma(String dataScopeOrgs) {
		if (StrUtils.isNullOrBlank(dataScopeOrgs)) {
			return null;
		}
		// 去掉前面的逗号
		if (dataScopeOrgs.startsWith(",")) {
			dataScopeOrgs = dataScopeOrgs.substring(1, dataScopeOrgs.length());
		}
		// 去掉后面的逗号
		if (dataScopeOrgs.endsWith(",")) {
			dataScopeOrgs = dataScopeOrgs.substring(0,
					dataScopeOrgs.length() - 1);
		}
		return dataScopeOrgs;
	}
}
