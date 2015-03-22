package org.cl.system.log.entity;

import java.util.Date;

import org.cl.common.core.orm.BaseEntity;

/**
 * 操作日志
 * 
 * @author chenl
 * @data Dec 2, 2009
 */
public class OperationLog extends BaseEntity {

	/**
	 * 登录
	 */
	private Long loginLogId;

	private LoginLog loginLog;

	/**
	 * 用户姓名
	 */
	private String userName;

	/**
	 * 业务名称
	 */
	private String businessName;

	/**
	 * 操作名称
	 */
	private String operationName;

	/**
	 * 操作时间
	 */
	private Date operationDate;

	/**
	 * 是否成功
	 */
	private Integer isSuccess;

	/**
	 * 部门名称
	 */
	private String orgName;

	public LoginLog getLoginLog() {
		return loginLog;
	}

	public void setLoginLog(LoginLog loginLog) {
		this.loginLog = loginLog;
	}

	public Long getLoginLogId() {
		return loginLogId;
	}

	public void setLoginLogId(Long loginLogId) {
		this.loginLogId = loginLogId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	public Integer getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

}