package org.cl.system.dictionary.dao;

import org.cl.common.core.orm.BaseDao;
import org.cl.system.dictionary.entity.Dictionary;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository()
public class DictionaryDao extends BaseDao {

	public Long getMaxIdForJdbc() {
		long maxId = this.getJdbcTemplate().queryForLong(
				"select max(id) from sys_dictionary");
		return maxId + 1;
	}

	public Dictionary findByIdentifierAndValue(String identifier, String value) {
		List<Dictionary> list = this.getHibernateTemplate().find(
				" from Dictionary where identifier=? and value=?",
				new Object[] { identifier, value });
		if (list.size() == 1) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public String getNameForJdbc(String code, String identifier) {
		String sql = "select name from sys_dictionary where code=? and identifier=?";
		List list = this.getJdbcTemplate().queryForList(sql,
				new Object[] { code, identifier });
		for (int i = 0; i < list.size();) {
			Map map = (Map) list.get(i);
			return (String) map.get("name");
		}
		return null;
	}

	/**
	 * 根据数据字典代码和标识符获取数据字典
	 * 
	 * @param code
	 * @param identifier
	 * @return
	 */
	public Dictionary findByIdentifierAndCode(String identifier, String code) {
		String queryString = " from Dictionary where identifier=? and code=?";
		List list = this.getHibernateTemplate().find(queryString,
				new Object[] { identifier, code });
		if (!list.isEmpty()) {
			return (Dictionary) list.get(0);
		}
		return null;
	}

	/**
	 * 根据标识符获取数据字典
	 * 
	 * @param identifier
	 * @return
	 */
	public List findByIdentifier(String identifier) {
		return this.getHibernateTemplate().find(
				" from  Dictionary where identifier=?", identifier);
	}
}
