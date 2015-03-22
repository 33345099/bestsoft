package org.cl.common.widget.web;

import java.io.IOException;
import java.util.List;
import javax.servlet.jsp.JspException;

import org.cl.common.util.SpringTool;
import org.cl.common.util.StrUtils;
import org.cl.system.dictionary.entity.Dictionary;
import org.cl.system.dictionary.service.DictionaryService;

/**
 * 数据字典输出下拉框
 * 
 * @author chenl
 * @data 2009-7-24
 */
public class DictionarySelectTag extends BaseSelectTag {

	/**
	 * 数据字典标识符
	 */
	private String identifier = null;

	public int doStartTag() throws JspException {
		html = new StringBuffer("");
		Dictionary dictionary = null;
		// 获取数据字典
		DictionaryService dictionaryService = (DictionaryService) SpringTool
				.getBean(DictionaryService.class);
		List<Dictionary> list = dictionaryService
				.findDictionaryByIdentifier(identifier);
		if (!"radio".equals(type)) {
			html.append("<select ");
			super.loadCommonPropertys(html);
			super.loadProperty(html, "multiple", multiple);
			html.append(">");
			if (hasDefault) {
				html.append("<option></option>");
			}
			// 遍历输出数据字典
			for (int i = 0; i < list.size(); i++) {
				String isSelect = "";
				dictionary = list.get(i);
				if (StrUtils.isNotNullOrBlank(notLimit)) {
					if (notLimit.indexOf(dictionary.getCode())>=0) {
						continue;
					}
				}
				// 判断是否默认选中
				if (value != null) {
					if (null != multiple && multiple.equals("true")) {
						String[] valueArray = value.split(",");
						for (int j = 0; j < valueArray.length; j++) {
							if (valueArray[j].equals(dictionary.getCode()
									.toString())) {
								isSelect = "selected";
							}
						}
					} else {
						if (value.equals(dictionary.getCode().toString())) {
							isSelect = "selected";
						}
					}
				}
				html.append("<option value=\"" + dictionary.getCode() + "\" ");
				if (StrUtils.isNotNullOrBlank(isSelect)) {
					html.append(isSelect);
				}
				html.append(">");
				html.append(dictionary.getName());
				html.append("</option>");
			}
			html.append("</select>");
		} else {
			// 遍历输出数据字典
			for (int i = 0; i < list.size(); i++) {
				String checked = "";
				dictionary = list.get(i);
				// 判断是否默认选中
				if (value != null
						&& value.equals(dictionary.getCode().toString())) {
					checked = "checked";
				}
				html.append("<input  type=\"radio\" name=\"" + name
						+ "\"  value=\"" + dictionary.getCode() + "\" ");
				if (StrUtils.isNotNullOrBlank(checked)) {
					html.append("checked=\"" + checked + "\"");
				}
				if (StrUtils.isNotNullOrBlank(onclick)) {
					html.append("onclick=\"" + this.getOnclick() + "\"");
				}
				if (StrUtils.isNotNullOrBlank(this.getOnchange())) {
					html.append("onchange=\"" + this.getOnchange() + "\"");
				}
				html.append("/>" + dictionary.getName());
				if (this.getNewline()) {
					if (i < list.size() - 1) {
						html.append("<br>");
					}
				}
			}
		}
		try {
			pageContext.getOut().println(html.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.initProperty();
		return EVAL_PAGE;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
}
