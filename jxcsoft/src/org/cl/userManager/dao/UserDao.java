package org.cl.userManager.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cl.common.core.orm.BaseDao;
import org.cl.common.util.StrUtils;
import org.cl.system.resource.entity.Menu;
import org.cl.userManager.entity.Role;
import org.cl.userManager.entity.User;
import org.cl.userManager.entity.UserRole;
import org.springframework.stereotype.Repository;

@Repository()
public class UserDao extends BaseDao {

	/**
	 * 删除用户所有的角色
	 * 
	 * @param userId
	 */
	public void deleteGrantRole(Long userId) {
		this.getHibernateTemplate().bulkUpdate(
				"delete from UserRole where userId=?", userId);
	}

	/**
	 * 根据用户名加载用户
	 * 
	 * @param username
	 * @return
	 */
	public User loadUserByUsername(String username) {
		String queryString = " from User u where isDelete=0 and u.username=?";
		List<User> list = this.getHibernateTemplate().find(queryString,
				username);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 判断用户名是否唯一
	 * 
	 * @param username
	 * @return
	 */
	public Boolean loadUserByUsername(String username, Boolean isAdd, Long id) {
		String queryString = " from User u where isDelete=0 and u.username='"
				+ username + "'";
		if (!isAdd) {
			queryString += " and u.id!=" + id;
		}
		List<User> list = this.getHibernateTemplate().find(queryString);
		if (list.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 根据用户工种和所属店面加载用户
	 * 
	 * @param username
	 * @return
	 */
	public List<User> loadUserByUserDuty(String userDuty, Long organizationId) {
		String queryString = " from User u where isDelete=0 and u.dutyCode=? and organizationId=?";
		List<User> list = this.getHibernateTemplate().find(queryString,
				new Object[] { userDuty, organizationId });
		if (list.isEmpty()) {
			return null;
		}
		return list;
	}

	/**
	 * 根据用户ID和模块ID获取菜单集合
	 * 
	 * @param parentId
	 * @param userId
	 * @return
	 */
	public List<Menu> findMenuByParentIdAndUserIdForJdbc(Long parentId,
			Long userId) {
		List list = new ArrayList();
		String queryString = " select m.id,m.name ,m.url from sys_menu m,um_user_role ur,um_role_menu rm "
				+ " where m.id=rm.menu_id and rm.role_id=ur.role_id and m.is_enable!=0 and m.parent_id="
				+ parentId + " and ur.user_id=? order by m.order_code";
		List resultList = this.getJdbcTemplate().queryForList(queryString,
				new Object[] { userId });
		Map map = null;
		for (int i = 0; i < resultList.size(); i++) {
			Menu menu = new Menu();
			map = (Map) resultList.get(i);
			menu.setId(StrUtils.toLong(map.get("id")));
			menu.setName(StrUtils.ObjectToString(map.get("name")));
			menu.setUrl(StrUtils.ObjectToString(map.get("url")));
			list.add(menu);
		}
		return list;
	}

	/**
	 * 删除用户
	 */
	public void delete(Long id) {
		this.getJdbcTemplate().update(
				"delete from um_user_role where user_id=" + id);
		this.getJdbcTemplate().update("delete from um_user where id=" + id);
	}

	public void updatePassword(Long userId, String password) {
		this.getHibernateTemplate().bulkUpdate(
				" update User set password=? where id=?",
				new Object[] { password, userId });
	}

	public void updatePassword(String password, Long userId) {
		this.getHibernateTemplate().bulkUpdate(
				" update User set password=? where id=?",
				new Object[] { password, userId });
	}

	/**
	 * 判断是否含有超级管理员
	 * 
	 * @return
	 */
	public boolean hasAdministrator() {
		String queryString = "select count(*) from um_user where is_delete=0 and type='0'";
		Long count = this.getJdbcTemplate().queryForLong(queryString);
		if (count > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 根据用户类型获取用户
	 * 
	 * @param string
	 * @return
	 */
	public List<Long> findUserIdByType(String type) {
		String queryString = null;
		List list = null;
		if (null != type) {
			queryString = " select id from User  where isDelete=0 and type =?";
			list = super.getHibernateTemplate().find(queryString, type);
		} else {
			queryString = "select id   from User o where isDelete=0";
			list = super.getHibernateTemplate().find(queryString);
		}
		return list;
	}

	/**
	 * 根据用户Id获取角色ID集合
	 * 
	 * @param userId
	 * @return
	 */
	public Collection findRoleIdByUserId(Long userId) {
		return this.getHibernateTemplate().find(
				"select roleId from UserRole where userId=?", userId);
	}

	/**
	 * 根据用户Id获取角色名称集合
	 * 
	 * @param userId
	 * @return
	 */
	public Collection findRoleNameByUserId(Long userId) {
		return this.getHibernateTemplate().find(
				"select role.name from UserRole where userId=?", userId);
	}

	/**
	 * 根据职位和部门获取用户集合
	 * 
	 * @param filialeId
	 * @param duty
	 * @return
	 */
	public List getByOrgAndDuty(Long orgId, String duty) {
		List list = this
				.getHibernateTemplate()
				.find(
						" from User where isDelete=0 and organizationId=? and dutyCode=? and isDimission=0",
						new Object[] { orgId, duty });
		return list;
	}

	/**
	 * 根据职位和分公司获取用户集合
	 * 
	 * @param filialeId
	 * @param duty
	 * @return
	 */
	public List getByFilialeAndDuty(Long filialeId, String duty) {
		return this
				.getHibernateTemplate()
				.find(
						" from User where isDelete=0 and filialeId=? and dutyCode=? and isDimission=0",
						new Object[] { filialeId, duty });
	}

	/**
	 * 根据分公司获取用户集合
	 * 
	 * @param filialeId
	 * @return
	 */
	public List getByFiliale(Long filialeId) {
		return this
				.getHibernateTemplate()
				.find(
						"select id from User where isDelete=0 and filialeId=? and isDimission=0",
						new Object[] { filialeId });
	}

	/**
	 * 获取该部门下的用户
	 * 
	 * @param orgId
	 * @return
	 */
	public List findUserIdByOrgId(Long organizationId) {
		List list = this.getHibernateTemplate().find(
				"select id from User where isDelete=0 and organizationId=?",
				organizationId);

		return list;
	}

	/**
	 * 根据机构ID获取用户
	 * 
	 * @param orgId
	 * @return
	 */
	public List<User> findUserByOrgId(Long orgId) {
		return this.getHibernateTemplate().find(
				" from User where isDelete=0 and organizationId=?", orgId);
	}

	/**
	 * 判断机构下面是否含有用户
	 * 
	 * @param orgId
	 * @return
	 */
	public Long hasUserByOrgId(Long orgId) {
		List list = this
				.getHibernateTemplate()
				.find(
						"select count(*) from User where isDelete=0 and organizationId=?",
						orgId);
		if (list.isEmpty()) {
			return 0l;
		} else {
			return (Long) list.get(0);
		}
	}

	public List<User> getByRole(Role role){
		@SuppressWarnings("unchecked")
		List<UserRole> list = this.getHibernateTemplate().find(
				"from UserRole ur where ur.roleId = ? order by ur.userId", role.getId()
				);
		List<User> users = new ArrayList<User>(list.size());
		for(UserRole ur : list){
			users.add(ur.getUser());
		}
		return users;
	}
}
