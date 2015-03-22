package org.cl.common.widget.web;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 过期转换
 * 
 * @author cl
 * 
 */
public class DiscardTag extends TagSupport {
	private String value = null;

	public int doStartTag() throws JspException {
		try {
			if (value.endsWith("(过期)")) {
				value = value.substring(0, value.length() - "(过期)".length());
			}
			pageContext.getOut().println(value);
		} catch (IOException e) {
			e.printStackTrace();
		}
		value = null;
		return EVAL_PAGE;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
