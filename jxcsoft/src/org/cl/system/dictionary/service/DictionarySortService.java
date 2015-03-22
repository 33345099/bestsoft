package org.cl.system.dictionary.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cl.common.core.orm.BaseService;
import org.cl.common.util.XmlDom4j;
import org.cl.system.dictionary.dao.DictionarySortDao;
import org.cl.system.dictionary.entity.Dictionary;
import org.cl.system.dictionary.entity.DictionarySort;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.cl.system.dictionary.service.DictionarySortService")
@Transactional()
public class DictionarySortService extends BaseService {
	@Autowired
	private DictionarySortDao dictionarySortDao = null;

	private static final Log log = LogFactory.getLog(DictionarySortService.class);

	public void initDictionarySort() {
		log.info("重新根据配置文件dictionary.xml文件生成数据库中的数据字典");
		dictionarySortDao.deleteAllDictionarySortForJdbc();
		XmlDom4j xmlDom4j = new XmlDom4j();
		// 加载数据字典文件
		Document document = xmlDom4j.loadFile("initData/dictionary.xml");
		Element element = null;
		// 获取数据字典分类集合
		List<Element> list = xmlDom4j.getRootElement(document).selectNodes("dictionarySort");
		DictionarySort dictionarySort = null;
		Set dictionarys = new HashSet();
		Element dictionaryElement = null;
		String identifier = null;
		// 遍历数据字典分类集合
		for (int i = 0; i < list.size(); i++) {
			dictionarySort = new DictionarySort();
			element = list.get(i);
			Long dictionarySortId = new Long(element.attributeValue("id").trim());
			dictionarySort.setId(dictionarySortId);
			dictionarySort.setName(element.attributeValue("name"));
			if (null != element.attributeValue("isSystem")) {
				dictionarySort.setIsSystem(new Integer(element.attributeValue("isSystem")));
			}
			identifier = element.attributeValue("identifier");
			dictionarySort.setIdentifier(identifier);
			dictionarySort.setDescription(element.attributeValue("description"));
			dictionarys = new HashSet();
			// 获取数据字典
			List dictionaryElements = element.selectNodes("dictionary");
			for (int j = 0; j < dictionaryElements.size(); j++) {
				Dictionary dictionary = new Dictionary();
				dictionaryElement = (Element) dictionaryElements.get(j);
				Long dictionaryId = new Long(dictionaryElement.attributeValue("id").trim());
				dictionary.setId(dictionaryId);
				dictionary.setIdentifier(identifier);
				dictionary.setName(dictionaryElement.attributeValue("name"));
				dictionary.setCode(dictionaryElement.attributeValue("code"));
				dictionary.setOrderCode(j + 1);
				dictionary.setDictionarySortId(dictionarySort.getId());
				dictionarys.add(dictionary);
			}
			dictionarySort.setDictionarys(dictionarys);
			dictionarySortDao.saveForJdbc(dictionarySort);
		}
	}

	/**
	 * 加载所有的数据字典标识符
	 */
	
	public Map loadAllIdentifier() {
		List<DictionarySort> list = dictionarySortDao.findAll(DictionarySort.class, null, null);
		Map map = new HashMap();
		for (DictionarySort dictionarySort : list) {
			map.put(dictionarySort.getIdentifier(), dictionarySort.getIdentifier());
		}
		return map;
	}

	/**
	 * 根据数据字典标识符获取数据字典分类
	 * 
	 * @param identifier
	 * @return
	 */
	public DictionarySort findByIdentifier(String identifier) {
		List list = dictionarySortDao.findByProperty(DictionarySort.class, "identifier", identifier, null, null);
		if (!list.isEmpty()) {
			return (DictionarySort) list.get(0);
		}
		return null;
	}
}
