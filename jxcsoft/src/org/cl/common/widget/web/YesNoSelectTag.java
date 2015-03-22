package org.cl.common.widget.web;

import javax.servlet.jsp.JspException;

import org.cl.common.util.StrUtils;

/**
 * 是否下拉框
 * 
 * @author chenl
 * @data 2009-7-24
 */
@SuppressWarnings("serial")
public class YesNoSelectTag extends BaseSelectTag {
	private String aliasYes = null;
	private String aliasNo = null;

	public int doStartTag() throws JspException {
		html = new StringBuffer();
		html.append("<select ");
		super.loadCommonPropertys(html);
		html.append(">");
		if (hasDefault) {
			html.append("<option></option>");
		}

		for (int i = 1; i >= 0; i--) {
			String isSelect = "";
			if (value != null && value.equals(new Integer(i).toString())) {
				isSelect = "selected";
			}
			if (StrUtils.isNotNullOrBlank(limit)) {
				if (limit.indexOf(new Integer(i).toString()) < 0) {
					continue;
				}
			}
			html.append("<option value=\"" + i + "\" " + isSelect + ">");
			if (i == 1) {
				if (StrUtils.isNotNullOrBlank(aliasYes)) {
					html.append(aliasYes);
				} else {
					html.append("是");
				}
			} else {
				if (StrUtils.isNotNullOrBlank(aliasNo)) {
					html.append(aliasNo);
				} else {
					html.append("否");
				}
			}
			html.append("</option>");
		}
		html.append("</select>");
		try {
			pageContext.getOut().println(html.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.initProperty();
		aliasYes = null;
		aliasNo = null;
		return EVAL_PAGE;
	}

	public String getAliasYes() {
		return aliasYes;
	}

	public void setAliasYes(String aliasYes) {
		this.aliasYes = aliasYes;
	}

	public String getAliasNo() {
		return aliasNo;
	}

	public void setAliasNo(String aliasNo) {
		this.aliasNo = aliasNo;
	}
}
