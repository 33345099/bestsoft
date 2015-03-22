package org.cl.userManager.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cl.common.core.exception.BusinessException;
import org.cl.common.core.exception.ExceptionConstant;
import org.cl.common.core.orm.BaseService;
import org.cl.userManager.dao.OrganizationDao;
import org.cl.userManager.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 单位实现类
 * 
 * @author chenl
 * @data Oct 8, 2009
 */
@Transactional()
@Service(value = "org.cl.userManager.service.OrganizationService")
public class OrganizationService extends BaseService {
	@Autowired
	private OrganizationDao organizationDao = null;

	/**
	 * 获取根节点
	 */
	public Organization findRootOrganization() {
		return organizationDao.findRootOrganization();
	}

	/**
	 * 保存组织机构
	 * 
	 * @param organization
	 */
	public void save(Organization organization) {
		organization.setIsDelete(0);
		organization.setIsLeaf(1);
		organizationDao.saveOrUpdate(organization);
		Organization parent = (Organization) organizationDao.findById(
				Organization.class, organization.getParentId());
		if (parent.getIsLeaf() == 1) {
			parent.setIsLeaf(0);
		}
		if ("filiale".equals(organization.getTypeCode())) {
			organization.setFilialeId(organization.getId());
		}
		organizationDao.saveOrUpdate(organization);
	}

	/**
	 * 删除组织机构
	 * 
	 * @param ids
	 */
	public void deleteIds(String ids) {
		String[] idArray = ids.split(",");
		Organization parent = null;
		Organization organization = null;
		for (int i = 0; i < idArray.length; i++) {
			organization = (Organization) organizationDao.findById(
					Organization.class, new Long(idArray[i]));
			// 判断是否是叶子节点，如果不是，则无法删除
			if (organization.getIsLeaf() == 0) {
				throw new BusinessException(ExceptionConstant.HAS_CHILD);
			}
			parent = organization.getParent();
			organization.setIsDelete(1);// 表示逻辑上删除，查询的时候不查出来
			organizationDao.update(organization);
			Map<String, Object> propertyMap = new HashMap<String, Object>();
			propertyMap.put("isDelete", 0);
			Long childCount = organizationDao.hasCountChild(parent.getId(),
					propertyMap);
			// 判断是否是叶子节点
			if (childCount == 0) {
				parent.setIsLeaf(1);
				organizationDao.update(parent);
			}
		}
	}

	/**
	 * 初始化根节点
	 * 
	 * @return
	 */
	public Organization initRootOrganization() {
		return organizationDao.initRootOrganization();
	}

	/**
	 * 根据机构类型获取机构
	 * 
	 * @param string
	 * @return
	 */
	public List findByType(String type) {
		return organizationDao.findByType(type);
	}

	/**
	 * 根据分公司Id获取店面
	 * 
	 * @param filialeId
	 * @return
	 */
	public List findShopByFilialeId(Long filialeId) {
		return organizationDao.findShopByFilialeId(filialeId);
	}

	/**
	 * 获取所有分公司部门和店面
	 * 
	 * @return
	 */
	public List findAllfilialeDepAndShop() {
		return organizationDao.findAllfilialeDepAndShop();
	}

	public List<Organization> findByParentId(Long parentId) {
		return organizationDao.findByParentId(parentId);
	}
}
