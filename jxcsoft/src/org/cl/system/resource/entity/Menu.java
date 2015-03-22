package org.cl.system.resource.entity;

import java.util.Set;

import org.cl.common.core.orm.BaseEntity;

/**
 * 菜单实体类
 * 
 * @author chenl
 * @data May 8, 2009
 */
public class Menu extends BaseEntity {

	/**
	 * 菜单名称
	 */
	private String name;

	/**
	 * 是否叶子节点
	 */
	private Integer isLeaf;

	/**
	 * url
	 */
	private String url;

	/**
	 * 排序
	 */
	private Integer orderCode = 1;

	/**
	 * 状态
	 */
	public Integer isEnable = null;
	/**
	 * 子菜单
	 */
	private Set<Menu> childMenus = null;

	/**
	 * 父菜单ID
	 */
	private Long parentId;

	/**
	 * 父菜单
	 */
	private Menu parentMenu = null;

	/**
	 * 菜单类型(0：链接菜单,1：非链接菜单,2:模块)
	 */
	private String type = null;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(Integer orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	public Set<Menu> getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(Set<Menu> childMenus) {
		this.childMenus = childMenus;
	}

	public Menu getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}