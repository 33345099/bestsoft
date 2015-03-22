package org.cl.userManager.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cl.common.core.orm.BaseService;
import org.cl.common.util.StrUtils;
import org.cl.system.resource.dao.MenuDao;
import org.cl.system.resource.entity.Menu;
import org.cl.userManager.dao.RoleDao;
import org.cl.userManager.entity.RoleMenu;
import org.cl.userManager.entity.RoleOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色Service
 * 
 * @author chenl
 * @data Sep 10, 2009
 */
@Service(value = "org.cl.userManager.service.RoleService")
@Transactional()
public class RoleService extends BaseService {

	@Autowired
	private RoleDao roleDao = null;
	@Autowired
	private MenuDao menuDao = null;

	/**
	 * 给角色赋予资源
	 */

	public boolean grantResource(Long roleId, String grantInfos) {
		Menu rootMenu = menuDao.findRootMenu();
		// 根据角色ID删除之前赋予的角色
		roleDao.deleteRoleGrantByRoleId(roleId);
		Long menuId = null;
		Long operationId = null;
		RoleMenu roleMenu = null;
		RoleOperation roleOperation = null;
		if (StrUtils.isNullOrBlank(grantInfos)) {
			return true;
		}
		String[] grantInfoArray = grantInfos.split(",");
		Map grantMenuMap = new HashMap();
		for (int i = 0; i < grantInfoArray.length; i++) {
			if (grantInfoArray[i].indexOf("menu") >= 0) {
				menuId = new Long(grantInfoArray[i].substring(4));
				grantMenuMap.put(menuId.toString(), menuId);
				// 添加其相应的父节点
				Long parentId = menuId;
				while (true) {
					parentId = menuDao.findParentIdByIdForJdbc(parentId);
					if (parentId.compareTo(rootMenu.getId()) != 0) {
						if (null == grantMenuMap.get(parentId.toString())) {
							grantMenuMap.put(parentId.toString(), parentId);
						} else {
							break;
						}
					} else {
						break;
					}
				}
			} else {
				operationId = new Long(grantInfoArray[i].substring(9));
				roleOperation = new RoleOperation();
				roleOperation.setOperationId(operationId);
				roleOperation.setRoleId(roleId);
				roleDao.saveOrUpdate(roleOperation);
			}
		}
		for (Object o : grantMenuMap.keySet()) {
			menuId = (Long) grantMenuMap.get(o);
			roleMenu = new RoleMenu();
			roleMenu.setMenuId(menuId);
			roleMenu.setRoleId(roleId);
			roleDao.saveOrUpdate(roleMenu);
		}
		return true;
	}

	/**
	 * 操作授权
	 */

	public boolean grantOperationResource(Long roleId, String grantInfos) {
		// 根据角色ID删除之前赋予的角色
		roleDao.deleteRoleGrantOperationByRoleId(roleId);
		Long operationId = null;
		RoleOperation roleOperation = null;
		if (StrUtils.isNullOrBlank(grantInfos)) {
			return true;
		}
		String[] grantInfoArray = grantInfos.split(",");
		for (int i = 0; i < grantInfoArray.length; i++) {
			if (grantInfoArray[i].startsWith("operation")) {
				operationId = new Long(grantInfoArray[i].substring(9));
				roleOperation = new RoleOperation();
				roleOperation.setOperationId(operationId);
				roleOperation.setRoleId(roleId);
				roleDao.saveOrUpdate(roleOperation);
			}
		}
		return true;
	}

	/**
	 * 获取角色所拥有的操作权限
	 */

	public List<RoleOperation> findRoleOperation(Long roleId) {
		return roleDao.findRoleOperation(roleId);
	}

	/**
	 * 根据用户ID获取角色名称
	 */

	public List<String> findNameByUserId(Long userId) {
		return roleDao.findNameByUserIdForJdbc(userId);
	}

	/**
	 * 删除角色
	 */

	public void deleteIds(String ids) {
		String[] idArray = ids.split(",");
		for (int i = 0; i < idArray.length; i++) {
			roleDao.delete(new Long(idArray[i]));
		}
	}
}
