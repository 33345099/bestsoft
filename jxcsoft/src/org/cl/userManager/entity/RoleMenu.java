package org.cl.userManager.entity;

import org.cl.common.core.orm.BaseEntity;
import org.cl.system.resource.entity.Menu;

/**
 * 菜单角色关系表
 * 
 * @author chenl
 * @data 2009-8-1
 */
public class RoleMenu extends BaseEntity {

	private Long roleId = null;

	private Long menuId = null;

	private Role role = null;

	private Menu menu = null;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}
