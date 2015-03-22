package org.cl.userManager.web;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.cl.common.core.web.BaseAction;
import org.cl.common.util.HttpTool;
import org.cl.common.util.SpringTool;
import org.cl.common.util.StrUtils;
import org.cl.common.widget.pagination.Page;
import org.cl.system.dictionary.entity.Dictionary;
import org.cl.system.dictionary.service.DictionaryService;
import org.cl.system.resource.entity.Menu;
import org.cl.system.resource.entity.Operation;
import org.cl.system.resource.service.MenuService;
import org.cl.system.resource.service.OperationService;
import org.cl.userManager.entity.Role;
import org.cl.userManager.entity.RoleMenu;
import org.cl.userManager.entity.RoleOperation;
import org.cl.userManager.service.RoleService;

/**
 * 角色Action
 * 
 * @author chenl
 * @data 2009-8-10
 */
@Namespace("/userManager")
@Results( {
		@Result(name = "listRole", location = "/WEB-INF/content/userManager/role/listRole.jsp"),
		@Result(name = "addRole", location = "/WEB-INF/content/userManager/role/addRole.jsp"),
		@Result(name = "modifyRole", location = "/WEB-INF/content/userManager/role/modifyRole.jsp"),
		@Result(name = "detailRole", location = "/WEB-INF/content/userManager/role/detailRole.jsp"),
		@Result(name = "showGrantMenu", location = "/WEB-INF/content/userManager/role/showGrantMenu.jsp"),
		@Result(name = "showGrantOperation", location = "/WEB-INF/content/userManager/role/showGrantOperation.jsp") })
public class RoleAction extends BaseAction {

	private RoleService roleService = (RoleService) SpringTool
			.getBean(RoleService.class);

	private MenuService menuService = (MenuService) SpringTool
			.getBean(MenuService.class);

	private OperationService operationService = (OperationService) SpringTool
			.getBean(OperationService.class);

	private Role role = null;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * 显示角色列表
	 */
	public String listRole() throws Exception {
		Map searchMap = super.buildSearch();
		page = new Page(HttpTool.getPageNum());
		roleService.findByPage(page, searchMap);
		return "listRole";
	}

	/**
	 * 显示添加角色页面
	 */
	public String addRole() throws Exception {
		return "addRole";
	}

	/**
	 * 显示添加角色页面
	 */
	public String saveRole() throws Exception {
		roleService.save(role);
		HttpTool.setAttribute("_reLoadTree", true);
		return jump("role!listRole.action");
	}

	/**
	 * 修改角色
	 */
	public String modifyRole() throws Exception {
		role = (Role) roleService.findById(id);
		return "modifyRole";
	}

	/**
	 * 修改角色
	 */
	public String updateRole() throws Exception {
		roleService.update(role);
		return jump("role!listRole.action");
	}

	/**
	 * 删除角色
	 */
	public String deleteRole() throws Exception {
		String ids = HttpTool.getParameter("ids");
		roleService.deleteIds(ids);
		return jump("role!listRole.action");
	}

	/**
	 * 显示菜单赋授权页面
	 */
	public String showGrantMenu() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		Long roleId = HttpTool.getLongParameter("roleId");
		request.setAttribute("roleId", roleId);
		return "showGrantMenu";
	}

	/**
	 * 菜单授权树
	 */
	public String grantMenuTree() throws Exception {
		Long roleId = HttpTool.getLongParameter("roleId");
		// 角色菜单集合
		List roleMenuList = roleService
				.findBy(RoleMenu.class, "roleId", roleId);
		// 根菜单
		Menu rootMenu = menuService.findRootMenu();
		StringBuffer json = new StringBuffer("[{");
		json.append("\"text\":" + "\"" + rootMenu.getName() + "\"" + ","
				+ "\"parentId\":\""
				+ StrUtils.isNullToBank(rootMenu.getParentId()) + "\"" + ","
				+ "\"leaf\":" + rootMenu.getIsLeaf() + "," + "\"expanded\":"
				+ true);
		recursionChildMenuForJson(json, rootMenu, roleMenuList);
		json.append("}]");
		this.jsonString = json.toString();
		return "json";
	}

	/**
	 * 遍历子菜单拼写JSON
	 * 
	 * @param json
	 * @param menu
	 * @param roleMenuList
	 * @param roleOperationList
	 * @throws Exception
	 */
	private void recursionChildMenuForJson(StringBuffer json, Menu menu,
			List<RoleMenu> roleMenuList) throws Exception {
		List<Menu> childMenuList = menuService.findEnableByParentId(menu
				.getId());
		boolean isLeaf = false;
		// 是否含有子菜单,有子菜单，则遍历子菜单
		if (childMenuList.isEmpty()) {
			return;
		}
		json.append(",\"children\":[");
		// 遍历子菜单
		for (int i = 0; i < childMenuList.size(); i++) {
			// 此叶子菜单是否已经被付权限
			boolean hasGrantMenu = false;
			Menu childMenu = childMenuList.get(i);
			isLeaf = childMenu.getIsLeaf() == 0 ? false : true;
			json.append("{\"id\":\"menu" + childMenu.getId() + "\"" + ","
					+ "\"text\":" + "\"" + childMenu.getName() + "\"");
			// 如果是叶子节点，则判断是否已经被赋权，并且判断操作权限赋权情况
			if (isLeaf) {
				// 否已经被赋权
				for (int j = 0; j < roleMenuList.size(); j++) {
					if (roleMenuList.get(j).getMenuId().compareTo(
							childMenu.getId()) == 0) {
						hasGrantMenu = true;
					}
				}
				// 如果被赋权，则checkbox被选上
				if (hasGrantMenu) {
					json.append(",\"checked\":true");
				} else {
					json.append(",\"checked\":false");
				}
			}
			if (isLeaf) {
				json.append(",\"expanded\":false");
				json.append(",\"leaf\":true");
			} else {
				json.append(",\"expanded\":true");
				json.append(",\"leaf\":false");
			}
			recursionChildMenuForJson(json, childMenu, roleMenuList);
			if (i >= 0 && i < childMenuList.size() - 1) {
				json.append("},");
			}
		}
		json.append("}]");
	}

	/**
	 * 给角色付权限
	 */
	public String grantMenu() throws Exception {
		String grantInfo = HttpTool.getParameter("grantInfo");
		Long roleId = HttpTool.getLongParameter("roleId");
		boolean isSuccess = roleService.grantResource(roleId, grantInfo);
		if (isSuccess) {
			this.jsonString = "true";
		}
		return "json";
	}

	/**
	 * 显示菜单赋授权页面
	 */
	public String showGrantOperation() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		Long roleId = HttpTool.getLongParameter("roleId");
		request.setAttribute("roleId", roleId);
		return "showGrantOperation";
	}

	/**
	 * 给角色付权限
	 */
	public String grantOperation() throws Exception {
		String grantInfo = HttpTool.getParameter("grantInfo");
		Long roleId = HttpTool.getLongParameter("roleId");
		boolean isSuccess = roleService.grantOperationResource(roleId,
				grantInfo);
		if (isSuccess) {
			this.jsonString = "true";
		}
		return "json";
	}

	/**
	 * 菜单授权树
	 */
	public String grantOperationTree() throws Exception {
		DictionaryService dictionService = (DictionaryService) SpringTool
				.getBean(DictionaryService.class);
		Long roleId = HttpTool.getLongParameter("roleId");
		// 角色操作集合
		List<RoleOperation> roleOperationList = roleService
				.findRoleOperation(roleId);
		// 操作分类集合
		List<Dictionary> systemModuleList = dictionService
				.findDictionaryByIdentifier("operation_type");
		boolean hasType = systemModuleList.size() != 0 ? true : false;
		StringBuffer json = new StringBuffer("[{");
		json.append("\"text\":" + "\"系统资源(操作)\" " + "," + "\"leaf\":"
				+ !hasType + "," + "\"expanded\":" + true);
		if (!hasType) {
			json.append("}]");
		}
		json.append(",\"children\":[");
		Dictionary systemModule = null;
		for (int i = 0; i < systemModuleList.size(); i++) {
			systemModule = systemModuleList.get(i);
			json.append("{\"id\":\"module" + systemModule.getCode() + "\""
					+ "," + "\"text\":" + "\"" + systemModule.getName() + "\""
					+ "," + "\"expanded\":true");
			boolean hasOperation = operationService
					.hasChildOperation(systemModule.getCode());
			boolean hasTypeGrant = false;
			if (hasOperation) {
				json.append(",\"children\":[");
				// 获取此类别下的操作权限
				List<Operation> operationList = operationService
						.findByType(systemModule.getCode());
				for (int k = 0; k < operationList.size(); k++) {
					boolean hasGrantOperation = false;
					Operation operation = operationList.get(k);
					json.append("{\"id\":\"operation" + operation.getId()
							+ "\"" + "," + "\"text\":" + "\""
							+ operation.getName() + "\"" + ","
							+ "\"leaf\":\"true\"" + "," + "\"iconCls\":"
							+ "\"treeOperationIcon\"");
					// 判断此操作权限是否被赋权
					for (int h = 0; h < roleOperationList.size(); h++) {
						if (roleOperationList.get(h).getOperationId()
								.compareTo(operation.getId()) == 0) {
							hasGrantOperation = true;
							if (!hasTypeGrant) {
								hasTypeGrant = true;
							}
						}
					}
					// 如果被赋权，则checkbox被勾上
					if (hasGrantOperation) {
						json.append(",\"checked\":true");
					} else {
						json.append(",\"checked\":false");
					}
					json.append("}");
					if (k >= 0 && k < operationList.size() - 1) {
						json.append(",");
					}
				}
				json.append("]");
			} else {
				json.append(",\"leaf\":\"true\"");
			}
			if (hasTypeGrant) {
				json.append(",\"checked\":true");
			} else {
				json.append(",\"checked\":false");
			}
			json.append("}");
			if (i >= 0 && i < systemModuleList.size() - 1) {
				json.append(",");
			}

		}
		json.append("]");
		json.append("}]");
		this.jsonString = json.toString();
		return "json";
	}

}
