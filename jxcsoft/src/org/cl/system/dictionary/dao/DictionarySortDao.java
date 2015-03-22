package org.cl.system.dictionary.dao;

import org.cl.common.core.orm.BaseDao;
import org.cl.system.dictionary.entity.Dictionary;
import org.cl.system.dictionary.entity.DictionarySort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Repository()
public class DictionarySortDao extends BaseDao {
	private static final Logger logger = LoggerFactory
			.getLogger(DictionarySortDao.class);

	/**
	 * 删除所有的数据字典
	 */
	public void deleteAllDictionarySortForJdbc() {
		this.getJdbcTemplate().update("delete from sys_dictionary");
		this.getJdbcTemplate().update("delete from sys_dictionary_sort");
	}

	/**
	 * 基于Jdbc保存数据字典
	 * 
	 * @param dictionarySort
	 */
	public void saveForJdbc(DictionarySort dictionarySort) {
		logger.info("插入数据字典分类:" + dictionarySort.getName() + "---"
				+ dictionarySort.getIdentifier());
		String sql = null;
		Dictionary dictionary = null;
		sql = "insert into sys_dictionary_sort(id,name,identifier,description)values(?,?,?,?)";
		this.getJdbcTemplate().update(
				sql,
				new Object[] { dictionarySort.getId(),
						dictionarySort.getName(),
						dictionarySort.getIdentifier(),
						dictionarySort.getDescription() });
		Set dictionarys = dictionarySort.getDictionarys();
		if (null == dictionarys) {
			return;
		}
		Iterator<Dictionary> it = dictionarys.iterator();
		while (it.hasNext()) {
			dictionary = it.next();
			logger.info("----插入数据字典分类:" + dictionary.getName());
			sql = "insert into SYS_DICTIONARY(ID,IDENTIFIER,DICTIONARY_SORT_ID,NAME,CODE,ORDER_CODE)values(?,?,?,?,?,?)";
			this.getJdbcTemplate().update(
					sql,
					new Object[] { dictionary.getId(),
							dictionary.getIdentifier(),
							dictionary.getDictionarySortId(),
							dictionary.getName(), dictionary.getCode(),
							dictionary.getOrderCode() });
		}
	}

	/**
	 * 根据分类获取数据字典
	 * 
	 * @param string
	 * @return
	 */
	public DictionarySort findByIdentifier(String identifier) {
		List list = this.getHibernateTemplate().find(
				" from DictionarySort where identifier=?", identifier);
		if (!list.isEmpty()) {
			return (DictionarySort) list.get(0);
		}
		return null;
	}
}
