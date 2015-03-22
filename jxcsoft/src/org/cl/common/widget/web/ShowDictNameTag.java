package org.cl.common.widget.web;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.cl.common.util.SpringTool;
import org.cl.common.util.StrUtils;
import org.cl.system.dictionary.service.DictionaryService;

/**
 * 根据数据字典ID显示数据字典名称
 * 
 * @author chenl
 * @data 2009-7-24
 */
public class ShowDictNameTag extends TagSupport {

	private String code = null;// 数据字典ID

	private String identifier = null;// 数据字典ID

	public int doStartTag() throws JspException {
		String dictionaryName = "";
		DictionaryService dictionaryService = (DictionaryService) SpringTool.getBean(DictionaryService.class);
		try {
			if (StrUtils.isNotNullOrBlank(code) && StrUtils.isNotNullOrBlank(identifier)) {
				dictionaryName = dictionaryService.getNameForJdbc(code, identifier);
			}
			pageContext.getOut().print(dictionaryName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dictionaryName = null;
		return SKIP_BODY;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
}
