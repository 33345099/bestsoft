package org.cl.userManager.entity;

import org.cl.common.core.orm.BaseEntity;

/**
 * 角色实体类
 * 
 * @author chenl
 * @data 2009-7-25
 */
public class Role extends BaseEntity {
	/**
	 * 角色名称
	 */
	private String name;

	/**
	 * 说明
	 */
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
