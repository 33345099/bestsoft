package org.cl.common.core.orm;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cl.common.core.orm.daoWrapper.DaoSupport;
import org.cl.common.widget.pagination.Page;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.util.Assert;

/**
 * 基础DAO，封装了常用的CRUD操作
 * 
 * @author chenl
 * @Apr 15, 2009
 */

public class BaseDao {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private DaoSupport daoSupport;

	public HibernateTemplate getHibernateTemplate() {
		return daoSupport.getHibernateDaoSupport().getHibernateTemplate();
	}

	public JdbcTemplate getJdbcTemplate() {
		return daoSupport.getJdbcDaoSupport().getJdbcTemplate();
	}

	/**
	 * 保存实体
	 * 
	 * @param entity
	 *            实体对象
	 */
	public void saveOrUpdate(BaseEntity entity) {
		Assert.notNull(entity, "实体类不能为空");
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	/**
	 * 保存实体
	 * @param entity
	 */
	public void save(BaseEntity entity){
		Assert.notNull(entity, "实体类不能为空");
		this.getHibernateTemplate().save(entity);
	}
	/**
	 * 修改实体
	 * 
	 * @param entity
	 *            实体对象
	 */
	public void update(BaseEntity entity) {
		Assert.notNull(entity, "实体类不能为空");
		this.getHibernateTemplate().update(entity);
	}

	/**
	 * 删除实体
	 * 
	 * @param entity
	 *            实体对象
	 */
	public void delete(BaseEntity entity) {
		Assert.notNull(entity);
		this.getHibernateTemplate().delete(entity);
	}

	/**
	 * 清楚缓存
	 * 
	 * @param entity
	 *            实体对象
	 */
	public void clear() {
		this.getHibernateTemplate().clear();
	}

	/**
	 * 根据ID删除实体类
	 * 
	 * @param id
	 *            主键
	 * @param clazz
	 *            实体类class
	 */
	public void delete(Class clazz, Serializable id) {
		BaseEntity entity = (BaseEntity) this.getHibernateTemplate().get(clazz,
				id);
		delete(entity);
	}

	/**
	 * 删除实体类集合
	 * 
	 * @param clazz
	 * @param ids
	 */
	public void deleteIds(Class clazz, String ids) {
		String[] idArray = ids.split(",");
		for (int i = 0; i < idArray.length; i++) {
			BaseEntity entity = this.findById(clazz, new Long(idArray[i]));
			this.delete(entity);
		}

	}

	/**
	 * 根据主键取得实体对象
	 * 
	 * @param id主键
	 * @param clazz
	 *            实体类class
	 * @return 实体类
	 */
	public BaseEntity findById(Class clazz, Serializable id) {
		return (BaseEntity) this.getHibernateTemplate().get(clazz, id);
	}

	/**
	 * 根据ID获取实体的某一个属性
	 * 
	 * @param clazz
	 *            对象类型
	 * @param propertyName
	 *            属性名称
	 * @param id
	 *            主键
	 * @return
	 */
	public String findPropertyById(String clazz, String propertyName,
			Serializable id) {
		List list = this.getHibernateTemplate()
				.find(
						"select " + propertyName + " from " + clazz
								+ " where id=?", id);
		if (list.size() != 0) {
			return list.get(0).toString();
		}
		return null;
	}

	/**
	 * 获取所有的实体
	 * 
	 * @param clazz
	 *            实体类型
	 * @param orderProperty
	 *            排序字段名称
	 * @param isAsc
	 *            是否升序
	 * @return
	 */
	public List findAll(Class clazz, String orderProperty, Boolean isAsc) {
		String queryString = " from " + clazz.getSimpleName();
		if (null != orderProperty && null != isAsc) {
			String orderType = isAsc == true ? " asc " : " desc ";
			queryString = queryString + " order by " + orderProperty + " "
					+ orderType;
		}
		return this.getHibernateTemplate().find(queryString);
	}

	/**
	 * 根据属性获取对象
	 * 
	 * @param clazz
	 *            实体类型
	 * @param propertyName
	 *            属性字段名称
	 * @param propertyValue
	 *            属性字段类型
	 * @param orderProperty
	 *            排序字段名称
	 * @param isAsc
	 *            是否升序
	 * @return
	 */
	public List findByProperty(Class clazz, String propertyName,
			Object propertyValue, String orderProperty, Boolean isAsc) {
		String queryString = null;
		if (null != propertyValue) {
			queryString = " from " + clazz.getSimpleName() + " where "
					+ propertyName + "=? ";
		} else {
			queryString = " from " + clazz.getSimpleName() + " where "
					+ propertyName + " is null ";
		}
		if (null != isAsc && null != orderProperty) {
			String orderType = isAsc == true ? " asc " : " desc ";
			queryString = queryString + " order by " + orderProperty + " "
					+ orderType;
		}
		if (null != propertyValue) {
			return this.getHibernateTemplate().find(queryString, propertyValue);
		} else {
			return this.getHibernateTemplate().find(queryString);
		}
	}

	/**
	 * 根据属性获取对象
	 * 
	 * @param clazz
	 *            实体类型
	 * @param propertyMap
	 *            属性字段名称值Map
	 * @param orderProperty
	 *            排序字段名称
	 * @param isAsc
	 *            是否升序
	 * @return
	 */
	public List findByProperty(Class clazz, Map<String, Object> propertyMap,
			String orderProperty, Boolean isAsc) {
		StringBuilder queryString = new StringBuilder(" from ");
		queryString.append(clazz.getSimpleName());
		if (null != propertyMap) {
			Set<String> keys = propertyMap.keySet();
			queryString.append(" where ");
			for (String key : keys) {
				queryString.append(key).append("=")
						.append(propertyMap.get(key)).append(" and ");
			}
			if (queryString.lastIndexOf("and") > 0) {
				queryString.delete(queryString.lastIndexOf("and"), queryString
						.length() - 1);
			}
		}
		if (null != isAsc && null != orderProperty) {
			String orderType = isAsc == true ? " asc " : " desc ";
			queryString.append(" order by ").append(orderProperty).append(" ")
					.append(orderType);
		}
		return this.getHibernateTemplate().find(queryString.toString());
	}

	/**
	 * 使用HQL进行分页查询 截取hql的第一个from 拼凑select count(*)进行记录总数查询
	 * 
	 * @param page
	 *            分页对象
	 * @param hql
	 *            HQL语句
	 * @param values
	 *            HQL语句参数
	 * @return
	 */
	public List findByHql(final Page page, final String hql,
			final Object[] values) {
		int index = hql.toLowerCase().indexOf("from");
		String hqlCount = "select count(*) " + hql.substring(index);
		return findByHql(page, hqlCount, hql, values);
	}

	/**
	 * 根据查询获取参数
	 * 
	 * @param page
	 *            分页对象
	 * @param hql
	 *            HQL语句
	 * @param valueList
	 *            HQL语句的参数
	 * @return
	 */
	public List findByHql(Page page, String hql, List valueList) {
		Object object[] = new Object[valueList.size()];
		for (int i = 0; i < valueList.size(); i++) {
			object[i] = valueList.get(i);
		}
		return findByHql(page, hql, object);
	}

	public List findByHql(String hql, List valueList) {
		Object object[] = new Object[valueList.size()];
		for (int i = 0; i < valueList.size(); i++) {
			object[i] = valueList.get(i);
		}
		return this.getHibernateTemplate().find(hql, object);
	}

	/**
	 * 使用HQL进行分页查询
	 * 
	 * @param page
	 *            分页对象
	 * @param hqlCount
	 *            HQL语句查询有多少条记录
	 * @param hql
	 *            HQL语句
	 * @return
	 */
	public List findByHql(final Page page, final String hqlCount,
			final String hql, final Object[] values) {
		Assert.notNull(page, "分页类对象page不能为空");
		List list = (List) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Assert.notNull(hqlCount, "hqlCount不能为空");
						Assert.notNull(hql, "hql不能为空");
						Query query = null;
						query = session.createQuery(hqlCount);
						Long totalCount = 0l;
						if (values != null && values.length > 0) {
							for (int i = 0; i < values.length; i++) {
								query.setParameter(i, values[i]);
							}
						}
						totalCount = ((Long) query.uniqueResult());
						page.setTotalCount(totalCount);
						int offset = 0;
						offset = (page.getPageNum() - 1) * page.getPageSize();
						query = session.createQuery(hql);
						if (values != null && values.length > 0) {
							for (int i = 0; i < values.length; i++) {
								query.setParameter(i, values[i]);
							}
						}
						return query.setFirstResult(offset).setMaxResults(
								page.getPageSize()).list();

					}
				});
		page.setResults(list);
		return list;
	}

	/**
	 * 通用分页查询
	 * 
	 * @param clazz
	 *            实体类类型
	 * @param page
	 *            分页对象
	 * @param filterMap
	 *            过滤参数
	 */
	public List findByPage(Class clazz, Page page, Map<String, String> filterMap) {
		StringBuffer queryString = new StringBuffer(" from "
				+ clazz.getSimpleName() + " where 1=1 ");
		List valuelist = new ArrayList();
		OrmConverter.assemblyQuery(queryString, filterMap, valuelist);
		return findByHql(page, queryString.toString(), valuelist);
	}

	/**
	 * 添加实体时判断属性是否唯一
	 * 
	 * @param clazz
	 *            实体对象
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 * @return
	 */
	public boolean isUniqueByFieldForAdd(Class clazz, String propertyName,
			Object value) {
		List list = this.findByProperty(clazz, propertyName, value, null, null);
		if (list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 修改实体时判断属性是否唯一
	 * 
	 * @param clazz
	 * @param propertyName
	 * @param value
	 * @param id
	 * @return
	 */
	public boolean isUniqueByFieldForUpdate(Class clazz, String propertyName,
			Object value, Serializable id) {
		List list = this.getHibernateTemplate().find(
				" select id from " + clazz.getSimpleName() + " where "
						+ propertyName + "=?", value);
		Serializable oldId = null;
		if (list.isEmpty()) {
			oldId = null;
			return true;
		} else {
			oldId = list.get(0).toString();
		}
		if (oldId != null) {
			if (id != null && oldId != null && id.toString().equals(oldId)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据 JDBC获取集合
	 * 
	 * @param sql
	 * @param valueList
	 * @return
	 */
	public List queryForListByJdbc(String sql, List valueList) {
		Object objects[] = new Object[valueList.size()];
		for (int i = 0; i < valueList.size(); i++) {
			objects[i] = valueList.get(i);
		}
		return this.getJdbcTemplate().queryForList(sql, objects);
	}

	/**
	 * jdbc查询获取单个对象
	 * 
	 * @param property
	 * @param query
	 * @param valueList
	 * @return
	 */
	public String queryForSingleByJdbc(String property, String query,
			List valueList) {
		List<Map> list = queryForListByJdbc(query, valueList);
		if (!list.isEmpty()) {
			Object o = list.get(0).get(property);
			Assert.notNull(o, "获取的属性名称不正确");
			return o.toString();
		}
		return null;
	}

	/**
	 * 获取父级下面同级的最大序号
	 * 
	 * @param clazz
	 * @param orderByName
	 * @return
	 */
	public int findMaxOrderCode(Class clazz, String propertyName,
			Object propertyValue) {
		String queryString = null;
		List list = null;
		if (null == propertyName) {
			queryString = " select max(orderCode) as maxOrderCode from "
					+ clazz.getSimpleName();
			list = this.getHibernateTemplate().find(queryString);
		} else {
			if (null != propertyValue) {
				queryString = " select max(orderCode) as maxOrderCode from "
						+ clazz.getSimpleName() + " c where c." + propertyName
						+ " =?";
				list = this.getHibernateTemplate().find(queryString,
						propertyValue);
			} else {
				queryString = " select max(orderCode) as maxOrderCode from "
						+ clazz.getSimpleName() + " c where c." + propertyName
						+ " is null";
				list = this.getHibernateTemplate().find(queryString);
			}
		}
		if (!list.isEmpty()) {
			return list.get(0) == null ? 0 : (Integer) list.get(0);
		}
		return 1;
	}

	public void moveOrderCodeForDelete(Long orderId, String orderByName,
			Class claszz, Integer orderCode) {
		String queryString = null;
		if (null == orderId) {
			queryString = "update " + claszz.getSimpleName()
					+ " m set m.orderCode=m.orderCode-1 where m." + orderByName
					+ " is null and m.orderCode>=?";
			this.getHibernateTemplate().bulkUpdate(queryString,
					new Object[] { orderCode });
		} else {
			queryString = "update " + claszz.getSimpleName()
					+ " m set m.orderCode=m.orderCode-1 where m." + orderByName
					+ "=? and m.orderCode>=?";
			this.getHibernateTemplate().bulkUpdate(queryString,
					new Object[] { orderId, orderCode });
		}

	}

	/**
	 * 获取原有实体类的序号
	 */
	public Integer getOldOrderCode(Long id, Class clazz) {
		List list = this.getHibernateTemplate().find(
				"select m.orderCode from " + clazz.getSimpleName()
						+ " m where m.id=?", id);
		return (Integer) list.get(0);
	}

	/**
	 * 修改操作时更改序号
	 */
	public void moveOrderCodeForUpdate(Class clazz, String orderByName,
			Integer newOrderCode, Integer oldOrderCode, Long orderId) {
		String queryString = null;
		if (newOrderCode > oldOrderCode) {
			queryString = "update "
					+ clazz.getSimpleName()
					+ " m set m.orderCode=m.orderCode-1 where m.orderCode >? and m.orderCode <= ? ";

		} else {
			queryString = "update "
					+ clazz.getSimpleName()
					+ " m set m.orderCode=m.orderCode+1 where m.orderCode < ?  and m.orderCode >=?";
		}
		if (null != orderByName) {
			if (null != orderId) {
				queryString = queryString + " and m." + orderByName + "= ?";
			} else {
				queryString = queryString + " and m." + orderByName + " is ?";
			}
		}
		this.getHibernateTemplate().bulkUpdate(queryString,
				new Object[] { oldOrderCode, newOrderCode, orderId });
	}

	/**
	 * 添加实体时更改序号
	 */
	public void moveOrderCodeForAdd(Class clazz, String orderCodeByName,
			Long orderCodeById, Integer orderCode) {
		String queryString = null;
		if (null != orderCodeById) {
			queryString = "update " + clazz.getSimpleName()
					+ " m set m.orderCode=m.orderCode+1 where m."
					+ orderCodeByName + "=? and m.orderCode>=?";
		} else {
			queryString = "update " + clazz.getSimpleName()
					+ " m set m.orderCode=m.orderCode+1 where m."
					+ orderCodeByName + " is ? and m.orderCode>=?";
		}

		this.getHibernateTemplate().bulkUpdate(queryString,
				new Object[] { orderCodeById, orderCode });
	}

}
