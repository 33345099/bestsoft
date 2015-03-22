package org.cl.common.core.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.cl.common.core.SystemConstant;
import org.cl.common.util.SpringTool;
import org.cl.system.dictionary.service.DictionarySortService;
import org.cl.system.log.LogTool;
import org.cl.system.resource.service.MenuService;
import org.cl.system.resource.service.OperationService;
import org.cl.system.resource.service.SequenceService;
import org.cl.userManager.service.OrganizationService;
import org.cl.userManager.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统启动Servlet
 * 
 * @author chenl
 * @Apr 15, 2009
 */
public class SystemServlet extends HttpServlet {

	protected final Logger logger = LoggerFactory.getLogger("系统初始化");

	/**
	 * 对系统进行初始化
	 */
	public void init() throws ServletException {
		logger.info("系统初始化开始");
		SpringTool.initInstance(this.getServletContext());
		if ("true".equals(SystemConstant
				.getSystemConstant("system_init_dictionary"))) {
			logger.info("初始化数据字典================================");
			((DictionarySortService) SpringTool
					.getBean(DictionarySortService.class)).initDictionarySort();
		}
		if ("true".equals(SystemConstant
				.getSystemConstant("system_init_resource"))) {
			logger.info("初始化操作================================");
			((OperationService) SpringTool.getBean(OperationService.class))
					.initOperation();
			logger.info("初始化系统菜单================================");
			((MenuService) SpringTool.getBean(MenuService.class)).initMenu();
		}
		logger.info("初始化日志配置文件=============================");
		LogTool.initLog();
		logger.info("初始化基本数据=============================");
		initPlatformData();
		setStaticDataConstant();
		logger.info("系统初始化结束");
	}

	/**
	 * 初始化平台数据
	 */
	private void initPlatformData() {
		// 初始化根节点
		OrganizationService organizationService = (OrganizationService) SpringTool
				.getBean(OrganizationService.class);
		organizationService.initRootOrganization();
		// 初始化超级管理员
		UserService userService = (UserService) SpringTool
				.getBean(UserService.class);
		userService.initAdministrator(organizationService
				.findRootOrganization().getId());
		// 初始化系统流水序号
		SequenceService sequenceService = (SequenceService) SpringTool
				.getBean(SequenceService.class);
		sequenceService.initSequence();
	}

	/**
	 * 设置静态基本数据
	 */
	private void setStaticDataConstant() {
		SystemConstant.ROOT_MENU_ID = ((MenuService) SpringTool
				.getBean(MenuService.class)).findRootMenu().getId();
	}
}
