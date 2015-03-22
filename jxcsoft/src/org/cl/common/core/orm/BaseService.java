package org.cl.common.core.orm;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.cl.common.util.ReflectionUtils;
import org.cl.common.util.SpringTool;
import org.cl.common.widget.pagination.Page;
import org.springframework.transaction.annotation.Transactional;

/**
 * 基础Service
 * 
 * @author chenl 2010-9-26
 */
@Transactional()
public class BaseService {

	private BaseDao getDao() {
		GenericDao genericDao = (GenericDao) SpringTool
				.getBean(GenericDao.class);
		return genericDao;
	}

	/**
	 * 获取实体类类型
	 * 
	 * @return
	 */
	private Class getClazz() {
		Class clazz = null;
		String className = this.getClass().getName().replace("service",
				"entity").split("Service")[0];
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return clazz;
	}

	/**
	 * 根据ID获取对象
	 * 
	 * @param id
	 *            对象ID
	 * @return 对象
	 */
	public BaseEntity findById(Serializable id) {
		return getDao().findById(getClazz(), id);
	}

	/**
	 * 根据属性获取对象集合
	 * 
	 * @param propertyMap
	 *            属性名称-值集合
	 * @return 对象集合
	 */
	public List findByMap(Class clazz, Map<String,Object> propertyMap) {
		return getDao().findByProperty(clazz, propertyMap,"id", false);
	}
	
	/**
	 * 根据属性获取对象集合
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param propertyValue
	 *            属性值
	 * @return 对象集合
	 */
	public List findBy(Class clazz, String propertyName, Object propertyValue) {
		return getDao().findByProperty(clazz, propertyName, propertyValue,
				"id", false);
	}

	/**
	 * 根据属性获取单个对象
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param propertyValue
	 *            属性值
	 * @return 对象
	 */
	public BaseEntity findSingleBy(String propertyName, Object propertyValue) {
		List<BaseEntity> list = getDao().findByProperty(getClazz(),
				propertyName, propertyValue, "id", false);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据属性获取单个对象
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param propertyValue
	 *            属性值
	 * @return 对象
	 */
	public BaseEntity findSingleBy(Class clazz, String propertyName,
			Object propertyValue) {
		List<BaseEntity> list = getDao().findByProperty(clazz, propertyName,
				propertyValue, "id", false);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 获取所有的实体
	 * 
	 * @return
	 */
	public List findAll() {
		return getDao().findAll(getClazz(), "id", false);
	}

	/**
	 * 获取所有的实体
	 * 
	 * @return
	 */
	public List findAll(String orderProperty, Boolean isAsc) {
		return getDao().findAll(getClazz(), orderProperty, isAsc);
	}

	/**
	 * 保存实体类
	 * 
	 * @param baseEntity
	 */
	public void save(BaseEntity baseEntity) {
		getDao().saveOrUpdate(baseEntity);
	}

	/**
	 * 修改实体类
	 * 
	 * @param baseEntity
	 */
	public void update(BaseEntity baseEntity) {
		BaseEntity oldBaseEntity = this.findById(baseEntity.getId());
		ReflectionUtils.copyPropertiesForHasValue(oldBaseEntity, baseEntity);
		getDao().saveOrUpdate(oldBaseEntity);
	}

	/**
	 * 直接修改客户
	 */
	public void directUpdate(BaseEntity baseEntity) {
		getDao().update(baseEntity);
	}

	/**
	 * 删除实体类
	 * 
	 * @param ids
	 */
	public void deleteIds(String ids) {
		String[] idArray = ids.split(",");
		for (int i = 0; i < idArray.length; i++) {
			getDao().delete(getClazz(), new Long(idArray[i]));
		}
	}

	/**
	 * 分页获取对象集合
	 * 
	 * @param page
	 * @param searchMap
	 * @return
	 */
	public List findByPage(Page page, Map searchMap) {
		return getDao().findByPage(getClazz(), page, searchMap);
	}

	public boolean isUniqueByFieldCommon(Class clazz, String name,
			Object value, boolean isAdd, Long id) {
		if (isAdd) {
			return getDao().isUniqueByFieldForAdd(clazz, name, value);
		} else {
			return getDao().isUniqueByFieldForUpdate(clazz, name, value, id);
		}
	}

	/**
	 * 获取父级下面同级的最大序号
	 * 
	 * @param clazz
	 * @param orderByName
	 * @return
	 */
	public int findMaxOrderCode(String propertyName, Object propertyValue) {
		return getDao().findMaxOrderCode(getClazz(), propertyName,
				propertyValue);
	}
}
