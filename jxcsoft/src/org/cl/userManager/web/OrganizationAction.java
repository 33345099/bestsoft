package org.cl.userManager.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.cl.common.core.exception.BusinessException;
import org.cl.common.core.exception.ExceptionConstant;
import org.cl.common.core.web.BaseAction;
import org.cl.common.util.HttpTool;
import org.cl.common.util.SpringTool;
import org.cl.common.widget.pagination.Page;
import org.cl.common.widget.tree.TreeEntity;
import org.cl.interfaces.OrgInterface;
import org.cl.userManager.entity.Organization;
import org.cl.userManager.service.OrganizationService;

/**
 * 机构Action
 * 
 * @author chenl
 * @data Oct 8, 2009
 */
@Namespace("/userManager")
@Results( {
		@Result(name = "indexOrganization", location = "/WEB-INF/content/userManager/organization/indexOrganization.jsp"),
		@Result(name = "listOrganization", location = "/WEB-INF/content/userManager/organization/listOrganization.jsp"),
		@Result(name = "addOrganization", location = "/WEB-INF/content/userManager/organization/addOrganization.jsp"),
		@Result(name = "modifyOrganization", location = "/WEB-INF/content/userManager/organization/modifyOrganization.jsp") })
public class OrganizationAction extends BaseAction {

	private OrganizationService organizationService = (OrganizationService) SpringTool
			.getBean(OrganizationService.class);

	private Organization organization = null;

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	/**
	 * 显示框架页面
	 */
	public String indexOrganization() throws Exception {
		organization = (Organization) organizationService.findSingleBy(
				"parentId", null);// 获取根节点机构
		return "indexOrganization";
	}

	/**
	 * 根据父ID显示Organization列表
	 */
	public String listOrganization() throws Exception {
		Map searchMap = super.buildSearch();
		page = new Page(HttpTool.getPageNum());
		super.transferBuildSearch(searchMap, "and", "EQ", "L",
				"transfer_parentId", "parentId");
		searchMap.put("filter_and_isDelete_EQ_I", 0);//逻辑删除的机构不显示出来
		organizationService.findByPage(page, searchMap);
		return "listOrganization";
	}

	/**
	 * 根据父ID显示机构树
	 */
	public String treeOrganization() throws Exception {
		Map<String,Object> propertyMap = new HashMap<String,Object>();
		propertyMap.put("parentId", HttpTool.getLongParameter("parentId"));
		propertyMap.put("isDelete", 0);
		List<Organization> list = organizationService.findByMap(Organization.class, propertyMap);
		List jsonList = new ArrayList();
		Organization organization = null;
		for (int i = 0; i < list.size(); i++) {
			organization = list.get(i);
			TreeEntity tree = new TreeEntity(organization.getId(), organization
					.getName(), organization.getIsLeaf());
			jsonList.add(tree);
		}
		return super.json(jsonList);
	}

	
	/**
	 * 保存机构
	 */
	public String saveOrganization() throws Exception {
		organization.setIsLeaf(1);
		organizationService.save(organization);
		HttpTool.setAttribute("transfer_reLoadTree", "true");
		return jump("organization!listOrganization.action");
	}

	

	/**
	 * 修改机构
	 */
	public String updateOrganization() throws Exception {
		if ("filiale".equals(organization.getTypeCode())) {
			organization.setFilialeId(this.getId());
		}
		organizationService.update(organization);
		HttpTool.setAttribute("transfer_reLoadTree", "true");
		return jump("organization!listOrganization.action");
	}

	/**
	 * 删除机构
	 */
	public String deleteOrganization() throws Exception {
		try {
			organizationService.deleteIds(ids);
		} catch (BusinessException e) {
			if (e.getCode().equals(ExceptionConstant.HAS_CHILD)) {
				HttpTool.setAttribute("alert", "删除数据失败，请首先删除子机构！");
			} else if (e.getCode().equals(ExceptionConstant.HAS_DATA)) {
				HttpTool.setAttribute("alert", "删除数据失败，请首先删除用户!");
			}
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("transfer_reLoadTree", true);
		return jump("organization!listOrganization.action");
	}
}
