package org.cl.system.dictionary.service;

import java.util.List;

import org.cl.common.core.orm.BaseService;
import org.cl.system.dictionary.dao.DictionaryDao;
import org.cl.system.dictionary.entity.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.cl.system.dictionary.service.DictionaryService")
@Transactional()
public class DictionaryService extends BaseService {
	@Autowired
	private DictionaryDao dictionaryDao = null;

	/**
	 * 根据数据字典标识符获取数据字典
	 */
	public List findDictionaryByIdentifier(String identifier) {
		return dictionaryDao.findByProperty(Dictionary.class, "identifier",
				identifier, "orderCode", true);
	}

	/**
	 * 删除菜单集合
	 */
	public void deleteIds(String ids) {
		String[] idArray = ids.split(",");
		for (int i = 0; i < idArray.length; i++) {
			Dictionary dictionary = (Dictionary) dictionaryDao.findById(
					Dictionary.class, new Long(idArray[i]));
			dictionaryDao.delete(dictionary);
		}

	}

	public void save(Dictionary dictionary) {
		dictionary.setId(dictionaryDao.getMaxIdForJdbc());
		dictionaryDao.saveOrUpdate(dictionary);
	}

	/**
	 * 根据数据字典代码和标识符获取数据字典名称
	 */
	public String getNameForJdbc(String code, String identifier) {
		return dictionaryDao.getNameForJdbc(code, identifier);
	}

	/**
	 * 根据数据字典代码和标识符获取数据字典
	 * 
	 * @param code
	 * @param identifier
	 * @return
	 */
	public Dictionary findByIdentifierAndCode(String code, String identifier) {
		return dictionaryDao.findByIdentifierAndCode(identifier,code);
	}

	/**
	 * 
	 * @param identifier
	 * @return
	 */
	public List findByIdentifier(String identifier) {
		return dictionaryDao.findByProperty(Dictionary.class, "identifier",
				identifier, null, null);
	}

}
