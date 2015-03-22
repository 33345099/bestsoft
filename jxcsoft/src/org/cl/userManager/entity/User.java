package org.cl.userManager.entity;

import java.util.List;

import org.cl.common.core.orm.BaseEntity;
import org.cl.system.resource.entity.Menu;
import org.cl.system.resource.entity.Operation;

/**
 * 用户实体类
 * 
 * @author chenl
 * @data 2009-8-7
 */
public class User extends BaseEntity {

	/**
	 * 用户名
	 */
	public String username = null;

	/**
	 * 用户姓名
	 */
	public String name = null;

	/**
	 * 密码
	 */
	public String password = null;

	/**
	 * 是否启用
	 */
	public Integer isEnable = 1;

	/**
	 * 角色集合
	 */
	private List<String> roleStrs = null;

	/**
	 * 角色集合
	 */
	private List<Role> roles = null;

	/**
	 * 所属机构ID
	 */
	private Long organizationId = null;

	/**
	 * 所属分公司
	 */
	private Long filialeId = null;

	private Organization organization = null;

	private Organization filiale = null;

	/**
	 * 授权折扣率
	 */
	private Double discountRate;

	/**
	 * 客户授权范围级别
	 */
	private String dataScopeLevelCode;

	/**
	 * 查看客户联系方式
	 */
	private Integer isLookCustomerContract;

	/**
	 * 设计师报价级别
	 */
	public String stylistLevels = null;

	private List<String> stylistLevelList = null; // 辅助属性设计师报价级别集合

	/**
	 * 数据范围机构集合
	 */
	public String dataScopeOrgs = null;

	private List<String> dataScopeOrgList = null; // 辅助属性数据范围机构集合

	/**
	 * 用户类型，0标识超级管理员 1标识普通用户
	 */
	private String type = null;

	/**
	 * 用户工种
	 */
	private String dutyCode = null;

	// 辅助属性++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**
	 * 用户可以访问的所有资源
	 */
	private List<String> urls = null;

	/**
	 * 用户可以访问的菜单
	 */
	private List<Menu> menus = null;

	/**
	 * 用户可以访问的模块
	 */
	private List<Menu> moduleMenus = null;

	/**
	 * 用户可以访问的操作
	 */
	private List<Operation> operations = null;

	/**
	 * 角色名称集合
	 */
	private String roleNames = null;

	/**
	 * 是否离职
	 */
	private Integer isDimission = null;

	private Integer isDelete = null;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	public List<String> getRoleStrs() {
		return roleStrs;
	}

	public void setRoleStrs(List<String> roleStrs) {
		this.roleStrs = roleStrs;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public List<Menu> getModuleMenus() {
		return moduleMenus;
	}

	public void setModuleMenus(List<Menu> moduleMenus) {
		this.moduleMenus = moduleMenus;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public Double getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Double discountRate) {
		this.discountRate = discountRate;
	}

	public String getDataScopeLevelCode() {
		return dataScopeLevelCode;
	}

	public void setDataScopeLevelCode(String dataScopeLevelCode) {
		this.dataScopeLevelCode = dataScopeLevelCode;
	}

	public Integer getIsLookCustomerContract() {
		return isLookCustomerContract;
	}

	public void setIsLookCustomerContract(Integer isLookCustomerContract) {
		this.isLookCustomerContract = isLookCustomerContract;
	}

	public String getStylistLevels() {
		return stylistLevels;
	}

	public void setStylistLevels(String stylistLevels) {
		this.stylistLevels = stylistLevels;
	}

	public String getDataScopeOrgs() {
		return dataScopeOrgs;
	}

	public void setDataScopeOrgs(String dataScopeOrgs) {
		this.dataScopeOrgs = dataScopeOrgs;
	}

	public List<String> getStylistLevelList() {
		return stylistLevelList;
	}

	public void setStylistLevelList(List<String> stylistLevelList) {
		this.stylistLevelList = stylistLevelList;
	}

	public List<String> getDataScopeOrgList() {
		return dataScopeOrgList;
	}

	public void setDataScopeOrgList(List<String> dataScopeOrgList) {
		this.dataScopeOrgList = dataScopeOrgList;
	}

	public String getDutyCode() {
		return dutyCode;
	}

	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
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

	public Integer getIsDimission() {
		return isDimission;
	}

	public void setIsDimission(Integer isDimission) {
		this.isDimission = isDimission;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

}
