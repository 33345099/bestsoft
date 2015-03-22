package org.cl.system.log.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.cl.common.core.orm.BaseEntity;

/**
 * 登录日志
 * 
 * @author chenl
 * @data Dec 2, 2009
 */
public class LoginLog extends BaseEntity {

	private String userName;

	private String loginIp;

	private Date loginTime;

	private Date logoutTime;

	private String orgName;

	private Set operationLogs = new HashSet(0);

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public Set getOperationLogs() {
		return operationLogs;
	}

	public void setOperationLogs(Set operationLogs) {
		this.operationLogs = operationLogs;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

}