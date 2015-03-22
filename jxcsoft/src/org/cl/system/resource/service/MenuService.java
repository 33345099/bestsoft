package org.cl.system.resource.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cl.common.core.orm.BaseService;
import org.cl.common.util.XmlDom4j;
import org.cl.system.resource.dao.MenuDao;
import org.cl.system.resource.entity.Menu;
import org.cl.system.resource.service.MenuService;
import org.dom4j.Document;
import org.dom4j.Element;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 菜单Service实现类
 * 
 * @author chenl
 * @data May 10, 2009
 */
@Service(value = "org.cl.system.resource.service.MenuService")
@Transactional()
public class MenuService extends BaseService {
	private static final Log log = LogFactory.getLog(MenuService.class);
	@Autowired
	private MenuDao menuDao = null;

	/**
	 * 获取根菜单
	 */
	public Menu findRootMenu() {
		return menuDao.findRootMenu();
	}

	/**
	 * 根据父ID获取可用的菜单
	 */
	public List<Menu> findEnableByParentId(Long parentId) {
		return menuDao.findEnableByParentId(parentId);
	}

	private void initMenuForRecursion(Element element, Long parentId,
			Integer orderCode) {
		List<Element> childList = element.selectNodes("menu");
		Menu menu = new Menu();
		if (childList.size() > 0) {
			menu.setIsLeaf(0);
		} else {
			menu.setIsLeaf(1);
		}
		menu.setName(element.attributeValue("name"));
		if (null != element.attributeValue("isEnable")) {
			menu.setIsEnable(new Integer(element.attributeValue("isEnable")));
		} else {
			menu.setIsEnable(1);
		}
		menu.setOrderCode(orderCode);
		if (null != element.attributeValue("type")) {
			menu.setType(element.attributeValue("type"));
		}
		if (null != element.attributeValue("url")) {
			menu.setUrl(element.attributeValue("url"));
		}
		menu.setParentId(parentId);
		this.save(menu);
		for (int i = 0; i < childList.size(); i++) {
			element = childList.get(i);
			initMenuForRecursion(element, menu.getId(), i);
		}
	}

	/**
	 * 初始化系统菜单
	 */

	public void initMenu() {
		log.info("重新根据配置文件dictionary.xml文件生成数据库中的数据字典");
		menuDao.deleteAllMenu();
		XmlDom4j xmlDom4j = new XmlDom4j();
		// 加载数据字典文件
		Document document = xmlDom4j.loadFile("initData/menu.xml");
		Element rootElement = xmlDom4j.getRootElement(document);
		initMenuForRecursion(rootElement, null, 1);
	}
}
