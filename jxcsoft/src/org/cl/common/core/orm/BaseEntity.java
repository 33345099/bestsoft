package org.cl.common.core.orm;

import java.util.Date;

/**
 * 实体类的基类
 * 
 * @author chenl
 * @Apr 2, 2009
 */
public class BaseEntity {

	protected Long id = null;

	protected Long creatorId;

	/**
	 * 创建人名称
	 */
	protected String creatorName;

	/**
	 * 创建时间
	 */
	protected Date createTime;

	/**
	 * 创建日期
	 */
	protected Date createDate;

	/**
	 * 创建部门ID
	 */
	protected Long createOrgId;

	/**
	 * 创建部门名称
	 */
	protected String createOrgName;

	protected Date lastModifiedTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getCreateOrgId() {
		return createOrgId;
	}

	public void setCreateOrgId(Long createOrgId) {
		this.createOrgId = createOrgId;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
}
