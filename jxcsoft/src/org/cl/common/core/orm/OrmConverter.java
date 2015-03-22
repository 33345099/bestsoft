package org.cl.common.core.orm;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.cl.common.util.ReflectionUtils;
import org.cl.common.util.StrUtils;
import org.cl.interfaces.UserInterface;
import org.cl.userManager.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 底层封装（filter_matchType_propertyName_operation_propertyType）
 * 
 * @author chenl 2010-10-5
 */
public class OrmConverter {

	protected static final Logger logger = LoggerFactory
			.getLogger(OrmConverter.class);

	/** 属性比较类型. */
	public enum MatchType {
		EQ, LIKE, LT, GT, LE, GE, IS, ISNOT, NOEQ
	}

	/** 属性数据类型. */
	public enum PropertyType {
		S(String.class), I(Integer.class), L(Long.class), D(Double.class), T(
				Date.class), B(Boolean.class);

		private Class<?> clazz;

		PropertyType(Class<?> clazz) {
			this.clazz = clazz;
		}

		public Class<?> getValue() {
			return clazz;
		}
	}

	/**
	 * 获取属性类型
	 * 
	 * @param str
	 * @return
	 */
	public static Class getPropertyType(String str) {
		return Enum.valueOf(OrmConverter.PropertyType.class, str).getValue();
	}

	/**
	 * 获取属性值
	 * 
	 * @param str
	 * @return
	 */
	public static Object getPropertyValue(Object value, Class propertyType,
			String matchType, String queryName) {
		if (null == value) {
			return null;
		}
		Object object = null;
		try {
			if (null != matchType && "like".equals(matchType)) {
				value = "%" + value + "%";
			}
			if (null != matchType && "is".equals(matchType) && null == value) {
				return null;
			}
			if (null != matchType && "is not".equals(matchType)
					&& null == value) {
				return null;
			}
			object = ReflectionUtils.convertStringToObject(value.toString(),
					propertyType);
		} catch (Exception e) {
			logger.info(queryName + "获取属性类型错误！");
			e.printStackTrace();
		}
		return object;
	}

	/**
	 * 获取真实的比较类型
	 * 
	 * @param matchType
	 * @return
	 */
	public static String getMatchType(MatchType matchType) {
		if (MatchType.EQ.equals(matchType)) {
			return "=";
		} else if (MatchType.LIKE.equals(matchType)) {
			return "like";
		} else if (MatchType.LE.equals(matchType)) {
			return "<=";
		} else if (MatchType.LT.equals(matchType)) {
			return "<";
		} else if (MatchType.GE.equals(matchType)) {
			return ">=";
		} else if (MatchType.GT.equals(matchType)) {
			return ">";
		} else if (MatchType.IS.equals(matchType)) {
			return "is";
		} else if (MatchType.ISNOT.equals(matchType)) {
			return "is not";
		} else if (MatchType.NOEQ.equals(matchType)) {
			return " !=  ";
		} else {
			throw new IllegalArgumentException("无法得到属性比较类型.");
		}
	}

	/**
	 * 拼写查询语句
	 * 
	 * @param queryString
	 * @param filterMap
	 * @param valuelist
	 */
	public static void assemblyQuery(StringBuffer queryString,
			Map<String, String> filterMap, List valueList) {
		String[] temp = null;
		for (String o : filterMap.keySet()) {
			if (o.startsWith("filter")) {
				getQuery(queryString, filterMap, o, null, valueList);
			}
		}
		boolean isOrder = false;
		// 第二次遍历排序参数
		for (String o : filterMap.keySet()) {
			if (o.toString().startsWith("order")) {
				temp = o.split("_");
				if (queryString.toString().indexOf("order by") < 0) {
					queryString.append(" order by");
				} else {
					queryString.append(",");
				}
				queryString.append(" " + temp[1] + " " + filterMap.get(o));
				if (!isOrder) {
					isOrder = true;
				}
			}
		}
		// 封装数据权限
		security(queryString, filterMap);
		if (!isOrder) {
			queryString.append(" order by id desc");
		}
	}

	/**
	 * 从filterMap中获取参数拼写查询语句
	 * 
	 * @param queryString
	 * @param filterMap
	 * @param queryName
	 * @param propertyName
	 * @param valueList
	 */
	public static void getQuery(StringBuffer queryString,
			Map<String, String> filterMap, String queryName,
			String propertyName, List valueList) {
		Object value = filterMap.get(queryName);
		queryName = queryName.replaceAll("__", ".");
		String[] temp = queryName.split("_");
		MatchType matchType = null;
		try {
			matchType = Enum.valueOf(MatchType.class, temp[3]);
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("filter名称" + queryName
					+ "没有按规则编写,无法得到属性比较类型.", e);
		}
		// 过滤掉为空的值
		if (!MatchType.IS.equals(matchType)
				&& !MatchType.ISNOT.equals(matchType)) {
			if (StrUtils.isNullOrBlank(value)) {
				return;
			}
		}
		Class propertyType = null;
		Object propertyValue = null;
		try {
			propertyType = Enum.valueOf(PropertyType.class, temp[4]).getValue();
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("filter名称" + queryName
					+ "没有按规则编写,无法得到属性值类型.", e);
		}
		if (null != value) {
			value = value.toString();
		}
		if (null != propertyName) {
			temp[2] = propertyName;
		}
		propertyValue = getPropertyValue(value, propertyType,
				getMatchType(matchType), queryName);
		if (!MatchType.IS.equals(matchType)
				&& !MatchType.ISNOT.equals(matchType)) {
			queryString.append(" " + temp[1] + " " + temp[2] + " "
					+ getMatchType(matchType) + " ?");
			valueList.add(propertyValue);
		} else {
			queryString.append(" " + temp[1] + " " + temp[2] + " "
					+ getMatchType(matchType) + " null");
		}
	}

	/**
	 * 直接根据value拼写查询语句
	 * 
	 * @param queryString
	 * @param queryName
	 * @param propertyName
	 * @param propertyValue
	 * @param valueList
	 */
	public static void getQueryByValue(StringBuffer queryString,
			String queryName, String propertyName, Object propertyValue,
			List valueList) {
		queryName = queryName.replaceAll("__", ".");
		String[] temp = queryName.split("_");
		MatchType matchType = null;
		try {
			matchType = Enum.valueOf(MatchType.class, temp[3]);
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("filter名称" + queryName
					+ "没有按规则编写,无法得到属性比较类型.", e);
		}
		if (null != propertyName) {
			temp[2] = propertyName;
		}
		if (!MatchType.IS.equals(matchType)
				&& !MatchType.ISNOT.equals(matchType)) {
			queryString.append(" " + temp[1] + " " + temp[2] + " "
					+ getMatchType(matchType) + " ?");
			valueList.add(propertyValue);
		} else {
			queryString.append(" " + temp[1] + " " + temp[2] + " "
					+ getMatchType(matchType) + " null");
		}
	}

	/**
	 * 创建信息自动赋值
	 * 
	 * @param baseEntity
	 */
	public static void createInfo(BaseEntity baseEntity) {
		User user = UserInterface.getCurentUser();
		baseEntity.setCreatorId(UserInterface.getCurentUser().getId());
		baseEntity.setCreatorName(user.getName());
		baseEntity.setCreateOrgId(user.getOrganization().getId());
		baseEntity.setCreateOrgName(user.getOrganization().getName());
		baseEntity.setLastModifiedTime(new Date());
		baseEntity.setCreateTime(new Date());
		baseEntity.setCreateDate(new Date());
	}

	public static void security(StringBuffer queryString, Map filterMap) {
		// 封装数据权限
		if (null != filterMap.get("security_data")) {
			User user = UserInterface.getCurentUser();
			String dataScopeLevelCode = user.getDataScopeLevelCode();
			String orgIds = user.getDataScopeOrgs();
			String property = "";
			if (StrUtils.isNotNullOrBlank(filterMap.get("security_data"))) {
				property = filterMap.get("security_data") + ".";
			}
			if (StrUtils.isNotNullOrBlank(dataScopeLevelCode)
					|| StrUtils.isNotNullOrBlank(orgIds)) {
				orgIds = orgIds.substring(1, orgIds.length() - 1);

				// 单个分公司或者多个分公司
				if ("single_filiale".equals(dataScopeLevelCode)
						|| "mult_filiale".equals(dataScopeLevelCode)) {
					property = property + "filialeId";
				} else {
					property = property + "shopId";
				}
				queryString.append(" and " + property + " in(" + orgIds + ") ");
			}

		}
	}

	public static void security(StringBuffer queryString, String shopName,
			String filialeName) {
		// 封装数据权限
		User user = UserInterface.getCurentUser();
		String dataScopeLevelCode = user.getDataScopeLevelCode();
		String orgIds = user.getDataScopeOrgs();
		String property = "";
		if (StrUtils.isNotNullOrBlank(dataScopeLevelCode)
				|| StrUtils.isNotNullOrBlank(orgIds)) {
			orgIds = orgIds.substring(1, orgIds.length() - 1);

			// 单个分公司或者多个分公司
			if ("single_filiale".equals(dataScopeLevelCode)
					|| "mult_filiale".equals(dataScopeLevelCode)) {
				property = filialeName;
			} else {
				property = shopName;
			}
			queryString.append(" and " + property + " in(" + orgIds + ") ");
		}

	}
}
