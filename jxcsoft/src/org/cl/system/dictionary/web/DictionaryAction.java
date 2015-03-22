package org.cl.system.dictionary.web;

import java.util.Map;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.cl.common.core.web.BaseAction;
import org.cl.common.util.HttpTool;
import org.cl.common.util.SpringTool;
import org.cl.common.widget.pagination.Page;
import org.cl.system.dictionary.entity.Dictionary;
import org.cl.system.dictionary.entity.DictionarySort;
import org.cl.system.dictionary.service.DictionaryService;
import org.cl.system.dictionary.service.DictionarySortService;

/**
 * 数据字典Action
 * 
 * @author chenl
 * @data Mar 22, 2010
 */
@Namespace("/system")
@Action("dictionary")
@Results( {
		@Result(name = "indexDictionary", location = "/WEB-INF/content/system/dictionary/indexDictionary.jsp"),
		@Result(name = "listDictionary", location = "/WEB-INF/content/system/dictionary/listDictionary.jsp"),
		@Result(name = "addDictionary", location = "/WEB-INF/content/system/dictionary/addDictionary.jsp"),
		@Result(name = "modifyDictionary", location = "/WEB-INF/content/system/dictionary/modifyDictionary.jsp") })
public class DictionaryAction extends BaseAction {

	private DictionarySortService dictionarySortService = (DictionarySortService) SpringTool
			.getBean(DictionarySortService.class);

	private DictionaryService dictionaryService = (DictionaryService) SpringTool
			.getBean(DictionaryService.class);

	private Dictionary dictionary = null;

	public Dictionary getDictionary() {
		return dictionary;
	}

	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
	}

	/**
	 * 显示数据字典
	 */
	public String listDictionary() throws Exception {
		Map searchMap = super.buildSearch();
		DictionarySort dictionarySort = dictionarySortService
				.findByIdentifier((String) HttpTool
						.getParameter("transfer_identifier"));
		page = new Page(HttpTool.getPageNum());
		super.transferBuildSearch(searchMap, "and", "EQ", "S",
				"transfer_identifier", "identifier");
		dictionaryService.findByPage(page, searchMap);
		HttpTool.setAttribute("dictionarySort", dictionarySort);
		return "listDictionary";
	}

	/**
	 * 显示添加数据字典页面
	 */
	public String addDictionary() throws Exception {
		DictionarySort dictionarySort = dictionarySortService
				.findByIdentifier((String) HttpTool
						.getParameter("transfer_identifier"));
		HttpTool.setAttribute("dictionarySort", dictionarySort);
		return "addDictionary";
	}

	/**
	 * 显示添加数据字典页面
	 */
	public String saveDictionary() throws Exception {
		dictionaryService.save(dictionary);
		return jump("dictionary!listDictionary.action");
	}

	/**
	 * 显示修改数据字典页面
	 */
	public String modifyDictionary() throws Exception {
		dictionary = (Dictionary) dictionaryService.findById(id);
		DictionarySort dictionarySort = dictionarySortService
				.findByIdentifier((String) HttpTool
						.getParameter("transfer_identifier"));
		HttpTool.setAttribute("dictionarySort", dictionarySort);
		return "modifyDictionary";
	}

	/**
	 * 修改数据字典
	 */
	public String updateDictionary() throws Exception {
		dictionaryService.update(dictionary);
		return jump("dictionary!listDictionary.action");
	}

	/**
	 * 删除数据字典
	 */
	public String deleteDictionary() throws Exception {
		String ids = HttpTool.getParameter("ids");
		dictionaryService.deleteIds(ids);
		return jump("dictionary!listDictionary.action");
	}
}
