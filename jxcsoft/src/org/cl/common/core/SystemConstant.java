package org.cl.common.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.springframework.util.Assert;

/**
 * 基础系统静态常量类
 * 
 * @author chenl
 * @Apr 26, 2010
 */
public class SystemConstant {
	public static final String TEMP_DIR = "tmp/";

	public static final String DESIGNER_IMAGE_DIR = "design/";

	public static Properties CONSTANTS = null;// 系统变量存放区

	public static Properties logProperties = new Properties();// 日志Key集合

	public static Long ROOT_MENU_ID = null;// 资源根节点ID
	
	/**
	 * 数据字典 回单类型-----非回单
	 */
	public static Long D_CUSTOMER_BACKTYPE_FFD = 4000000033001l;
	
	// ============================================================================================================================

	/**
	 * 数据字典客户资料范围不授权
	 */
	public static String D_CUSTOMER_DATA_SCOPE_NONE_ID = "3000000004001";

	/**
	 * 数据字典客户资料范围单个分公司ID
	 */
	public static String D_CUSTOMER_DATA_SCOPE_SINGLE_FILIALE_ID = "3000000004002";

	/**
	 * 数据字典客户资料范围多个分公司ID
	 */
	public static String D_CUSTOMER_DATA_SCOPE_MULT_FILIALE_ID = "3000000004003";

	/**
	 * 数据字典客户资料范围单个店面ID
	 */
	public static String D_CUSTOMER_DATA_SCOPE_SINGLE_SHOP_ID = "3000000004004";

	/**
	 * 数据字典客户资料范围某一分公司多个店面
	 */
	public static String D_CUSTOMER_DATA_SCOPE_SINGLE_FILIALE_MULT_SHOP_ID = "3000000004005";

	/**
	 * 数据字典客户资料范围多个分公司多个店面
	 */
	public static String D_CUSTOMER_DATA_SCOPE_MULT_FILIALE_MULT_SHOP_ID = "3000000004006";

	/**
	 * 获取系统变量
	 * 
	 * @param constantName
	 *            属性名称
	 * @return
	 */
	public static String getSystemConstant(String constantName) {
		String property = null;
		if (null == CONSTANTS) {
			initSystemProperties();
		}
		property = CONSTANTS.getProperty(constantName);
		Assert.notNull(property, "系统变量" + constantName + "不存在");
		return property;
	}

	/**
	 * 初始化系统属性文件
	 */
	private static void initSystemProperties() {
		CONSTANTS = new Properties();
		ClassLoader cl = SystemConstant.class.getClassLoader();
		// 加载数据字典分类初始化文件
		InputStream stream = cl.getResourceAsStream("system.properties");
		try {
			CONSTANTS.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != stream) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
