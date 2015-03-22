package org.cl.system.resource.entity;

import org.cl.common.core.orm.BaseEntity;

public class Operation extends BaseEntity {
	/**
	 * 操作名称
	 */
	private String name;

	/**
	 * 操作标识符
	 */
	private String identifier;

	private String typeCode = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
}
