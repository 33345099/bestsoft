package org.cl.userManager.entity;

import org.cl.common.core.orm.BaseEntity;

/**
 * 组织机构实体类
 * 
 * @author chenl
 * @data Sep 28, 2009
 */
public class Organization extends BaseEntity {

	/**
	 * 机构代码
	 */
	private String code = null;

	/**
	 * 单位名称
	 */
	private String name = null;

	/**
	 * 单位电话
	 */
	private String phone = null;

	/**
	 * 父节点ID
	 */
	private Long parentId = null;

	/**
	 * 父机构
	 */
	private Organization parent = null;

	/**
	 * 是否是叶子节点
	 */
	private Integer isLeaf = null;

	/**
	 * 机构类型
	 */
	private String typeCode = null;
	
	/**
	 * 删除状态
	 */
	private Integer isDelete = 0;

	/**
	 * 所属城市
	 */
	private Long cityId = null;

	private Long filialeId = null;

	private Organization filiale = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Organization getParent() {
		return parent;
	}

	public void setParent(Organization parent) {
		this.parent = parent;
	}

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getFilialeId() {
		return filialeId;
	}

	public void setFilialeId(Long filialeId) {
		this.filialeId = filialeId;
	}

	public Organization getFiliale() {
		return filiale;
	}

	public void setFiliale(Organization filiale) {
		this.filiale = filiale;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}



	
	
	
}
