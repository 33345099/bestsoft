package org.cl.system.dictionary.util;

import org.cl.common.util.SpringTool;
import org.cl.system.dictionary.service.DictionaryService;

public class DictionaryInterface {

	/**
	 * 获取数据字典名称
	 * 
	 * @param code
	 * @param identifier
	 * @return
	 */
	public static String findNameByCode(String code, String identifier) {
		DictionaryService dictionaryService = (DictionaryService) SpringTool.getBean(DictionaryService.class);
		return dictionaryService.getNameForJdbc(code, identifier);
	}
}
