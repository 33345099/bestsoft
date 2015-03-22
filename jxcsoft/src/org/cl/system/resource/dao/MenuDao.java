package org.cl.system.resource.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cl.common.core.orm.BaseDao;
import org.cl.common.util.StrUtils;
import org.cl.system.resource.entity.Menu;
import org.springframework.stereotype.Repository;

@Repository()
public class MenuDao extends BaseDao {

	private static final Log log = LogFactory.getLog(MenuDao.class);

	/**
	 * 获取根菜单
	 */
	public Menu findRootMenu() {
		String queryString = null;
		queryString = " from Menu m where m.parentId is null";
		List<Menu> list = super.getHibernateTemplate().find(queryString);
		return list.get(0);
	}

	/**
	 * 获取用户可以访问的模块级别的菜单(二级菜单)
	 * 
	 * @return
	 */
	public List<Menu> findModuleMenuByUserIdForJdbc(Long userId) {
		List list = new ArrayList();
		String queryString = " select m.id,m.name from sys_menu m,um_user_role ur,um_role_menu rm "
				+ " where m.id=rm.menu_id and rm.role_id=ur.role_id and m.is_enable!=0 and m.type='2' and ur.user_id=?";
		List resultList = this.getJdbcTemplate().queryForList(queryString,
				new Object[] { userId });
		Map map = null;
		for (int i = 0; i < resultList.size(); i++) {
			Menu menu = new Menu();
			map = (Map) resultList.get(i);
			menu.setId(StrUtils.toLong(map.get("id")));
			menu.setName(StrUtils.ObjectToString(map.get("name")));
			list.add(menu);
		}
		return list;
	}

	/**
	 * 按照树形结构获取所有的菜单
	 * 
	 * @return 菜单集合
	 */
	public List findAllMenuForTee() {
		String queryString = " from Menu m order by parentId,orderCode";
		return this.getHibernateTemplate().find(queryString);
	}

	/**
	 * 子菜单的数目
	 * 
	 * @param parentId
	 * @return
	 */
	public long childMenuSize(Long parentId) {
		String queryString = null;
		List list = null;
		if (null != parentId) {
			queryString = " select count(*) from Menu m where m.parentId =?";
			list = super.getHibernateTemplate().find(queryString, parentId);
		} else {
			queryString = " select count(*) from Menu m where m.parentId is null";
			list = super.getHibernateTemplate().find(queryString);
		}
		return (Long) list.get(0);
	}

	/**
	 * 根据父ID获取子菜单
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Menu> findByParentId(Long parentId) {
		String queryString = null;
		List list = null;
		if (null != parentId) {
			queryString = "  from Menu m where m.parentId =? order by orderCode";
			list = super.getHibernateTemplate().find(queryString, parentId);
		} else {
			queryString = "  from Menu m where m.parentId is null order by orderCode";
			list = super.getHibernateTemplate().find(queryString);
		}
		return list;
	}

	public List<Menu> findAllEnable() {
		String queryString = " from Menu m where m.isEnable=1 order by m.orderCode";
		return this.getHibernateTemplate().find(queryString);
	}

	/**
	 * 获取可用的并且是叶子节点的菜单URL
	 * 
	 * @return
	 */
	public List<Menu> findAllEnableAndLeafForJdbc() {
		List list = new ArrayList();
		String queryString = " select id from SYS_MENU m where m.IS_ENABLE!=0 and m.IS_LEAF=1";
		List resultList = this.getJdbcTemplate().queryForList(queryString);
		Map map = null;
		for (int i = 0; i < resultList.size(); i++) {
			Menu menu = new Menu();
			map = (Map) resultList.get(i);
			menu.setId(StrUtils.toLong(map.get("id")));
			list.add(menu);
		}
		return list;
	}

	/**
	 * 根据ID获取父ID
	 * 
	 * @param menuId
	 * @return
	 */
	public Long findParentIdByIdForJdbc(Long menuId) {
		String queryString = " select m.parent_id from sys_menu m where m.id=?";
		Long parentId = this.getJdbcTemplate().queryForLong(queryString,
				new Object[] { menuId });
		return parentId;
	}

	/**
	 * 根据菜单ID获取其相应的权限
	 * 
	 * @param menuId
	 * @return
	 */
	public String getRoleNameForRoleMenuForJdbc(Long menuId) {
		String queryString = " select r.name from um_role r,um_role_menu rm where rm.role_id=r.id and rm.menu_id=?";
		List list = this.getJdbcTemplate().queryForList(queryString,
				new Object[] { menuId });
		String roles = null;
		Map map = null;
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			roles = roles + map.get("name");
			if (i < list.size() - 1) {
				roles = roles + ",";
			}
		}
		return roles;
	}

	public List<Menu> findModuleMenu() {
		List list = new ArrayList();
		String queryString = " select m.id,m.name from sys_menu m where m.is_enable!=0 and m.type='2'";
		List resultList = this.getJdbcTemplate().queryForList(queryString);
		Map map = null;
		for (int i = 0; i < resultList.size(); i++) {
			Menu menu = new Menu();
			map = (Map) resultList.get(i);
			menu.setId(StrUtils.toLong(map.get("id")));
			menu.setName(StrUtils.ObjectToString(map.get("name")));
			list.add(menu);
		}
		return list;
	}

	public List<Menu> findEnableByParentId(Long parentId) {
		String queryString = null;
		List list = null;
		if (null != parentId) {
			queryString = "  from Menu m where m.parentId =? and isEnable!=0 order by orderCode";
			list = super.getHibernateTemplate().find(queryString, parentId);
		} else {
			queryString = "  from Menu m where m.parentId is null and isEnable!=0 order by orderCode";
			list = super.getHibernateTemplate().find(queryString);
		}
		return list;
	}

	/**
	 * 根据用户ID加载所有可用的菜单集合
	 * 
	 * @param userId
	 * @return
	 */
	public List findAllEnableMenuByUserIdForJdbc(Long userId) {
		List list = new ArrayList();
		String queryString = " select distinct m.id,m.name,m.url,m.is_leaf,m.parent_id,m.type,m.order_code, m.url from sys_menu m,um_user_role ur,um_role_menu rm "
				+ " where m.id=rm.menu_id and rm.role_id=ur.role_id and m.is_enable=1 and ur.user_id=? order by m.order_code";
		List resultList = this.getJdbcTemplate().queryForList(queryString,
				new Object[] { userId });
		Map map = null;
		for (int i = 0; i < resultList.size(); i++) {
			Menu menu = new Menu();
			map = (Map) resultList.get(i);
			menu.setId(StrUtils.toLong(map.get("id")));
			menu.setParentId(StrUtils.toLong(map.get("parent_id")));
			menu.setName(StrUtils.ObjectToString(map.get("name")));
			menu.setUrl(StrUtils.ObjectToString(map.get("url")));
			menu.setType(StrUtils.ObjectToString(map.get("type")));
			menu.setOrderCode(StrUtils
					.ObjectToInteger(map.get("order_code")));
			menu.setIsLeaf(StrUtils.ObjectToInteger(map.get("is_leaf")));
			list.add(menu);
		}
		return list;
	}

	/**
	 * 删除所有的菜单
	 */
	public void deleteAllMenu() {
		this.getHibernateTemplate().bulkUpdate(" delete from RoleMenu");
		Menu menu = getHibernateTemplate().get(Menu.class, 1l);
		menu.getChildMenus().clear();
		getHibernateTemplate().delete(menu);
		this.getHibernateTemplate().bulkUpdate(" delete from Menu");
	}

	synchronized public Long getMaxIdForJdbc() {
		long maxId = this.getJdbcTemplate().queryForLong(
				"select max(id) from sys_menu");
		return maxId + 1;
	}

	public void saveForJdbc(Menu menu) {
		log.info("插入菜单:" + menu.getName());
		String sql = null;
		Long id = getMaxIdForJdbc();
		menu.setId(id);
		sql = "insert into sys_menu(id,name,is_leaf,url,order_code,is_enable,parent_id,type)values(?,?,?,?,?,?,?,?)";
		this.getJdbcTemplate().update(
				sql,
				new Object[] { id, menu.getName(), menu.getIsLeaf(),
						menu.getUrl(), menu.getOrderCode(), menu.getIsEnable(),
						menu.getParentId(), menu.getType() });
	}
}
