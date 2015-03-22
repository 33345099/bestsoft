package org.cl.userManager.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cl.common.core.SystemConstant;
import org.cl.common.core.orm.BaseDao;
import org.cl.userManager.entity.Organization;
import org.springframework.stereotype.Repository;

/**
 * 组织机构DAO
 * 
 * @author chenl
 * @data Oct 8, 2009
 */
@Repository()
public class OrganizationDao extends BaseDao {

	/**
	 * 获取根节点
	 * 
	 * @return
	 */
	public Organization findRootOrganization() {
		String queryString = null;
		queryString = " from Organization o where  isDelete=0 and o.parentId is null";
		List<Organization> list = super.getHibernateTemplate()
				.find(queryString);
		list = super.getHibernateTemplate().find(queryString);
		return list.get(0);
	}

	/**
	 * 是否含有叶子节点
	 * 
	 * @param parentId
	 * @return
	 */
	public Long hasCountChild(Long parentId) {
		String queryString = " select count(*) from Organization where isDelete=0 and parentId=?";
		List list = this.getHibernateTemplate().find(queryString, parentId);
		Long count = (Long) list.get(0);
		return count;
	}

	/**
	 * 是否含有叶子节点(带查询条件的)
	 * 
	 * @param parentId
	 * 
	 * @param propertyMap
	 * @return
	 */
	public Long hasCountChild(Long parentId, Map<String, Object> propertyMap) {
		StringBuilder queryString = new StringBuilder(
				" select count(*) from Organization where isDelete=0 and parentId=?");
		if (propertyMap != null) {
			Set<String> keys = propertyMap.keySet();
			for (String key : keys) {
				queryString.append(" and ").append(key).append(" = ").append(
						propertyMap.get(key));
			}
		}
		List list = this.getHibernateTemplate().find(queryString.toString(),
				parentId);
		Long count = (Long) list.get(0);
		return count;
	}

	/**
	 * 判断组织机构下面是否有用户
	 * 
	 * @return
	 */
	public long hasUserForJdbc(String organizationId) {
		String queryString = " select count(id) from um_user where is_delete=0 and organization_id=?";
		long count = this.getJdbcTemplate().queryForLong(queryString,
				new Object[] { organizationId });
		return count;
	}

	/**
	 * 初始化根节点
	 * 
	 * @return
	 */
	public Organization initRootOrganization() {
		String queryString = null;
		queryString = " from Organization o where isDelete=0 and o.parentId is null";
		List<Organization> list = super.getHibernateTemplate()
				.find(queryString);
		Organization rootOrganization = null;
		// 如果根节点不存在，则初始化根节点信息
		if (list.size() == 0) {
			rootOrganization = new Organization();
			rootOrganization.setName(SystemConstant
					.getSystemConstant("init_root_org_name"));
			rootOrganization.setIsLeaf(1);
		} else {
			rootOrganization = list.get(0);
			rootOrganization.setName(SystemConstant
					.getSystemConstant("init_root_org_name"));
		}
		saveOrUpdate(rootOrganization);
		return rootOrganization;
	}

	/**
	 * 根据机构类型获取机构
	 * 
	 * @param type
	 * @return
	 */
	public List findByType(String type) {
		return this.getHibernateTemplate().find(
				" from Organization where isDelete=0 and typeCode=?", type);
	}

	/**
	 * 根据分公司Id获取店面
	 * 
	 * @param filialeId
	 * @return
	 */
	public List findShopByFilialeId(Long filialeId) {
		if (null != filialeId) {
			return this
					.getHibernateTemplate()
					.find(
							" from Organization where isDelete=0 and filialeId=? and typeCode=?",
							new Object[] { filialeId, "shop" });
		} else {
			return this.getHibernateTemplate().find(
					" from Organization where isDelete=0 and typeCode=?",
					new Object[] { "shop" });
		}
	}

	/**
	 * 获取所有分公司部门和店面
	 * 
	 * @return
	 */
	public List findAllfilialeDepAndShop() {
		return this
				.getHibernateTemplate()
				.find(
						" from Organization where isDelete=0 and typeCode='filiale_dep' or typeCode='shop'");
	}

	public List<Organization> findByParentId(Long parentId) {
		if (null != parentId) {
			return this.getHibernateTemplate().find(
					" from Organization where isDelete=0 and  parentId=? order by id desc",
					parentId);
		} else {
			return this
					.getHibernateTemplate()
					.find(
							" from Organization where isDelete=0 and  parentId is null order by id desc");
		}
	}
}
