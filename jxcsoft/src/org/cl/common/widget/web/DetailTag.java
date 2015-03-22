package org.cl.common.widget.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.util.Assert;

/**
 * 详细页面
 * 
 * @author cl
 * 
 */
public class DetailTag extends TagSupport {

	private String id = null;

	private String value = null;

	private String type = null;

	public int doStartTag() throws JspException {
		String url = null;
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		try {
			if (type.equals("customer")) {
				url = request.getContextPath()
						+ "/crm/customer!detailCustomer.action?id=" + id;
			} else if (type.equals("vip")) {
				url = request.getContextPath()
				+ "/pm/vip!detailVip.action?id=" + id;
			} 
			else {
				Assert.isTrue(false, "无法解析详细地址");
			}
			String html = "<nobr><a href=\"javascript:openDetailWin('" + url
					+ "')\">" + value + "</a></nobr>";
			pageContext.getOut().println(html);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
