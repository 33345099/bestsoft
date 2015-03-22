package org.cl.system.resource.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cl.common.core.orm.BaseDao;
import org.cl.common.util.StrUtils;
import org.cl.common.widget.pagination.Page;
import org.cl.system.resource.entity.Operation;
import org.springframework.stereotype.Repository;

@Repository()
public class OperationDao extends BaseDao {

	private static final Log log = LogFactory.getLog(OperationDao.class);

	/**
	 * 根据用户ID加载所有可用的操作集合
	 * 
	 * @param userId
	 * @return
	 */
	public List<Operation> findAllOperationByUserIdForJdbc(Long userId) {
		List list = new ArrayList();
		String queryString = " select o.id,o.name,o.identifier,o.type_code from sys_operation o,um_user_role ur,um_role_operation ro "
				+ " where o.id=ro.operation_id and ro.role_id=ur.role_id and ur.user_id=?";
		List resultList = this.getJdbcTemplate().queryForList(queryString,
				new Object[] { userId });
		for (int i = 0; i < resultList.size(); i++) {
			Operation operation = new Operation();
			list.add(operation);
		}
		return list;
	}

	public List findByPage(Page page, Map parameterMap) {
		StringBuffer queryString = new StringBuffer(
				" from Operation o where 1=1");
		List valueList = new ArrayList();
		return this.findByHql(page, queryString.toString(), valueList);
	}

	public List<Operation> findByTypeForJdbc(String typeCode) {
		List list = new ArrayList();
		String queryString = " select id,name from sys_operation o where type_code=?";
		List resultList = this.getJdbcTemplate().queryForList(queryString,
				new Object[] { typeCode });
		Map map = null;
		for (int i = 0; i < resultList.size(); i++) {
			Operation operation = new Operation();
			map = (Map) resultList.get(i);
			operation.setId(StrUtils.toLong(map.get("id")));
			operation.setName(StrUtils.ObjectToString(map.get("name")));
			list.add(operation);
		}
		return list;
	}

	public long childOperationSizeForJdbc(String typeCode) {
		String queryString = " select count(*) from sys_operation o where o.type_code =?";
		Long count = super.getJdbcTemplate().queryForLong(queryString,
				new Object[] { typeCode });

		return count;
	}

	public List<Operation> findAll() {
		String queryString = " from Operation o  order by identifier,name";
		return this.getHibernateTemplate().find(queryString);
	}

	/**
	 * 删除所有的菜单
	 */
	public void deleteAllOperationForJdbc() {
		this.getJdbcTemplate().update(" delete from um_role_operation");
		this.getJdbcTemplate().update(" delete from sys_operation");
	}

	synchronized public Long getMaxIdForJdbc() {
		long maxId = this.getJdbcTemplate().queryForLong(
				"select max(id) from sys_operation");
		return maxId + 1;
	}

	public void saveForJdbc(Operation operation) {
		log.info("插入操作:" + operation.getName());
		String sql = null;
		Long id = getMaxIdForJdbc();
		operation.setId(id);
		sql = "insert into sys_operation(id,name,identifier)values(?,?,?)";
		this.getJdbcTemplate().update(
				sql,
				new Object[] { id, operation.getName(),
						operation.getIdentifier() });
	}
}
