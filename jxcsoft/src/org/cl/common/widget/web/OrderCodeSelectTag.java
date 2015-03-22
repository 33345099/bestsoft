package org.cl.common.widget.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.springframework.util.Assert;

public class OrderCodeSelectTag extends BaseSelectTag {

	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		StringBuffer html = new StringBuffer();
		try {
			Assert.notNull(request.getAttribute("maxOrderCode"),
					"request中不存在maxOrderCode");
			Integer maxOrderCode = (Integer) request
					.getAttribute("maxOrderCode");
			if (null == value || "".equals(value)) {
				maxOrderCode = maxOrderCode + 1;
			}
			style = style == null ? "" : "style=\"" + style + "\"";
			html.append("<select name=\"" + name + "\"" + style + " title=\""
					+ title + "\" " + this.getOnchange() + ">");
			String isSelect = "";
			if (null == value || "".equals(value)) {
				value = maxOrderCode.toString();
			}
			for (int i = 1; i <= maxOrderCode; i++) {
				if (value != null && value.equals(new Integer(i).toString())) {
					isSelect = "selected";
				} else {
					isSelect = "";
				}
				html.append("<option value=\"" + i + "\" " + isSelect + ">");
				html.append(i);
				html.append("</option>");
			}
			html.append("</select>");
			pageContext.getOut().println(html.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 表示按照正常的流程继续执行JSP网页
		return EVAL_PAGE;
	}
}
