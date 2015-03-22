package org.cl.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.cl.common.util.SpringTool;
import org.cl.common.util.StrUtils;
import org.cl.userManager.entity.Organization;
import org.cl.userManager.entity.User;
import org.cl.userManager.service.OrganizationService;
import org.cl.userManager.service.UserService;

/**
 * 组织机构接口
 * 
 * @author cl
 * 
 */
public class OrgInterface {

	/**
	 * 获取所有的分公司
	 * 
	 * @return
	 */
	public static List findAllfiliale() {
		OrganizationService organizationService = (OrganizationService) SpringTool
				.getBean(OrganizationService.class);
		return organizationService.findByType("filiale");
	}

	/**
	 * 根据filialeId获取店面,filialeId为空，则获取所有的
	 * 
	 * @param filialeId
	 * @return
	 */
	public static List findShopByFilialeId(Long filialeId) {
		OrganizationService organizationService = (OrganizationService) SpringTool
				.getBean(OrganizationService.class);
		return organizationService.findShopByFilialeId(filialeId);
	}

	/**
	 * 根据数据级别获取机构ID
	 * 
	 * @return
	 */
	public static List getOrgIdByDataScopeLevel(String filialeIdStr,
			String dataScopeLevelCode) {
		OrganizationService organizationService = (OrganizationService) SpringTool
				.getBean(OrganizationService.class);
		List<Organization> list = new ArrayList();
		Long filialeId = null;
		if (StrUtils.isNotNullOrBlank(filialeIdStr)) {
			filialeId = new Long(filialeIdStr);
		}
		// 不授权
		if ("none".equals(dataScopeLevelCode)) {
			return list;
		} else if ("single_filiale".equals(dataScopeLevelCode)) { // 获取单个分公司
			if (null != filialeId) {
				Organization filialeOrganization = (Organization) organizationService
						.findById(filialeId);
				list.add(filialeOrganization);
			} else {
				List orgList = findAllfiliale();
				list.addAll(orgList);
			}
		} else if ("mult_filiale".equals(dataScopeLevelCode)) {
			List orgList = findAllfiliale();
			list.addAll(orgList);
		} else if ("single_shop".equals(dataScopeLevelCode)) {
			List shopList = findShopByFilialeId(filialeId);
			list.addAll(shopList);
		} else if ("single_filiale_mult_shop".equals(dataScopeLevelCode)) {
			List shopList = findShopByFilialeId(filialeId);
			list.addAll(shopList);
		} else if ("mult_filiale_mult_shop".equals(dataScopeLevelCode)) {
			List shopList = findShopByFilialeId(null);
			list.addAll(shopList);
		}

		return list;
	}

	/**
	 * 根据分公司ID获取用户能访问的店面集合
	 * 
	 * @param userId
	 * @param filialeId
	 * @return
	 */
	public static List findShopByDataScopeAndFilialeId(Long userId,
			Long filialeId) {
		OrganizationService organizationService = (OrganizationService) SpringTool
				.getBean(OrganizationService.class);
		UserService userService = (UserService) SpringTool
				.getBean(UserService.class);
		User user = (User) userService.findById(userId);
		String dataScopeLevelCode = user.getDataScopeLevelCode();
		List<Organization> shopList = new ArrayList();
		if (StrUtils.isNullOrBlank(dataScopeLevelCode)
				|| StrUtils.isNullOrBlank(user.getDataScopeOrgs())) {
			return shopList;
		}
		// 单个分公司或者多个分公司
		if ("single_filiale".equals(dataScopeLevelCode)
				|| "mult_filiale".equals(dataScopeLevelCode)) {
			shopList = organizationService.findShopByFilialeId(filialeId);
		} else {
			List<Organization> temp = organizationService
					.findShopByFilialeId(filialeId);
			for (int i = 0; i < temp.size(); i++) {
				if (user.getDataScopeOrgs().indexOf(
						temp.get(i).getId().toString()) > 0) {
					shopList.add(temp.get(i));
				}
			}
		}
		return shopList;
	}

	/**
	 * 获取用户可以访问的店面集合
	 * 
	 * @param userId
	 * @return
	 */
	public static List findShopByDataScope() {
		OrganizationService organizationService = (OrganizationService) SpringTool
				.getBean(OrganizationService.class);
		User user = UserInterface.getCurentUser();
		String dataScopeLevelCode = user.getDataScopeLevelCode();
		List<Organization> shopList = new ArrayList();
		if (StrUtils.isNullOrBlank(dataScopeLevelCode)
				|| StrUtils.isNullOrBlank(user.getDataScopeOrgs())) {
			return shopList;
		}
		String orgStrs = user.getDataScopeOrgs();
		if (StrUtils.isNullOrBlank(orgStrs)) {
			return shopList;
		}
		String[] orgArray = orgStrs.split(",");
		for (int i = 0; i < orgArray.length; i++) {
			if (StrUtils.isNullOrBlank(orgArray[i])) {
				continue;
			}
			Long orgId = new Long(orgArray[i]);
			Organization organization = (Organization) organizationService
					.findById(orgId);
			if ("filiale".equals(organization.getTypeCode())) {
				shopList.addAll(findShopByFilialeId(organization.getId()));
			} else {
				shopList.add(organization);
			}
		}
		return shopList;
	}

	/**
	 * 获取所有的分公司部门和店面
	 * 
	 * @return
	 */
	public static List findAllfilialeDepAndShop() {
		OrganizationService organizationService = (OrganizationService) SpringTool
				.getBean(OrganizationService.class);
		return organizationService.findAllfilialeDepAndShop();
	}
}
