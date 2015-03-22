package org.cl.userManager.entity;

import org.cl.common.core.orm.BaseEntity;
import org.cl.system.resource.entity.Operation;

public class RoleOperation extends BaseEntity {

	private Long roleId = null;

	private Long operationId = null;

	private Role role = null;

	private Operation operation = null;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getOperationId() {
		return operationId;
	}

	public void setOperationId(Long operationId) {
		this.operationId = operationId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

}
