package org.cl.userManager.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.cl.common.widget.tree.CheckTreeEntity;
import org.cl.common.widget.tree.TreeEntity;
import org.cl.interfaces.UserInterface;
import org.cl.system.security.context.ContextHolder;
import org.cl.userManager.entity.Organization;
import org.cl.userManager.entity.Role;
import org.cl.userManager.entity.User;
import org.cl.userManager.entity.UserRole;
import org.cl.userManager.service.OrganizationService;
import org.cl.userManager.service.RoleService;
import org.cl.userManager.service.UserService;

/**
 * 用户Action
 * 
 * @author chenl
 * @data 2009-8-9
 */
@Namespace("/userManager")
@Results( {
		@Result(name = "indexUser", location = "/WEB-INF/content/userManager/user/indexUser.jsp"),
		@Result(name = "listUser", location = "/WEB-INF/content/userManager/user/listUser.jsp"),
		@Result(name = "listOnline", location = "/WEB-INF/content/userManager/user/listOnline.jsp"),
		@Result(name = "addUser", location = "/WEB-INF/content/userManager/user/addUser.jsp"),
		@Result(name = "modifyUser", location = "/WEB-INF/content/userManager/user/modifyUser.jsp"),
		@Result(name = "showGrantRole", location = "/WEB-INF/content/userManager/user/showGrantRole.jsp"),
		@Result(name = "showSelectUser", location = "/WEB-INF/content/userManager/user/showSelectUser.jsp"),
		@Result(name = "portalPersonInfo", location = "/WEB-INF/content/userManager/user/portalPersonInfo.jsp"),
		@Result(name = "showChangeOrg", location = "/WEB-INF/content/userManager/user/showChangeOrg.jsp") })
public class UserAction extends BaseAction {

	private UserService userService = (UserService) SpringTool
			.getBean(UserService.class);

	private RoleService roleService = (RoleService) SpringTool
			.getBean(RoleService.class);

	private OrganizationService organizationService = (OrganizationService) SpringTool
			.getBean(OrganizationService.class);

	private User user = null;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private Map<String, Date> onlineUsers;

	public Map<String, Date> getOnlineUsers() {
		return onlineUsers;
	}

	public void setOnlineUsers(Map<String, Date> onlineUsers) {
		this.onlineUsers = onlineUsers;
	}

	/**
	 * 显示角色列表
	 */
	public String indexUser() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		Organization rootOrganization = organizationService
				.findRootOrganization();
		request.setAttribute("rootOrganization", rootOrganization);
		return "indexUser";
	}

	public String portalPersonInfo() throws Exception {
		user = UserInterface.getCurentUser();
		return "portalPersonInfo";
	}

	/**
	 * 显示用户列表
	 */
	public String listUser() throws Exception {
		Map searchMap = super.buildSearch();
		page = new Page(HttpTool.getPageNum());
		super.transferBuildSearch(searchMap, "and", "EQ", "L",
				"transfer_organizationId", "organizationId");
		searchMap.put("filter_and_isDelete_EQ_I", 0);
		List<User> list = userService.findByPage(page, searchMap);
		for (int i = 0; i < list.size(); i++) {
			List<String> roleList = roleService.findNameByUserId(list.get(i)
					.getId());
			list.get(i).setRoleStrs(roleList);
		}
		return "listUser";
	}

	/**
	 * 取得当前在线的所有用户
	 */
	public String listOnline(){
		ContextHolder contextHolder = SpringTool.getSpringBean(ContextHolder.class);
		this.onlineUsers = contextHolder.getUserList();
		return "listOnline";
	}

	public String addUser() throws Exception {
		Long organizationId = HttpTool
				.getLongParameter("transfer_organizationId");
		Organization organization = (Organization) organizationService
				.findById(organizationId);
		HttpTool.setAttribute("organization", organization);
		HttpTool.setAttribute("roleList", roleService.findAll());
		return "addUser";
	}

	public String saveUser() throws Exception {
		user.setType("1");
		String[] roles = ServletActionContext.getRequest().getParameterValues(
				"roles");
		String stylistLevels = StrUtils.collectionToStr(user
				.getStylistLevelList(), ",", true);
		user.setStylistLevels(stylistLevels);
		String dataScopeOrgs = StrUtils.collectionToStr(user
				.getDataScopeOrgList(), ",", true);
		user.setDataScopeOrgs(dataScopeOrgs);
		Organization organization = (Organization) organizationService
				.findById(user.getOrganizationId());
		user.setFilialeId(organization.getFilialeId());
		if (null == user.getIsLookCustomerContract()) {
			user.setIsLookCustomerContract(0);
		}
		userService.save(user, roles);
		return jump("user!listUser.action");
	}

	/**
	 * 显示修改用户页面
	 */
	public String modifyUser() throws Exception {
		List organizationList = organizationService.findAll();// 获取根节点机构
		user = (User) userService.findById(id);
		HttpTool.setAttribute("roleList", roleService.findAll());
		String roleIds = StrUtils.collectionToStr(userService
				.findRoleIdByUserId(user.getId()), ",", false);
		HttpTool.setAttribute("roleIds", roleIds);
		HttpTool.setAttribute("organizationList", organizationList);
		return "modifyUser";
	}

	/**
	 * 修改用户
	 */
	public String updateUser() throws Exception {
		String[] roles = ServletActionContext.getRequest().getParameterValues(
				"roles");
		String stylistLevels = StrUtils.collectionToStr(user
				.getStylistLevelList(), ",", true);
		user.setStylistLevels(stylistLevels);
		String dataScopeOrgs = StrUtils.collectionToStr(user
				.getDataScopeOrgList(), ",", true);
		user.setDataScopeOrgs(dataScopeOrgs);
		userService.update(user, roles);
		return jump("user!listUser.action");
	}

	/**
	 * 删除用户
	 */
	public String deleteUser() throws Exception {
		String ids = HttpTool.getParameter("ids");
		userService.deleteIds(ids);
		return jump("user!listUser.action");
	}

	/**
	 * 给用户付角色
	 */
	public String showGrantRole() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		Long userId = HttpTool.getLongParameter("userId");
		request.setAttribute("userId", userId);
		return "showGrantRole";
	}

	/**
	 * 用户角色树
	 */
	public String grantRoleTree() throws Exception {
		Long userId = HttpTool.getLongParameter("userId");
		List<Role> allRoleList = roleService.findAll();
		List<UserRole> userRoleList = userService.findBy(UserRole.class,
				"userId", userId);
		Role role = null;
		StringBuffer json = new StringBuffer("[");
		for (int i = 0; i < allRoleList.size(); i++) {
			boolean hasGrant = false;
			role = allRoleList.get(i);
			json.append("{\"id\":\"" + role.getId() + "\"" + "," + "\"text\":"
					+ "\"" + role.getName() + "\"" + "," + "\"leaf\":\"true\"");
			for (int j = 0; j < userRoleList.size(); j++) {
				if (userRoleList.get(j).getRoleId().compareTo(role.getId()) == 0) {
					hasGrant = true;
				}
			}
			if (hasGrant) {
				json.append(",\"checked\":true");
			} else {
				json.append(",\"checked\":false");
			}
			if (i >= 0 && i < allRoleList.size() - 1) {
				json.append("},");
			} else {
				json.append("}");
			}
		}
		json.append("]");
		this.jsonString = json.toString();
		return "json";
	}

	/**
	 * 给角色付权限
	 */
	public String grantRole() throws Exception {
		String roleInfo = HttpTool.getParameter("roleInfo");
		Long userId = HttpTool.getLongParameter("userId");
		boolean isSuccess = userService.grantRole(userId, roleInfo);
		if (isSuccess) {
			this.jsonString = "true";
		}
		return "json";
	}

	/**
	 * 根据父ID显示员工树
	 */
	public String treeOrganization() throws Exception {
		Map<String, Object> propertyMap = new HashMap<String, Object>();
		propertyMap.put("parentId", HttpTool.getLongParameter("parentId"));
		propertyMap.put("isDelete", 0);
		List<Organization> list = organizationService.findByMap(
				Organization.class, propertyMap);
		List jsonList = new ArrayList();
		Organization organization = null;
		for (int i = 0; i < list.size(); i++) {
			organization = list.get(i);
			TreeEntity tree = new TreeEntity(organization.getId().toString(),
					organization.getName(), organization.getIsLeaf(), null);
			jsonList.add(tree);
		}
		return json(jsonList);
	}

	/**
	 * 显示选择用户
	 */
	public String showSelectUser() throws Exception {
		HttpTool.setAttribute("userNameTempTag", HttpTool
				.getParameter("userNameTempTag"));
		HttpTool.setAttribute("userNameTag", HttpTool
				.getParameter("userNameTag"));
		HttpTool.setAttribute("userIdTag", HttpTool.getParameter("userIdTag"));
		return "showSelectUser";
	}

	/**
	 * 获取组织机构的tree json
	 */
	public String orgJson() throws Exception {
		List jsonList = new ArrayList();
		Long parentId = null;
		if (HttpTool.getParameter("parentId").indexOf("org") >= 0) {
			parentId = new Long(HttpTool.getParameter("parentId").split("-")[1]);
		}
		List<Organization> orgList = new ArrayList();
		orgList = organizationService.findByParentId(parentId);
		List<User> userList = userService.findUserByOrgId(parentId);
		Organization organization = null;
		User user = null;
		for (int i = 0; i < orgList.size(); i++) {
			organization = orgList.get(i);
			TreeEntity tree = new CheckTreeEntity("org-"
					+ organization.getId().toString(), organization.getName(),
					1, null);
			// 判断机构下面是否含有用户
			Long hasUserCount = userService
					.hasUserByOrgId(organization.getId());
			if (hasUserCount > 0 || organization.getIsLeaf() == 0) {
				tree.setLeaf(false);
			} else {
				tree.setLeaf(true);
			}
			if (null == parentId) {
				tree.setExpanded(true);
			}
			jsonList.add(tree);
		}
		for (int i = 0; i < userList.size(); i++) {
			user = userList.get(i);
			TreeEntity tree = new CheckTreeEntity("user-"
					+ user.getId().toString(), user.getName() + "(用户)", 1, null);
			jsonList.add(tree);
		}
		return super.json(jsonList);
	}

	/**
	 * 显示选择用户
	 */
	public String showChangeOrg() throws Exception {
		user = (User) userService.findById(id);
		return "showChangeOrg";
	}

	/**
	 * 显示选择用户
	 */
	public String changeOrg() throws Exception {
		Long orgId = HttpTool.getLongParameter("orgId");
		Long userId = HttpTool.getLongParameter("userId");
		User user = (User) userService.findById(userId);
		Organization org = (Organization) organizationService.findById(orgId);
		user.setOrganizationId(orgId);
		user.setFilialeId(org.getId());
		userService.update(user);
		this.jsonString = "true";
		return "json";
	}

	/**
	 * 显示机构树
	 */
	public String changeOrgTree() throws Exception {
		// 根菜单
		Organization root = organizationService.findRootOrganization();
		StringBuffer json = new StringBuffer("[{");
		json.append("\"text\":" + "\"" + root.getName() + "\"" + ","
				+ "\"parentId\":\"" + StrUtils.isNullToBank(root.getParentId())
				+ "\"" + "," + "\"leaf\":" + root.getIsLeaf() + ","
				+ "\"expanded\":" + true);
		recursionChildOrgForJson(json, root);
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
	private void recursionChildOrgForJson(StringBuffer json, Organization org)
			throws Exception {
		List<Organization> childOrgList = organizationService
				.findByParentId(org.getId());
		boolean isLeaf = false;
		if (childOrgList.isEmpty()) {
			return;
		}
		json.append(",\"children\":[");
		for (int i = 0; i < childOrgList.size(); i++) {
			Organization childOrg = childOrgList.get(i);
			isLeaf = childOrg.getIsLeaf() == 0 ? false : true;
			json.append("{\"id\":\"" + childOrg.getId() + "\"" + ","
					+ "\"text\":" + "\"" + childOrg.getName() + "\"");
			// 如果是叶子节点，则判断是否已经被赋权，并且判断操作权限赋权情况
			json.append(",\"checked\":false");
			if (isLeaf) {
				json.append(",\"expanded\":false");
				json.append(",\"leaf\":true");
			} else {
				json.append(",\"expanded\":true");
				json.append(",\"leaf\":false");
			}
			recursionChildOrgForJson(json, childOrg);
			if (i >= 0 && i < childOrgList.size() - 1) {
				json.append("},");
			}
		}
		json.append("}]");
	}
}
