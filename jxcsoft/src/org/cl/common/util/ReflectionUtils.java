package org.cl.common.util;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.beanutils.ConvertUtils;

/**
 * Bean操作工具类
 * 
 * @author cl
 * 
 */
public class ReflectionUtils {
	/**
	 * 转换字符串到相应类型.
	 * 
	 * @param value
	 *            待转换的字符串
	 * @param toType
	 *            转换目标类型
	 */
	public static Object convertStringToObject(String value, Class toType) {
		if (toType.getSimpleName().equals("Date")) {
			Date date = null;
			try {
				date = DateUtils.convertDate(value, "yyyy-MM-dd");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
		} else {
			return ConvertUtils.convert(value, toType);
		}
	}

	/**
	 * 将源对象中的所有非空属性赋值到target中
	 * 
	 * @param target
	 *            目标对象
	 * @param source
	 *            源对象
	 */
	public static void copyPropertiesForHasValue(Object target, Object source) {
		// 获取
		List<Field> filedList = ReflectionUtils.getAllFieldAndSuperclass(source
				.getClass());
		Field field = null;
		try {
			for (int i = 0; i < filedList.size(); i++) {
				field = filedList.get(i);
				field.setAccessible(true);
				Object value = field.get(source);
				if (StrUtils.isNotNullOrBlank(value)) {
					if (value.toString().equals("-1")) {
						value = null;
					}
					org.springframework.util.ReflectionUtils.setField(field,
							target, value);
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取一个类里面的属性
	 * 
	 * @param clazz
	 * @return
	 */
	public static List<Field> getAllFieldAndSuperclass(Class clazz) {
		Field[] fileds = null;
		List filedList = new ArrayList();
		while (true) {
			if (null == clazz) {
				break;
			}
			fileds = clazz.getDeclaredFields();
			for (int i = 0; i < fileds.length; i++) {
				filedList.add(fileds[i]);
			}
			clazz = clazz.getSuperclass();
		}
		return filedList;
	}
}
