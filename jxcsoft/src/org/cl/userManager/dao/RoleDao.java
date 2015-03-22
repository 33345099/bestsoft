package org.cl.userManager.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cl.common.core.orm.BaseDao;
import org.cl.common.util.StrUtils;
import org.cl.userManager.entity.Role;
import org.cl.userManager.entity.RoleOperation;
import org.cl.userManager.entity.UserRole;
import org.springframework.stereotype.Repository;

@Repository()
public class RoleDao extends BaseDao {

	public void deleteRoleGrantByRoleId(Long roleId) {
		this.getHibernateTemplate().bulkUpdate(
				"delete from RoleMenu where roleId=?", roleId);

	}

	/**
	 * 获取角色所拥有的操作权限
	 * 
	 * @param roleId
	 * @return
	 */
	public List<RoleOperation> findRoleOperation(Long roleId) {
		String queryString = " from RoleOperation ro where ro.roleId=?";
		return this.getHibernateTemplate().find(queryString, roleId);
	}

	/**
	 * 根据用户ID获取角色名称
	 * 
	 * @param userId
	 * @return
	 */
	public List<String> findNameByUserIdForJdbc(Long userId) {
		List list = new ArrayList();
		String queryString = " select name from um_role r,um_user_role ur where ur.role_id=r.id and ur.user_id=?";
		List resultList = this.getJdbcTemplate().queryForList(queryString,
				new Object[] { userId });
		Map map = null;
		for (int i = 0; i < resultList.size(); i++) {
			map = (Map) resultList.get(i);
			list.add(StrUtils.ObjectToString(map.get("name")));
		}
		return list;
	}

	public void deleteRoleGrantOperationByRoleId(Long roleId) {
		this.getHibernateTemplate().bulkUpdate(
				"delete from RoleOperation where roleId=?", roleId);
	}

	public void deleteRoleGrantStudenDtataByRoleId(Long roleId) {
		this.getHibernateTemplate().bulkUpdate(
				"delete from RoleStudentData where roleId=?", roleId);
	}

	public void delete(Long id) {
		this.getHibernateTemplate().bulkUpdate(
				"delete from UserRole where roleId=?", id);
		this.getHibernateTemplate().bulkUpdate(
				"delete from RoleOperation where roleId=?", id);
		this.getHibernateTemplate().bulkUpdate("delete from Role where id=?",
				id);
	}

	public List<Role> findSystemRole() {
		return this.getHibernateTemplate().find(" from Role where isSystem=1");
	}

	public Long getCountSystemRole() {
		String queryString = " select count(*) from Role where isSystem=1";
		List list = this.getHibernateTemplate().find(queryString);
		Long count = (Long) list.get(0);
		return count;
	}

	/**
	 * 根据类型获取系统角色
	 * 
	 * @param roleStudentType
	 * @return
	 */
	public Role getSystemRoleByType(String type) {
		String queryString = " from Role  where isSystem=1 and type=?";
		List list = this.getHibernateTemplate().find(queryString, type);
		return (Role) list.get(0);
	}

	/**
	 * 根据用户ID获取角色
	 * 
	 * @param id
	 * @return
	 */
	public List findByUserId(Long userId) {
		String queryString = " from UserRole where userId=?";
		List<UserRole> list = this.getHibernateTemplate().find(queryString,
				userId);
		List returnList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			returnList.add(this.findById(Role.class, list.get(i).getRoleId()));
		}
		return returnList;
	}
}
