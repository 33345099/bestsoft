package org.cl.userManager.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.cl.common.core.SystemConstant;
import org.cl.common.core.orm.BaseService;
import org.cl.common.util.ReflectionUtils;
import org.cl.common.util.StrUtils;
import org.cl.system.resource.dao.MenuDao;
import org.cl.system.resource.dao.OperationDao;
import org.cl.system.resource.entity.Menu;
import org.cl.system.resource.entity.Operation;
import org.cl.userManager.dao.RoleDao;
import org.cl.userManager.dao.UserDao;
import org.cl.userManager.entity.Role;
import org.cl.userManager.entity.User;
import org.cl.userManager.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户Service实现类
 * 
 * @author chenl
 * @data Sep 13, 2009
 */
@Service(value = "org.cl.userManager.service.UserService")
@Transactional()
public class UserService extends BaseService {
	@Autowired
	private UserDao userDao = null;
	@Autowired
	private MenuDao menuDao = null;
	@Autowired
	private OperationDao operationDao = null;
	@Autowired
	private RoleDao roleDao = null;

	/**
	 * 赋角色
	 */

	public Boolean grantRole(Long userId, String roleInfos) {
		userDao.deleteGrantRole(userId);
		Long roleId = null;
		UserRole userRole = null;
		if (StrUtils.isNotNullOrBlank(roleInfos)) {
			String[] roleInfoArray = roleInfos.split(",");
			for (int i = 0; i < roleInfoArray.length; i++) {
				roleId = new Long(roleInfoArray[i]);
				userRole = new UserRole();
				userRole.setRoleId(roleId);
				userRole.setUserId(userId);
				userDao.saveOrUpdate(userRole);
			}
		}
		return true;
	}

	/**
	 * 根据用户名加载用户
	 */

	public User loadUserByUsername(String username) {
		return userDao.loadUserByUsername(username);
	}

	/**
	 * 判断用户名是否唯一
	 */

	public Boolean isUniqueUserByUsername(String username, Boolean isUpdate,
			Long id) {
		return userDao.loadUserByUsername(username, isUpdate, id);
	}

	/**
	 * 根据用户工种和所属店面加载用户
	 */

	public List<User> loadUserByUserDuty(String userDuty, Long organizationId) {
		return userDao.loadUserByUserDuty(userDuty, organizationId);
	}

	/**
	 * 组装合法登录用户
	 */

	public User loadSecurityUser(User user) {
		// 获取用户可以访问的菜单和资源
		List<Menu> menuList = null;
		List<Operation> operationList = null;
		// 超级管理员可以访问所有的资源
		if ("0".equals(user.getType())) {
			menuList = menuDao.findAllEnable();
			operationList = operationDao.findAll();
		} else {
			menuList = menuDao.findAllEnableMenuByUserIdForJdbc(user.getId());
			operationList = operationDao.findAllOperationByUserIdForJdbc(user
					.getId());
		}
		user.setMenus(menuList);
		user.setOperations(operationList);
		// 获取用户可以访问的模块
		List moduleMenuList = new ArrayList();
		for (int i = 0; i < menuList.size(); i++) {
			if (null != menuList.get(i).getType()
					&& menuList.get(i).getType().equals("2")) {
				moduleMenuList.add(menuList.get(i));
			}
		}
		user.setModuleMenus(moduleMenuList);
		// 获取用户拥有的角色
		List<Role> roleList = roleDao.findByUserId(user.getId());
		user.setRoles(roleList);
		return user;
	}

	/**
	 * 删除用户
	 */
	public void deleteIds(String ids) {
		String[] idArray = ids.split(",");
		for (int i = 0; i < idArray.length; i++) {
			User user = (User) userDao.findById(User.class,
					new Long(idArray[i]));
			user.setIsDelete(1);
		}
	}

	/**
	 * 修改菜单状态
	 */
	public boolean updateEnable(Long userId) {
		User user = (User) userDao.findById(User.class, userId);
		if (user.isEnable == 0) {
			user.setIsEnable(1);
			return true;
		} else {
			user.setIsEnable(0);
			return false;
		}
	}

	/**
	 * 修改菜单状态
	 */
	public boolean updateDimission(Long userId) {
		User user = (User) userDao.findById(User.class, userId);
		if (user.isEnable == 0) {
			user.setIsDimission(0);
			user.setIsEnable(1);
			return false;
		} else {
			user.setIsDimission(1);
			user.setIsEnable(0);
			return true;
		}
	}

	/**
	 * 修改用户
	 */

	public void update(User user, String[] roles) {
		User oldUser = (User) userDao.findById(User.class, user.getId());
		ReflectionUtils.copyPropertiesForHasValue(oldUser, user);
		userDao.saveOrUpdate(oldUser);
		if (null != roles) {
			userDao.deleteGrantRole(user.getId());
			for (String roleId : roles) {
				UserRole userRole = new UserRole();
				userRole.setRoleId(new Long(roleId));
				userRole.setUserId(user.getId());
				userDao.saveOrUpdate(userRole);
			}
		}
		assembleUser(user.getId());
	}

	/**
	 * 修改密码
	 */

	public void updatePassword(String password, Long userId) {
		userDao.updatePassword(password, userId);
	}

	/**
	 * 初始化系统管理员
	 * 
	 * @param rootOrganizationId
	 */
	public void initAdministrator(Long rootOrganizationId) {
		boolean flag = userDao.hasAdministrator();
		if (!flag) {
			User user = new User();
			user.setUsername(SystemConstant
					.getSystemConstant("administrator_name"));
			user.setPassword(SystemConstant
					.getSystemConstant("administrator_password"));
			user.setIsDimission(0);
			user.setType("0");
			user.setIsDelete(0);
			user.setName("系统管理员");
			user.setOrganizationId(rootOrganizationId);
			userDao.saveOrUpdate(user);
		}
	}

	/**
	 * 保存用户
	 * 
	 * @param user
	 * @param userGroup
	 */
	public void save(User user, String[] roles) {
		user.setIsDimission(0);
		user.setIsDelete(0);
		userDao.saveOrUpdate(user);
		if (null != roles) {
			for (String roleId : roles) {
				UserRole userRole = new UserRole();
				userRole.setRoleId(new Long(roleId));
				userRole.setUserId(user.getId());
				userDao.saveOrUpdate(userRole);
			}
		}
		assembleUser(user.getId());
	}

	/**
	 * 根据用户名获取组ID集合
	 * 
	 * @param userId
	 * @return
	 */
	private void assembleUser(Long userId) {
		User user = (User) userDao.findById(User.class, userId);
		String roleNames = StrUtils.collectionToStr(userDao
				.findRoleNameByUserId(user.getId()), ",", false);
		user.setRoleNames(roleNames);
		userDao.saveOrUpdate(user);
	}

	/**
	 * 根据用户Id获取角色ID集合
	 * 
	 * @param userId
	 * @return
	 */
	public Collection findRoleIdByUserId(Long userId) {
		return userDao.findRoleIdByUserId(userId);
	}

	/**
	 * 根据用户Id获取角色名称集合
	 * 
	 * @param userId
	 * @return
	 */
	public Collection findRoleNameByUserId(Long userId) {
		return userDao.findRoleNameByUserId(userId);
	}

	/**
	 * 根据职位和部门获取用户集合
	 * 
	 * @param filialeId
	 * @param duty
	 * @return
	 */
	public List getByOrgAndDuty(Long orgId, String duty) {
		return userDao.getByOrgAndDuty(orgId, duty);
	}

	/**
	 * 根据分公司ID和职务获取用户集合
	 * 
	 * @param orgId
	 * @param duty
	 * @return
	 */
	public List getByFilialeAndDuty(Long filialeId, String duty) {
		return userDao.getByFilialeAndDuty(filialeId, duty);
	}

	public List<Long> findUserIdByOrgId(Long orgId) {
		return userDao.findUserIdByOrgId(orgId);
	}

	/**
	 * 根据机构ID获取用户
	 * 
	 * @param parentId
	 * @return
	 */
	public List<User> findUserByOrgId(Long orgId) {
		return userDao.findUserByOrgId(orgId);
	}

	/**
	 * 判断机构下面是否含有用户
	 * 
	 * @param orgId
	 * @return
	 */
	public Long hasUserByOrgId(Long orgId) {
		return userDao.hasUserByOrgId(orgId);
	}

	/**
	 * 根据分公司获取用户集合
	 * 
	 * @param filialeId
	 * @param duty
	 * @return
	 */
	public List getByFiliale(Long filialeId) {
		return userDao.getByFiliale(filialeId);
	}

	public List<User> getByRole(Role role){
		return userDao.getByRole(role);
	}
}
